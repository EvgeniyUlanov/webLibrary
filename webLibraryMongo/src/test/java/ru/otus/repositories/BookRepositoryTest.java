package ru.otus.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Genre;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.assertThat;

@ExtendWith(SpringExtension.class)
@DataMongoTest
@DisplayName("book repository test")
class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private MongoTemplate mongoTemplate;

    @BeforeEach
    private void setUpCollection() {
        mongoTemplate.dropCollection("genre");
        mongoTemplate.dropCollection("author");
        mongoTemplate.dropCollection("book");
    }

    @Test
    @DisplayName("finds books by name test")
    void testFindByNameContains() throws InterruptedException {
        Genre genre = mongoTemplate.insert(new Genre("drama"));
        Book first = mongoTemplate.insert(new Book(genre, "first book"));
        Book second = mongoTemplate.insert(new Book(genre, "second book"));
        mongoTemplate.insert(new Book(genre, "third"));

        List<Book> foundedBooks = new ArrayList<>();
        Flux<Book> bookFlux = bookRepository.findByNameContains("book");
        bookFlux.subscribe(foundedBooks::add);

        Thread.sleep(1000);

        assertThat(foundedBooks, containsInAnyOrder(first, second));
    }

    @Test
    @DisplayName("finds all books test")
    void testFindAll() throws InterruptedException {
        Genre genre = mongoTemplate.insert(new Genre("drama"));
        Book first = mongoTemplate.insert(new Book(genre, "first book"));
        Book second = mongoTemplate.insert(new Book(genre, "second book"));
        Book third = mongoTemplate.insert(new Book(genre, "third book"));

        List<Book> foundedBooks = new ArrayList<>();
        Flux<Book> bookFlux = bookRepository.findAll();
        bookFlux.subscribe(foundedBooks::add);

        Thread.sleep(1000);

        assertThat(foundedBooks, containsInAnyOrder(first, second, third));
    }

    @Test
    @DisplayName("finds books by genre test")
    void testFindByGenre() throws InterruptedException {
        Genre drama = mongoTemplate.insert(new Genre("drama"));
        Genre comedy = mongoTemplate.insert(new Genre("drama"));
        Book first = mongoTemplate.insert(new Book(drama, "first book"));
        Book second = mongoTemplate.insert(new Book(drama, "second book"));
        mongoTemplate.insert(new Book(comedy, "third book"));

        List<Book> foundedBooks = new ArrayList<>();
        Flux<Book> bookFlux = bookRepository.findByGenre(Mono.just(drama));
        bookFlux.subscribe(foundedBooks::add);

        Thread.sleep(1000);

        assertThat(foundedBooks, containsInAnyOrder(first, second));
    }

    @Test
    @DisplayName("finds books by author test")
    void testFindByAuthorsContains() throws InterruptedException {
        Genre genre = mongoTemplate.insert(new Genre("drama"));
        Author firstTestAuthor = mongoTemplate.insert(new Author("first author"));
        Author secondTestAuthor = mongoTemplate.insert(new Author("second author"));
        Book first = new Book(genre, "first book");
        Book second = new Book(genre, "second book");
        Book third = new Book(genre, "third book");
        first.getAuthors().add(firstTestAuthor);
        second.getAuthors().add(firstTestAuthor);
        third.getAuthors().add(secondTestAuthor);
        first = mongoTemplate.insert(first);
        second = mongoTemplate.insert(second);
        mongoTemplate.insert(third);

        List<Book> foundedBooks = new ArrayList<>();
        Flux<Book> bookFlux = bookRepository.findByAuthorsContains(Mono.just(firstTestAuthor));
        bookFlux.subscribe(foundedBooks::add);

        Thread.sleep(1000);

        assertThat(foundedBooks, containsInAnyOrder(first, second));
    }
}