package ru.otus.services.impl;

import org.springframework.stereotype.Service;
import ru.otus.domain.Author;
import ru.otus.repositories.AuthorRepository;
import ru.otus.services.AuthorService;

import java.util.List;

@Service
public class AuthorServiceImpl implements AuthorService {

    private AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public List<Author> getAll() {
        return authorRepository.findAll();
    }

    @Override
    public boolean addNewAuthorWithName(String name) {
        try {
            authorRepository.save(new Author(name));
            return true;
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return false;
    }
}
