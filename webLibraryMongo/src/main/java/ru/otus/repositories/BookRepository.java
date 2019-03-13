package ru.otus.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Genre;

import java.util.List;

@Repository
public interface BookRepository extends MongoRepository<Book, String> {

    List<Book> findByNameContains(String bookName);

    List<Book> findAll();

    List<Book> findByGenre(Genre genre);

    List<Book> findByAuthorsContains(Author author);
}
