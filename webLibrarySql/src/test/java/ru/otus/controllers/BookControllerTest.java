package ru.otus.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import ru.otus.domain.Book;
import ru.otus.domain.Genre;
import ru.otus.dto.CommentDto;
import ru.otus.integration.CommentCheck;
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

    @MockBean
    private UserDetailsService userDetailsService;

    @MockBean
    private CommentCheck commentCheck;

    @Test
    void getAll() throws Exception {
        mvc
                .perform(get("/book"))
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
                .perform(post("/book")
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
    @WithMockUser(
            username = "admin",
            authorities = {"ROLE_ADMIN"}
    )
    void deleteBook() throws Exception {
        mvc
                .perform(delete("/book/1"))
                .andExpect(ResultMatcher.matchAll(
                        status().isOk()
                        )
                );
        verify(bookService).deleteBook(1L);
    }

    @Test
    @WithMockUser(
            username = "admin",
            authorities = {"ROLE_ADMIN"}
    )
    void getBookInfo() throws Exception {
        when(bookService.getBookById(1L)).thenReturn(new Book(new Genre("genre"), "book"));
        mvc
                .perform(get("/book/1"))
                .andExpect(ResultMatcher.matchAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON_UTF8)
                        )
                );
        verify(bookService).getBookById(1L);
    }

    @Test
    @WithMockUser(
            username = "admin",
            authorities = {"ROLE_ADMIN"}
    )
    void addCommentToBook() throws Exception {
        CommentDto commentDto = new CommentDto(1L, "comment");
        when(commentCheck.check(eq(commentDto))).thenReturn(commentDto);
        mvc
                .perform(post("/book/addComment")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(new ObjectMapper().writeValueAsString(commentDto))
                )
                .andExpect(ResultMatcher.matchAll(
                        status().isOk()
                        )
                );
        verify(bookService).addCommentToBook(1L, "comment");
    }

    @Test
    @WithMockUser(
            username = "admin",
            authorities = {"ROLE_ADMIN"}
    )
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
    @WithMockUser(
            username = "admin",
            authorities = {"ROLE_ADMIN"}
    )
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
    @WithMockUser(
            username = "admin",
            authorities = {"ROLE_ADMIN"}
    )
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