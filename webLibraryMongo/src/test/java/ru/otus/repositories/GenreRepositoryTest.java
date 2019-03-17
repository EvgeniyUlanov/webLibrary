package ru.otus.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.domain.Genre;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@ExtendWith(SpringExtension.class)
@DataMongoTest
@DisplayName("genre repository test")
class GenreRepositoryTest {

    @Autowired
    private GenreRepository genreRepository;
    @Autowired
    private MongoTemplate mongoTemplate;

    @BeforeEach
    private void setUpCollection() {
        mongoTemplate.dropCollection("genre");
    }

    @Test
    @DisplayName("finds by name test")
    void findByNameTest() {
        Genre genre = mongoTemplate.insert(new Genre("drama"));

        Genre expected = genreRepository.findByName("drama").blockOptional().orElse(new Genre("empty"));

        assertThat(expected, is(genre));
    }
}