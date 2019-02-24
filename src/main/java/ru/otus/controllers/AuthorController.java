package ru.otus.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.otus.services.AuthorService;

@Controller
public class AuthorController {

    private static final String SUCCESS_MESSAGE = "ok";
    private static final String ERROR_MESSAGE ="error";

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
        boolean result = authorService.addNewAuthorWithName(name);
        model.addAttribute("isOk", result ? SUCCESS_MESSAGE : ERROR_MESSAGE);
        model.addAttribute("authors", authorService.getAll());
        return "/authorsPage";
    }
}