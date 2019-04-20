package ru.otus.batch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.item.*;
import org.springframework.batch.item.json.JacksonJsonObjectMarshaller;
import org.springframework.batch.item.json.JsonFileItemWriter;
import org.springframework.batch.item.json.builder.JsonFileItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import ru.otus.domain.Book;
import ru.otus.dto.BookDto;
import ru.otus.repositories.BookRepository;

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
    public BookItemReader reader(BookRepository bookRepository) {
        BookItemReader bookItemReader = new BookItemReader();
        bookItemReader.setBookRepository(bookRepository);
        return bookItemReader;
    }

    @Bean
    public ItemProcessor<Book, BookDto> processor() {
        return BookDto::dtoFromBook;
    }

    @Bean
    public JsonFileItemWriter<BookDto> writer() {
        return new JsonFileItemWriterBuilder<BookDto>()
                .name("personItemWriter")
                .resource(new FileSystemResource("outputBook.txt"))
                .jsonObjectMarshaller(new JacksonJsonObjectMarshaller<>())
                .build();
    }

    @Bean
    public Job importUserJob(Step step1, BookItemReader itemReader) {
        return jobBuilderFactory.get("importUserJob")
                .incrementer(new RunIdIncrementer())
                .flow(step1)
                .end()
                .listener(new JobExecutionListener() {
                    @Override
                    public void beforeJob(JobExecution jobExecution) {
                        logger.info("Начало job");
                        itemReader.init();
                    }

                    @Override
                    public void afterJob(JobExecution jobExecution) {
                        logger.info("Конец job");
                    }
                })
                .build();
    }

    @Bean
    public Step step1(JsonFileItemWriter<BookDto> writer, ItemReader<Book> reader, ItemProcessor<Book, BookDto> itemProcessor) {
        return stepBuilderFactory.get("step1")
                .<Book, BookDto>chunk(10)
                .reader(reader)
                .processor(itemProcessor)
                .writer(writer)
                .listener(new ItemReadListener<Book>() {
                    public void beforeRead() { logger.info("Начало чтения"); }
                    public void afterRead(Book book) { logger.info("Конец чтения: прочитана книга " + book); }
                    public void onReadError(Exception e) { logger.info("Ошибка чтения: " + e.getMessage()); }
                })
                .listener(new ItemWriteListener<Book>() {
                    public void beforeWrite(List list) { logger.info("Начало записи"); }
                    public void afterWrite(List list) { logger.info("Конец записи. Записано " + list.size()); }
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
