package ru.otus.services;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.domain.Genre;

public interface GenreService {

    Flux<Genre> getAll();

    Mono<Genre> createNewGenre(Genre genre);
}
