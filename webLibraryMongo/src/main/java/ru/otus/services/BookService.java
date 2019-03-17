package ru.otus.services;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.domain.Book;

public interface BookService {

    Flux<Book> getBookByName(String bookName);

    Mono<Book> getBookById(String id);

    Flux<Book> getAllBooks();

    Flux<Book> getBookByGenre(String genre);

    void addBook(String bookName, String genre, String authorName);

    Flux<Book> getBookByAuthor(String authorName);

    void addCommentToBook(String bookId, String comment);

    void deleteBook(String id);

    void addAuthorToBook(String bookId, String authorName);
}
