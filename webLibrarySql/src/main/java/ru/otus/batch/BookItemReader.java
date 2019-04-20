package ru.otus.batch;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import ru.otus.domain.Book;
import ru.otus.repositories.BookRepository;

import java.util.List;

public class BookItemReader implements ItemReader<Book> {

    private BookRepository bookRepository;
    private int bookIndex;
    private List<Book> bookList;

    public BookItemReader() {
        bookIndex = 0;
    }

    public void setBookRepository(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public void init() {
        bookList = bookRepository.findAll();
        bookIndex = 0;
    }

    @Override
    public Book read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        if (bookRepository == null) {
            throw new Exception("book repository not found");
        }
        Book book;
        if (bookIndex >= bookList.size()) {
            book = null;
        } else {
            book = bookList.get(bookIndex);
            bookIndex++;
        }
        return book;
    }
}
