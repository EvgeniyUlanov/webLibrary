package ru.otus.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.otus.domain.Author;

import java.util.List;
import java.util.Optional;

/**
 * author's repository, instance create's by spring boot.
 */
@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

    /**
     * method find author by exact name.
     * @param name - exact name.
     * @return author.
     */
    Optional<Author> findByName(String name);

    /**
     * method finds list of authors where author's name contains given name, this method ignore case.
     * @param name - part of author name as regex, example: "%partOfName%"
     * @return list of authors that contains given name.
     */
    @Query("select a from Author a where lower(a.name) like lower(:name) order by a.name")
    List<Author> findAuthorByNameIgnoreCase(@Param("name") String name);
}
