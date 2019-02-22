package ru.otus.repositories;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Genre;
import ru.otus.exeptions.EntityNotFoundException;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.core.Is.is;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@ActiveProfiles("springData")
public class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void testSaveAndGetByIdDeleteMethods() {
        Genre genre = entityManager.find(Genre.class, 1L);
        Book book = new Book(genre, "new book");
        bookRepository.save(book);

        Book expected = entityManager.find(Book.class, book.getId());

        assertThat(expected.getName(), is("new book"));
    }

    @Test
    public void testGetByNameMethod() {
        Genre genre = entityManager.find(Genre.class, 1L);
        Book book = new Book(genre, "new book");
        entityManager.persistAndFlush(book);

        Book expected = bookRepository.findByName("new book").orElseThrow(() -> new EntityNotFoundException("book not found"));
        assertThat(expected.getName(), is("new book"));
    }

    @Test
    public void testGetByIdMethod() {
        Genre genre = entityManager.find(Genre.class, 1L);
        Book book = new Book(genre, "new book");
        entityManager.persistAndFlush(book);

        Book expected = bookRepository.findById(book.getId()).orElse(new Book(genre, "wrong book"));
        assertThat(expected.getName(), is("new book"));
    }

    @Test
    public void testDeleteMethod() {
        Genre genre = entityManager.persistFlushFind(new Genre("some genre"));
        Book book = new Book(genre, "new book");
        entityManager.persistAndFlush(book);

        Book expected = entityManager.find(Book.class, book.getId());
        assertThat(expected.getName(), is("new book"));

        bookRepository.delete(book);

        expected = entityManager.find(Book.class, book.getId());
        assertThat(expected, is(nullValue()));
    }

    @Test
    public void testGetAllBooksMethod() {
        Genre genre = entityManager.persistFlushFind(new Genre("some genre"));
        Book testBook1 = entityManager.persistFlushFind(new Book(genre, "testBook1"));
        Book testBook2 = entityManager.persistFlushFind(new Book(genre, "testBook2"));
        Book testBook3 = entityManager.persistFlushFind(new Book(genre, "testBook3"));

        List<Book> bookList = bookRepository.findAll();
        assertThat(bookList, containsInAnyOrder(testBook1, testBook2, testBook3));
    }

    @Test
    public void testGetByGenreMethod() {
        Genre genre = entityManager.persistFlushFind(new Genre("some genre"));
        Book testBook5 = entityManager.persistFlushFind(new Book(genre, "testBook5"));
        Book testBook6 = entityManager.persistFlushFind(new Book(genre, "testBook6"));

        List<Book> bookList = bookRepository.findByGenreName(genre.getName());

        assertThat(bookList, containsInAnyOrder(testBook5, testBook6));
    }

    @Test
    public void testGetByAuthorMethod() {
        Genre genre = entityManager.persistFlushFind(new Genre("some genre"));
        Book someBook1 = new Book(genre, "someBook1");
        Book someBook2 = new Book(genre, "someBook2");
        Author author = entityManager.persistFlushFind(new Author("someAuthor"));
        someBook1.getAuthors().add(author);
        someBook2.getAuthors().add(author);
        entityManager.persistAndFlush(someBook1);
        entityManager.persistAndFlush(someBook2);

        List<Book> bookList = bookRepository.findByAuthorName(author.getName());

        for (Book book : bookList) {
            assertThat(book.getAuthors(), containsInAnyOrder(author));
        }
    }

    @Test
    public void testUpdateMethod() {
        Genre genre = entityManager.persistFlushFind(new Genre("some genre"));
        Book book = new Book(genre, "some book");
        entityManager.persistFlushFind(book);

        book.setName("updated book");

        Book expected = entityManager.find(Book.class, book.getId());
        assertThat(expected.getName(), is("some book"));

        bookRepository.save(book);

        expected = entityManager.find(Book.class, book.getId());
        assertThat(expected.getName(), is("updated book"));
    }
}