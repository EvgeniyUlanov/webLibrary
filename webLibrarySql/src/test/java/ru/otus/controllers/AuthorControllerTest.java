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
import ru.otus.services.AuthorService;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(AuthorController.class)
class AuthorControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private AuthorService authorService;

    @Test
    void getAll() throws Exception {
        mvc
                .perform(get("/author"))
                .andExpect(ResultMatcher.matchAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON_UTF8)
                        )
                );
        verify(authorService).getAll();
    }

    @Test
    void addAuthor() throws Exception {
        mvc
                .perform(post("/author").param("authorName", "new author"))
                .andExpect(ResultMatcher.matchAll(
                        status().isOk()
                        )
                );
        verify(authorService).addNewAuthorWithName("new author");
    }
}