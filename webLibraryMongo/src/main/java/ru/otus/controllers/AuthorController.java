package ru.otus.controllers;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import ru.otus.domain.Author;
import ru.otus.services.AuthorService;

@RestController
public class AuthorController {

    private AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping(value = "/author/getAll")
    public Flux<Author> getAll() {
        return authorService.getAll();
    }

    @PostMapping(value = "/author/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> addAuthor(@RequestBody Author author) {
        authorService.addNewAuthor(author);
        return ResponseEntity.ok("{}");
    }

    @GetMapping(value = "/author/findByName")
    public Flux<Author> findByName(@RequestParam(name = "authorName") String authorName) {
        return authorService.findByNameContains(authorName);
    }
}