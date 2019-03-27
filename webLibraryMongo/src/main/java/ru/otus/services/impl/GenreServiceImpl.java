package ru.otus.services.impl;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.domain.Genre;
import ru.otus.repositories.GenreRepository;
import ru.otus.services.GenreService;

@Service
public class GenreServiceImpl implements GenreService {

    private GenreRepository genreRepository;

    public GenreServiceImpl(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    @Override
    public Flux<Genre> getAll() {
        return genreRepository.findAll();
    }

    @Override
    public Mono<Genre> createNewGenre(Genre genre) {
        if (genre.getId() == null && genre.getName() != null) {
            return genreRepository.save(genre);
        }
        return Mono.empty();
    }
}
