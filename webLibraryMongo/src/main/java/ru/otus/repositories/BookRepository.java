package ru.otus.repositories;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Genre;

@Repository
public interface BookRepository extends ReactiveMongoRepository<Book, String> {

    Flux<Book> findByNameContains(String bookName);

    Flux<Book> findAll();

    Flux<Book> findByGenre(Mono<Genre> genre);

    Flux<Book> findByAuthorsContains(Mono<Author> author);
}