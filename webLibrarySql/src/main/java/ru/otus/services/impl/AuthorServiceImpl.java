package ru.otus.services.impl;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.otus.domain.Author;
import ru.otus.exeptions.EntityNotFoundException;
import ru.otus.repositories.AuthorRepository;
import ru.otus.services.AuthorService;

import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final org.slf4j.Logger logger = LoggerFactory.getLogger(Logger.class);

    private AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    @HystrixCommand
    public List<Author> getAll() {
        return authorRepository.findAll();
    }

    @Override
    @HystrixCommand
    public boolean addNewAuthorWithName(String name) {
        try {
            authorRepository.save(new Author(name));
            logger.info(String.format("author with name %s was added", name));
            return true;
        } catch (Exception e) {
            logger.error(String.format("error added author: %s", e.getMessage()));
        }
        return false;
    }

    @Override
    @HystrixCommand
    public List<Author> findByName(String authorName) {
        return Collections.singletonList(authorRepository.findByName(authorName).orElseThrow(() ->new EntityNotFoundException("author not found")));
    }

    @Override
    @HystrixCommand
    public List<Author> findByNameIgnoreCase(String authorName) {
        return authorRepository.findAuthorByNameIgnoreCase("%" + authorName + "%");
    }
}
