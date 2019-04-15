package ru.otus.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.domain.GrantedAuthoritySid;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.domain.PrincipalSid;
import org.springframework.security.acls.jdbc.JdbcMutableAclService;
import org.springframework.security.acls.model.MutableAcl;
import org.springframework.security.acls.model.NotFoundException;
import org.springframework.security.acls.model.ObjectIdentity;
import org.springframework.security.acls.model.Sid;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.otus.domain.Book;
import ru.otus.services.BookService;

import java.util.List;

@RestController
@PreAuthorize("isAuthenticated()")
public class BookController {

    private BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping(value = "/book")
    public List<Book> getAll() {
        return bookService.getAllBooks();
    }

    @PostMapping(value = "/book")
    @Transactional
    public ResponseEntity<String> addBook(
            @RequestParam(value = "bookName") String bookName,
            @RequestParam(value = "authorName") String authorName,
            @RequestParam(value = "genreName") String genreName) {
        bookService.addBook(bookName, genreName, authorName);
        return ResponseEntity.ok("{}");
    }

    @DeleteMapping(value = "/book/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> deleteBook(@PathVariable(value = "id") Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.ok("{}");
    }

    @GetMapping(value = "/book/{id}")
    public Book getBookInfo(@PathVariable(value = "id") Long id) {
        return bookService.getBookById(id);
    }

    @PostMapping(value = "/book/addComment")
    public ResponseEntity<String> addCommentToBook(
            @RequestParam(value = "comment") String comment,
            @RequestParam(value = "book_id") Long bookId) {
        bookService.addCommentToBook(bookId, comment);
        return ResponseEntity.ok("{}");
    }

    @PostMapping(value = "/book/addAuthorToBook")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> addAuthorToBook(
            @RequestParam(value = "book_id") Long bookId,
            @RequestParam(value = "authorName") String authorName,
            Model model) {
        bookService.addAuthorToBook(bookId, authorName);
        return ResponseEntity.ok("{}");
    }

    @GetMapping(value = "/book/findByAuthor")
    public List<Book> findByAuthor(@RequestParam(value = "authorName") String authorName) {
        return bookService.getBookByAuthor(authorName);
    }

    @GetMapping(value = "/book/findByGenre")
    public List<Book> findByGenre(@RequestParam(value = "genreName") String genreName) {
        return bookService.getBookByGenre(genreName);
    }

    @GetMapping(value = "book/findByName")
    public List<Book> findByName(@RequestParam(value = "bookName") String bookName) {
        return bookService.getBookByName(bookName);
    }
}