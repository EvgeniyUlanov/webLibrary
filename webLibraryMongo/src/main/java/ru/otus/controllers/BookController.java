package ru.otus.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.domain.Book;
import ru.otus.dto.BookContainerDto;
import ru.otus.dto.CommentContainerDto;
import ru.otus.services.BookService;

@RestController
public class BookController {

    private BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping(value = "/book/all")
    public Flux<Book> getAll() {
        return bookService.getAllBooks();
    }

    @PostMapping(value = "/book/add")
    public ResponseEntity<String> addBook(@RequestBody BookContainerDto bookDto) {
        bookService.addBook(bookDto.getBookName(), bookDto.getGenreName(), bookDto.getAuthorName());
        return ResponseEntity.ok("{}");
    }

    @DeleteMapping(value = "/book/delete/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable(value = "id") String id) {
        bookService.deleteBook(id);
        return ResponseEntity.ok("{}");
    }

    @GetMapping(value = "/book/info")
    public Mono<Book> getBookInfo(@RequestParam(value = "book_id") String id) {
        return bookService.getBookById(id);
    }

    @PostMapping(value = "/book/addComment")
    public ResponseEntity<String> addCommentToBook(@RequestBody CommentContainerDto commentDto) {
        bookService.addCommentToBook(commentDto.getBookId(), commentDto.getComment());
        return ResponseEntity.ok("{}");
    }

    @PostMapping(value = "/book/addAuthorToBook")
    public ResponseEntity<String> addAuthorToBook(@RequestBody BookContainerDto bookDto) {
        bookService.addAuthorToBook(bookDto.getBookId(), bookDto.getAuthorName());
        return ResponseEntity.ok("{}");
    }

    @GetMapping(value = "/book/findByAuthor")
    public Flux<Book> findByAuthor(@RequestParam(value = "authorName") String authorName) {
        return bookService.getBookByAuthor(authorName);
    }

    @GetMapping(value = "/book/findByGenre")
    public Flux<Book> findByGenre(@RequestParam(value = "genreName") String genreName) {
        return bookService.getBookByGenre(genreName);
    }

    @GetMapping(value = "/book/findByName")
    public Flux<Book> findByName(@RequestParam(value = "bookName") String bookName) {
        return bookService.getBookByName(bookName);
    }
}
