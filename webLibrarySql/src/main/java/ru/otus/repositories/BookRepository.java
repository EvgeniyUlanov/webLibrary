package ru.otus.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.domain.Book;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends CrudRepository<Book, Long> {

    @Query("select b from Book b where lower(b.name) like lower(:bookName)")
    List<Book> findByName(@Param("bookName") String bookName);

    List<Book> findAll();

    @Query("select b from Book b join b.genre g where lower(g.name) like lower(:genre)")
    List<Book> findByGenreName(@Param("genre") String genre);

    @Query("select b from Book b join b.authors a where lower(a.name) like lower(:authorName)")
    List<Book> findByAuthorName(@Param("authorName") String authorName);
}
