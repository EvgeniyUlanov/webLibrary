package ru.otus.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.otus.domain.Book;
import ru.otus.services.BookService;

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
    public ResponseEntity<String> addBook(
            @RequestParam(value = "bookName") String bookName,
            @RequestParam(value = "authorName") String authorName,
            @RequestParam(value = "genreName") String genreName) {
        bookService.addBook(bookName, genreName, authorName);
        return ResponseEntity.ok("{}");
    }

    @PostMapping(value = "/book/delete")
    public ResponseEntity<String> deleteBook(@RequestParam(value = "book_id") String id) {
        bookService.deleteBook(id);
        return ResponseEntity.ok("{}");
    }

    @GetMapping(value = "/book/info")
    public Book getBookInfo(@RequestParam(value = "book_id") String id) {
        return bookService.getBookById(id);
    }

    @PostMapping(value = "/book/addComment")
    public ResponseEntity<String> addCommentToBook(
            @RequestParam(value = "comment") String comment,
            @RequestParam(value = "book_id") String bookId) {
        bookService.addCommentToBook(bookId, comment);
        return ResponseEntity.ok("{}");
    }

    @PostMapping(value = "/book/addAuthorToBook")
    public ResponseEntity<String> addAuthorToBook(
            @RequestParam(value = "book_id") String bookId,
            @RequestParam(value = "authorName") String authorName,
            Model model) {
        bookService.addAuthorToBook(bookId, authorName);
        return ResponseEntity.ok("{}");
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
        return bookService.getBookByName(bookName);
    }
}
