package ru.otus.batch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.data.MongoItemWriter;
import org.springframework.batch.item.data.builder.MongoItemWriterBuilder;
import org.springframework.batch.item.json.JacksonJsonObjectMarshaller;
import org.springframework.batch.item.json.JacksonJsonObjectReader;
import org.springframework.batch.item.json.JsonFileItemWriter;
import org.springframework.batch.item.json.JsonItemReader;
import org.springframework.batch.item.json.builder.JsonFileItemWriterBuilder;
import org.springframework.batch.item.json.builder.JsonItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.mongodb.core.MongoOperations;
import ru.otus.domain.Book;
import ru.otus.dto.BookDto;
import ru.otus.services.BookService;

import java.util.List;

@EnableBatchProcessing
@Configuration
public class BatchConfig {

    private final Logger logger = LoggerFactory.getLogger("Batch");

    private JobBuilderFactory jobBuilderFactory;
    private StepBuilderFactory stepBuilderFactory;

    public BatchConfig(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
    }

    @Bean
    public JsonItemReader<BookDto> reader() {
        return new JsonItemReaderBuilder<BookDto>()
                .name("jsonBookReader")
                .resource(new FileSystemResource("outputBook.txt"))
                .jsonObjectReader(new JacksonJsonObjectReader<>(BookDto.class))
                .build();
    }

    @Bean
    public ItemProcessor<BookDto, Book> processor() {
        return BookDto::bookfromDto;
    }

    @Bean
    public BookItemWriter writer(BookService bookService) {
        return new BookItemWriter(bookService);
    }

    @Bean
    public Job importUserJob(Step step1) {
        return jobBuilderFactory.get("importUserJob")
                .incrementer(new RunIdIncrementer())
                .flow(step1)
                .end()
                .listener(new JobExecutionListener() {
                    @Override
                    public void beforeJob(JobExecution jobExecution) {
                        logger.info("Начало job");
                    }

                    @Override
                    public void afterJob(JobExecution jobExecution) {
                        logger.info("Конец job");
                    }
                })
                .build();
    }

    @Bean
    public Step step1(BookItemWriter writer, ItemReader<BookDto> reader, ItemProcessor<BookDto, Book> itemProcessor) {
        return stepBuilderFactory.get("step1")
                .<BookDto, Book>chunk(5)
                .reader(reader)
                .processor(itemProcessor)
                .writer(writer)
                .listener(new ItemReadListener<BookDto>() {
                    public void beforeRead() { logger.info("Начало чтения"); }
                    public void afterRead(BookDto bookDto) { logger.info("Конец чтения: прочитана книга " + bookDto); }
                    public void onReadError(Exception e) { logger.info("Ошибка чтения: " + e.getMessage()); }
                })
                .listener(new ItemWriteListener<Book>() {
                    public void beforeWrite(List list) { logger.info("Начало записи"); }
                    public void afterWrite(List list) { logger.info("Конец записи. Обработано " + list.size()); }
                    public void onWriteError(Exception e, List list) { logger.info("Ошибка записи"); }
                })
                .listener(new ChunkListener() {
                    public void beforeChunk(ChunkContext chunkContext) {logger.info("Начало пачки");}
                    public void afterChunk(ChunkContext chunkContext) {logger.info("Конец пачки");}
                    public void afterChunkError(ChunkContext chunkContext) {logger.info("Ошибка пачки");}
                })
                .allowStartIfComplete(true)
                .build();
    }
}
