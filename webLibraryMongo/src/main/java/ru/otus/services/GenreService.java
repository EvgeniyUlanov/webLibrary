package ru.otus.services;

import reactor.core.publisher.Flux;
import ru.otus.domain.Genre;

public interface GenreService {

    Flux<Genre> getAll();

    void createNewGenre(Genre genre);
}
