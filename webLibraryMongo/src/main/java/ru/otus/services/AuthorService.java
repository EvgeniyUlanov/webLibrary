package ru.otus.services;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.domain.Author;

public interface AuthorService {

    Flux<Author> getAll();

    Mono<Author> findByName(String authorName);

    Flux<Author> findByNameContains(String authorName);

    void addNewAuthor(Author author);
}
