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
import ru.otus.domain.Author;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;

@ExtendWith(SpringExtension.class)
@DataMongoTest
@DisplayName("author repository test")
class AuthorRepositoryTest {

    @Autowired
    private AuthorRepository authorMongoRepository;
    @Autowired
    private MongoTemplate mongoTemplate;

    @BeforeEach
    private void setUpCollection() {
        mongoTemplate.dropCollection("author");
    }

    @Test
    @DisplayName("find by exact name test")
    void findByName() {
        Author author = new Author("test author");
        mongoTemplate.insert(author);

        Author expected = authorMongoRepository.findByName("test author").blockOptional().orElse(new Author("empty"));

        assertThat(expected, is(author));
    }

    @Test
    @DisplayName("find by part of name test")
    void findByNameContains() throws InterruptedException {
        Author first = new Author("author first");
        Author second = new Author("author second");
        Author test = new Author("test");
        mongoTemplate.insert(first);
        mongoTemplate.insert(second);
        mongoTemplate.insert(test);

        List<Author> foundedAuthors = new ArrayList<>();
        Flux<Author> expected = authorMongoRepository.findByNameContains("author");
        expected.subscribe(foundedAuthors::add);

        Thread.sleep(1000);

        assertThat(foundedAuthors, containsInAnyOrder(first, second));
    }
}