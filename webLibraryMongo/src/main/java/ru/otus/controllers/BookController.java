package ru.otus.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.domain.Book;
import ru.otus.dto.BookContainerDto;
import ru.otus.dto.CommentContainerDto;
import ru.otus.services.BookService;

import java.sql.ResultSet;

import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@RestController
public class BookController {

    private BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping(value = "/book")
    public Flux<Book> getAll() {
        return bookService.getAllBooks();
    }

    @PostMapping(value = "/book")
    public Mono<HttpStatus> addBook(@RequestBody BookContainerDto bookDto) {
        return bookService
                .addBook(bookDto.getBookName(), bookDto.getGenreName(), bookDto.getAuthorName())
                .thenReturn(HttpStatus.OK);
    }

    @DeleteMapping(value = "/book/{id}")
    public Mono<ServerResponse> deleteBook(@PathVariable(value = "id") String id) {
        return bookService
                .deleteBook(id)
                .then(ok().build());
    }

    @GetMapping(value = "/book/{id}")
    public Mono<Book> getBookInfo(@PathVariable(value = "id") String id) {
        return bookService.getBookById(id);
    }

    @PostMapping(value = "/book/addComment")
    public Mono<HttpStatus> addCommentToBook(@RequestBody CommentContainerDto commentDto) {
        return bookService
                .addCommentToBook(commentDto.getBookId(), commentDto.getComment())
                .thenReturn(HttpStatus.OK);
    }

    @PostMapping(value = "/book/addAuthorToBook")
    public Mono<HttpStatus> addAuthorToBook(@RequestBody BookContainerDto bookDto) {
        return bookService
                .addAuthorToBook(bookDto.getBookId(), bookDto.getAuthorName())
                .thenReturn(HttpStatus.OK);
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
