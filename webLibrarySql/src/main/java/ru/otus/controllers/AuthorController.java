package ru.otus.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.otus.domain.Author;
import ru.otus.services.AuthorService;

import java.util.List;

@RestController
@PreAuthorize("isAuthenticated()")
public class AuthorController {

    private AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping(value = "/author")
    public List<Author> getAll() {
        return authorService.getAll();
    }

    @PostMapping(value = "/author")
    public ResponseEntity<String> addAuthor(@RequestParam(name = "authorName") String name) {
        authorService.addNewAuthorWithName(name);
        return ResponseEntity.ok("{}");
    }

    @GetMapping(value = "/author/findByName")
    public List<Author> findByName(@RequestParam(name = "authorName") String authorName) {
        return authorService.findByNameIgnoreCase(authorName);
    }
}