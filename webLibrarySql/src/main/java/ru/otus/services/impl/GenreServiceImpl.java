package ru.otus.services.impl;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.otus.domain.Genre;
import ru.otus.repositories.GenreRepository;
import ru.otus.services.GenreService;

import java.util.List;
import java.util.logging.Logger;

@Service
public class GenreServiceImpl implements GenreService {

    private final org.slf4j.Logger logger = LoggerFactory.getLogger(Logger.class);

    private GenreRepository genreRepository;

    public GenreServiceImpl(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    @Override
    public List<Genre> getAll() {
        return genreRepository.findAll();
    }

    @Override
    public boolean addNewGenreWithName(String name) {
        try {
            genreRepository.save(new Genre(name));
            logger.info(String.format("genre with name %s was added", name));
            return true;
        } catch (Exception e) {
            logger.info(String.format("genre was not added: %s", e.getMessage()));
        }
        return false;
    }
}
