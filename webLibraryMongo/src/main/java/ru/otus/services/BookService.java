package ru.otus.services;

import ru.otus.domain.Book;

import java.util.List;

public interface BookService {

    List<Book> getBookByName(String bookName);

    Book getBookById(String id);

    List<Book> getAllBooks();

    List<Book> getBookByGenre(String genre);

    void addBook(String bookName, String genre, String authorName);

    List<Book> getBookByAuthor(String authorName);

    void addCommentToBook(String bookId, String comment);

    void deleteBook(String id);

    void addAuthorToBook(String bookId, String authorName);
}
