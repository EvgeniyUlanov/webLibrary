package ru.otus.repositories;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.domain.Genre;
import ru.otus.exeptions.EntityNotFoundException;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@ActiveProfiles("springData")
public class GenreRepositoryTest {

    @Autowired
    private GenreRepository genreRepository;
    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void testSaveMethod() {
        Genre genre = new Genre("newGenre");
        genreRepository.save(genre);

        Genre founded = entityManager.find(Genre.class, genre.getId());
        assertThat(founded, is(genre));
    }

    @Test
    public void testGetByIdMethod() {
        Genre genre = entityManager.persistFlushFind(new Genre("new genre"));

        Genre foundedById = genreRepository.findById(genre.getId()).orElse(new Genre("wrong genre"));
        assertThat(foundedById.getName(), is("new genre"));
    }

    @Test
    public void testGetByName() {
        entityManager.persist(new Genre("new genre"));

        Genre foundedByName = genreRepository.findByName("new genre").orElseThrow(() -> new EntityNotFoundException("genre not found"));
        assertThat(foundedByName.getName(), is("new genre"));
    }

    @Test
    public void testDeleteMethod() {
        Genre genre = entityManager.persistFlushFind(new Genre("new genre"));

        assertThat(genre, is(notNullValue()));

        genreRepository.delete(genre);
        Genre expected = entityManager.find(Genre.class, genre.getId());

        assertThat(expected, is(nullValue()));
    }

    @Test
    public void testGetAllMethod() {
        Genre drama = entityManager.persistFlushFind(new Genre("Drama"));
        Genre comedy = entityManager.persistFlushFind(new Genre("Comedy"));
        Genre poem = entityManager.persistFlushFind(new Genre("Poem"));

        List<Genre> genreList = genreRepository.findAll();

        assertThat(genreList, containsInAnyOrder(drama, comedy, poem));
    }
}
