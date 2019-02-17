package ru.otus.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.services.GenreService;

@Controller
@RequestMapping("/genre")
public class GenreController {

    private GenreService genreService;

    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    @GetMapping(value = "/getAll")
    public String getAllGenres(Model model) {
        model.addAttribute("genres", genreService.getAll());
        return "/genresPage";
    }

    @PostMapping(value = "/add")
    public String addGenre(@RequestParam(name = "genreName") String name, Model model) {
        model.addAttribute("message",
                genreService.addNewGenreWithName(name) ? "genre was added" : "genre wasn't added");
        model.addAttribute("genres", genreService.getAll());
        return "/genresPage";
    }
}