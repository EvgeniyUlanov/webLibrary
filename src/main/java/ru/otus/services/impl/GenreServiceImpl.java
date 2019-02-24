package ru.otus.services.impl;

import org.springframework.stereotype.Service;
import ru.otus.domain.Genre;
import ru.otus.repositories.GenreRepository;
import ru.otus.services.GenreService;

import java.util.List;

@Service
public class GenreServiceImpl implements GenreService {

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
            return true;
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return false;
    }
}
