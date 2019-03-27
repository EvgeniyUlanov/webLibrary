package ru.otus.controllers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Mono;
import ru.otus.domain.Book;
import ru.otus.dto.BookContainerDto;
import ru.otus.dto.CommentContainerDto;
import ru.otus.services.BookService;

import static org.mockito.Mockito.when;


@RunWith(SpringRunner.class)
@WebFluxTest(BookController.class)
@DisplayName("book controller test")
class BookControllerTest {

    @Autowired
    private WebTestClient webClient;

    @MockBean
    private BookService bookService;

    @Test
    @DisplayName("when get all books than status ok")
    void testGetAll() {
        webClient
                .get().uri("/book")
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @DisplayName("when add book than status ok")
    void testAddBook() {
        BookContainerDto bookContainerDto =
                new BookContainerDto("test", "test", "test", "test");
        when(bookService
                .addBook(
                        bookContainerDto.getBookName(),
                        bookContainerDto.getGenreName(),
                        bookContainerDto.getAuthorName()
                )
        ).thenReturn(Mono.just(new Book()));

        webClient
                .post().uri("/book")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(BodyInserters.fromObject(bookContainerDto))
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @DisplayName("when book delete than status ok")
    void testDeleteBook() {
        when(bookService.deleteBook("1")).thenReturn(Mono.empty());

        webClient
                .delete().uri("/book/{id}", 1)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @DisplayName("when book/info than status ok")
    void testGetBookInfo() {
        webClient
                .get().uri("/book/{id}", 1)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @DisplayName("when book/addComment than status ok")
    void testAddCommentToBook() {
        CommentContainerDto commentContainerDto = new CommentContainerDto("1", "comment");
        when(bookService.addCommentToBook(commentContainerDto.getBookId(), commentContainerDto.getComment()))
                .thenReturn(Mono.just(new Book()));

        webClient
                .post().uri("/book/addComment")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(BodyInserters.fromObject(commentContainerDto))
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @DisplayName("when book/addAuthorToBook than status ok")
    void testAddAuthorToBook() {
        BookContainerDto bookContainerDto = new BookContainerDto("1", "test", "test", "test");
        when(bookService.addAuthorToBook(bookContainerDto.getBookId(), bookContainerDto.getAuthorName()))
                .thenReturn(Mono.just(new Book()));

        webClient
                .post().uri("/book/addAuthorToBook")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(BodyInserters.fromObject(bookContainerDto))
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @DisplayName("when book/findBuAuthor than status ok")
    void testFindByAuthor() {
        webClient
                .get().uri("/book/findByAuthor?authorName={name}", "test")
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @DisplayName("when book/findByGenre than status ok")
    void testFindByGenre() {
        webClient
                .get().uri("/book/findByGenre?genreName={name}", "test")
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @DisplayName("when book/findByName than status ok")
    void testFindByName() {
        webClient
                .get().uri("/book/findByName?bookName={name}", "test")
                .exchange()
                .expectStatus().isOk();
    }
}