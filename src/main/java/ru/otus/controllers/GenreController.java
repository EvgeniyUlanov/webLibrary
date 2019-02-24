package ru.otus.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.services.GenreService;

@Controller
public class GenreController {

    private static final String SUCCESS_ACTION = "ok";
    private static final String ERROR_ACTION = "error";

    private GenreService genreService;

    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    @GetMapping(value = "/genre/getAll")
    public String getAllGenres(Model model) {
        model.addAttribute("genres", genreService.getAll());
        return "/genresPage";
    }

    @PostMapping(value = "/genre/add")
    public String addGenre(@RequestParam(name = "genreName") String name, Model model) {
        model.addAttribute("isOk", genreService.addNewGenreWithName(name) ? SUCCESS_ACTION : ERROR_ACTION);
        model.addAttribute("genres", genreService.getAll());
        return "/genresPage";
    }
}