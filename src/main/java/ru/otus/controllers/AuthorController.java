package ru.otus.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.services.AuthorService;

@Controller
@RequestMapping(value = "/author")
public class AuthorController {

    private AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    public String getAll(Model model) {
        model.addAttribute("authors", authorService.getAll());
        return "/authorsPage";
    }

    @PostMapping(value = "/add")
    public String addAuthor(@RequestParam(name = "authorName") String name, Model model) {
        model.addAttribute("message",
                authorService.addNewAuthorWithName(name) ? "author was added" : "author was not added");
        model.addAttribute("authors", authorService.getAll());
        return "/authorsPage";
    }
}