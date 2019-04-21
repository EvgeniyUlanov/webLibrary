package ru.otus.batch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;
import ru.otus.domain.Book;
import ru.otus.exeptions.CreateEntityException;
import ru.otus.services.BookService;

import java.util.List;

public class BookItemWriter implements ItemWriter<Book> {

    private final Logger logger = LoggerFactory.getLogger("Batch");

    private BookService bookService;

    public BookItemWriter(BookService bookService) {
        this.bookService = bookService;
    }

    @Override
    public void write(List<? extends Book> items) throws Exception {
        for (Book book : items) {
            try {
                logger.info(String.format("trying write book '%s' to database", book.getName()));
                bookService.addBook(book).block();
            } catch (CreateEntityException e) {
                logger.info(e.getMessage());
            }
        }
    }
}
