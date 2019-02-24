package ru.otus.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.otus.domain.Book;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends CrudRepository<Book, Long> {

    Optional<Book> findByName(String bookName);

    List<Book> findAll();

    List<Book> findByGenreName(String genre);

    @Query("select b from Book b join b.authors a where a.name = :authorName")
    List<Book> findByAuthorName(@Param("authorName") String authorName);
}
