package ru.otus.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.otus.domain.Genre;
import ru.otus.services.GenreService;

import java.util.List;

@RestController
public class GenreController {

    private GenreService genreService;

    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    @GetMapping(value = "/genre/getAll")
    public List<Genre> getAllGenres() {
        return genreService.getAll();
    }

    @PostMapping(value = "/genre/add")
    public HttpStatus addGenre(@RequestParam(name = "genreName") String name) {
        genreService.addNewGenreWithName(name);
        return HttpStatus.OK;
    }
}