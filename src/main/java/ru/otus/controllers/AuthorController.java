package ru.otus.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.otus.domain.Author;
import ru.otus.services.AuthorService;

import java.util.List;

@RestController
public class AuthorController {

    private AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping(value = "/author/getAll")
    public List<Author> getAll() {
        return authorService.getAll();
    }

    @PostMapping(value = "/author/add")
    public HttpStatus addAuthor(@RequestParam(name = "authorName") String name) {
        authorService.addNewAuthorWithName(name);
        return HttpStatus.OK;
    }

    @GetMapping(value = "/author/findByName")
    public List<Author> findByName(@RequestParam(name = "authorName") String authorName) {
        return authorService.findByName(authorName);
    }
}