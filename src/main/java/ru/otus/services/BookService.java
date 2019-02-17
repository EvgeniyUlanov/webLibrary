package ru.otus.services;

import ru.otus.domain.Book;

import java.util.List;

public interface BookService {

    Book getBookByName(String bookName);

    Book getBookById(Long id);

    List<Book> getAllBooks();

    List<Book> getBookByGenre(String genre);

    void addBook(String bookName, String genre, String authorName);

    List<Book> getBookByAuthor(String authorName);

    void addCommentToBook(String bookName, String comment);

    List<String> getCommentsByBook(String bookName);
}
