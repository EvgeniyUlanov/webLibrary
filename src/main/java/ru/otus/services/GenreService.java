package ru.otus.services;

import ru.otus.domain.Genre;

import java.util.List;

public interface GenreService {

    List<Genre> getAll();

    boolean addNewGenreWithName(String name);
}
