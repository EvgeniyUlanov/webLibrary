package ru.otus.services;

import ru.otus.domain.Author;

import java.util.List;

public interface AuthorService {

    List<Author> getAll();

    boolean addNewAuthorWithName(String name);

    List<Author> findByName(String authorName);

    List<Author> findByNameContains(String authorName);
}
