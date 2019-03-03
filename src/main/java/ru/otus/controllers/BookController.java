package ru.otus.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.otus.domain.Book;
import ru.otus.services.BookService;

import java.util.Collections;
import java.util.List;

@RestController
public class BookController {

    private BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping(value = "/book/all")
    public List<Book> getAll() {
        return bookService.getAllBooks();
    }

    @PostMapping(value = "/book/add")
    public HttpStatus addBook(
            @RequestParam(value = "bookName") String bookName,
            @RequestParam(value = "authorName") String authorName,
            @RequestParam(value = "genreName") String genreName) {
        System.out.println(bookName + " " + authorName + " " + genreName);
        bookService.addBook(bookName, genreName, authorName);
        return HttpStatus.OK;
    }

    @PostMapping(value = "/book/delete")
    public HttpStatus deleteBook(@RequestParam(value = "book_id") Long id) {
        bookService.deleteBook(id);
        return HttpStatus.OK;
    }

    @GetMapping(value = "/book/info")
    public Book getBookInfo(@RequestParam(value = "book_id") Long id) {
        return bookService.getBookById(id);
    }

    @PostMapping(value = "/book/addComment")
    public HttpStatus addCommentToBook(
            @RequestParam(value = "comment") String comment,
            @RequestParam(value = "book_id") Long bookId) {
        Book book = bookService.getBookById(bookId);
        bookService.addCommentToBook(book.getName(), comment);
        return HttpStatus.OK;
    }

    @PostMapping(value = "/book/addAuthorToBook")
    public HttpStatus addAuthorToBook(
            @RequestParam(value = "book_id") Long bookId,
            @RequestParam(value = "authorName") String authorName,
            Model model) {
        Book book = bookService.getBookById(bookId);
        bookService.addAuthorToBook(book.getName(), authorName);
        return HttpStatus.OK;
    }

    @GetMapping(value = "/book/findByAuthor")
    public List<Book> findByAuthor(@RequestParam(value = "authorName") String authorName) {
        return bookService.getBookByAuthor(authorName);
    }

    @GetMapping(value = "/book/findByGenre")
    public List<Book> findByGenre(@RequestParam(value = "genreName") String genreName) {
        return bookService.getBookByGenre(genreName);
    }

    @GetMapping(value = "book/findByName")
    public List<Book> findByName(@RequestParam(value = "bookName") String bookName) {
        return Collections.singletonList(bookService.getBookByName(bookName));
    }
}
