package ru.otus.repositories;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.domain.Author;

/**
 * author's repository, instance create's by spring boot.
 */
@Repository
public interface AuthorRepository extends ReactiveMongoRepository<Author, String> {

    /**
     * method find author by exact name.
     * @param name - exact name.
     * @return author.
     */
    Mono<Author> findByName(String name);

    /**
     * method finds list of authors where author's name contains given name, this method ignore case.
     * @param name - part of author name as regex, example: "%partOfName%"
     * @return list of authors that contains given name.
     */
    Flux<Author> findByNameContains(String name);
}
