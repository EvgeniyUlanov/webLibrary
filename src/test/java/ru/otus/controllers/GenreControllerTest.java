package ru.otus.controllers;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import ru.otus.services.GenreService;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(GenreController.class)
class GenreControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private GenreService genreService;

    @Test
    void getAllGenres() throws Exception {
        mvc
                .perform(get("/genre/getAll"))
                .andExpect(ResultMatcher.matchAll(
                        status().isOk(),
                        content().contentType("application/json;charset=UTF-8")
                        )
                );
        verify(genreService).getAll();
    }

    @Test
    void addGenre() throws Exception {
        mvc
                .perform(post("/genre/add").param("genreName", "genre"))
                .andExpect(ResultMatcher.matchAll(
                        status().isOk()
                        )
                );
        verify(genreService).addNewGenreWithName("genre");
    }
}