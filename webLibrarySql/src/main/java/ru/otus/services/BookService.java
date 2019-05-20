package ru.otus.services;

import ru.otus.domain.Book;
import ru.otus.dto.CommentDto;

import java.util.List;

public interface BookService {

    List<Book> getBookByName(String bookName);

    Book getBookById(Long id);

    List<Book> getAllBooks();

    List<Book> getBookByGenre(String genre);

    void addBook(String bookName, String genre, String authorName);

    List<Book> getBookByAuthor(String authorName);

    void addCommentToBook(CommentDto commentDto);

    void deleteBook(Long id);

    void addAuthorToBook(Long bookId, String authorName);
}
