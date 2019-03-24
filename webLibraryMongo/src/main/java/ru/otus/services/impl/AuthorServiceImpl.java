package ru.otus.services.impl;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.domain.Author;
import ru.otus.repositories.AuthorRepository;
import ru.otus.services.AuthorService;

@Service
public class AuthorServiceImpl implements AuthorService {

    private AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public Flux<Author> getAll() {
        return authorRepository.findAll();
    }

    @Override
    public Mono<Author> findByName(String authorName) {
        return authorRepository.findByName(authorName);
    }

    @Override
    public Flux<Author> findByNameContains(String authorName) {
        return authorRepository.findByNameContains(authorName);
    }

    @Override
    public Mono<Author> addNewAuthor(Author author) {
        return authorRepository.save(author);
    }
}
