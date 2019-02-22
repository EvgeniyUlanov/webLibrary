package ru.otus.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.otus.services.AuthorService;

@Controller
public class AuthorController {

    private AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping(value = "/author/getAll")
    public String getAll(Model model) {
        model.addAttribute("authors", authorService.getAll());
        return "/authorsPage";
    }

    @PostMapping(value = "/author/add")
    public String addAuthor(@RequestParam(name = "authorName") String name, Model model) {
        model.addAttribute("message",
                authorService.addNewAuthorWithName(name) ? "author was added" : "author was not added");
        model.addAttribute("authors", authorService.getAll());
        return "/authorsPage";
    }
}