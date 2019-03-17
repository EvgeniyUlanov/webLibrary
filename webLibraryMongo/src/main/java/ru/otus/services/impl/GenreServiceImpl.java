package ru.otus.services.impl;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
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
    public void createNewGenre(Genre genre) {
        if (genre.getId() == null && genre.getName() != null) {
            genreRepository.save(genre).subscribe();
        }
    }
}
