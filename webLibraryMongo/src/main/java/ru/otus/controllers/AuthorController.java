package ru.otus.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.domain.Author;
import ru.otus.services.AuthorService;

import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@RestController
public class AuthorController {

    private AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping(value = "/author")
    public Flux<Author> getAll() {
        return authorService.getAll();
    }

    @PostMapping(value = "/author", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ServerResponse> addAuthor(@RequestBody Author author) {
        return authorService
                .addNewAuthor(author)
                .then(ok().build());
    }

    @GetMapping(value = "/author/findByName")
    public Flux<Author> findByName(@RequestParam(name = "authorName") String authorName) {
        return authorService.findByNameContains(authorName);
    }
}