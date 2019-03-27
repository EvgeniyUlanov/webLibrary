package ru.otus.repositories;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.domain.Author;
import ru.otus.exeptions.EntityNotFoundException;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@ActiveProfiles("emptyH2")
public class AuthorRepositoryTest {

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void testAddAuthorMethod() {
        Author author = new Author("newAuthor");
        authorRepository.save(author);

        Author expected = entityManager.find(Author.class, author.getId());

        assertThat(expected.getName(), is("newAuthor"));
    }

    @Test
    public void testGetByNameMethod() {
        Author author = new Author("someAuthor");
        entityManager.persistAndFlush(author);

        Author expected = authorRepository.findByName("someAuthor").orElseThrow(() -> new EntityNotFoundException("author not found"));

        assertThat(expected, is(notNullValue()));
    }

    @Test
    public void testGetByIdMethod() {
        Author author = new Author("someAuthor");
        entityManager.persistAndFlush(author);

        Author expected = authorRepository.findById(author.getId()).orElse(new Author("wrong author"));
        assertThat(expected.getName(), is("someAuthor"));
    }

    @Test
    public void testDeleteMethod() {
        Author author = new Author("newAuthor");
        author = entityManager.persistFlushFind(author);
        Long id = author.getId();

        assertThat(author, is(notNullValue()));

        authorRepository.delete(author);
        Author expected = entityManager.find(Author.class, id);

        assertThat(expected, is(nullValue()));
    }

    @Test
    @SuppressWarnings("JpaQlInspection")
    public void testGetAllMethod() {
        Author testAuthor1 = entityManager.persistAndFlush(new Author("testAuthor1"));
        Author testAuthor2 = entityManager.persistFlushFind(new Author("testAuthor2"));

        List<Author> authorList = authorRepository.findAll();
        assertThat(authorList, containsInAnyOrder(testAuthor1, testAuthor2));
    }

    @Test
    public void testUpdateMethod() {
        Author author = new Author("newAuthor");
        entityManager.persistAndFlush(author);
        entityManager.detach(author);

        author.setName("updatedName");

        Author expected = entityManager.find(Author.class, author.getId());
        assertThat(expected.getName(), is("newAuthor"));

        authorRepository.save(author);
        expected = entityManager.find(Author.class, author.getId());
        assertThat(expected.getName(), is("updatedName"));
    }
}