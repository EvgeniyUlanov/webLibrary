package ru.otus.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @GetMapping(value = "/genre")
    @PreAuthorize("isAuthenticated()")
    public List<Genre> getAllGenres() {
        return genreService.getAll();
    }

    @PostMapping(value = "/genre")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> addGenre(@RequestParam(name = "genreName") String name) {
        genreService.addNewGenreWithName(name);
        return ResponseEntity.ok("{}");
    }
}