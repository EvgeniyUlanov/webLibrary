package ru.otus.controllers;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import ru.otus.domain.Book;
import ru.otus.domain.Genre;
import ru.otus.services.BookService;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(BookController.class)
class BookControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private BookService bookService;

    @Test
    void getAll() throws Exception {
        mvc
                .perform(get("/book/all"))
                .andExpect(ResultMatcher.matchAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON_UTF8)
                        )
                );
        verify(bookService).getAllBooks();
    }

    @Test
    void addBook() throws Exception {
        mvc
                .perform(post("/book/add")
                        .param("bookName", "book name")
                        .param("authorName", "author")
                        .param("genreName", "genre"))
                .andExpect(ResultMatcher.matchAll(
                        status().isOk()
                        )
                );
        verify(bookService).addBook("book name", "genre", "author");
    }

    @Test
    void deleteBook() throws Exception {
        mvc
                .perform(post("/book/delete").param("book_id", "1"))
                .andExpect(ResultMatcher.matchAll(
                        status().isOk()
                        )
                );
        verify(bookService).deleteBook(1L);
    }

    @Test
    void getBookInfo() throws Exception {
        when(bookService.getBookById(1L)).thenReturn(new Book(new Genre("genre"), "book"));
        mvc
                .perform(get("/book/info").param("book_id", "1"))
                .andExpect(ResultMatcher.matchAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON_UTF8)
                        )
                );
        verify(bookService).getBookById(1L);
    }

    @Test
    void addCommentToBook() throws Exception {
        mvc
                .perform(post("/book/addComment")
                        .param("comment", "comment")
                        .param("book_id", "1"))
                .andExpect(ResultMatcher.matchAll(
                        status().isOk()
                        )
                );
        verify(bookService).addCommentToBook(1L, "comment");
    }

    @Test
    void addAuthorToBook() throws Exception {
        mvc
                .perform(post("/book/addAuthorToBook")
                        .param("book_id", "1")
                        .param("authorName", "author"))
                .andExpect(ResultMatcher.matchAll(
                        status().isOk()
                        )
                );
        verify(bookService).addAuthorToBook(1L, "author");
    }

    @Test
    void findByAuthor() throws Exception {
        mvc
                .perform(get("/book/findByAuthor").param("authorName", "author"))
                .andExpect(ResultMatcher.matchAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON_UTF8)
                        )
                );
        verify(bookService).getBookByAuthor("author");
    }

    @Test
    void findByGenre() throws Exception {
        mvc
                .perform(get("/book/findByGenre").param("genreName", "genre"))
                .andExpect(ResultMatcher.matchAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON_UTF8)
                        )
                );
        verify(bookService).getBookByGenre("genre");
    }
}