package ru.otus.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.domain.GrantedAuthoritySid;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.jdbc.JdbcMutableAclService;
import org.springframework.security.acls.model.MutableAcl;
import org.springframework.security.acls.model.NotFoundException;
import org.springframework.security.acls.model.ObjectIdentity;
import org.springframework.security.acls.model.Sid;
import org.springframework.stereotype.Service;
import ru.otus.exeptions.EntityNotFoundException;
import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Comment;
import ru.otus.domain.Genre;
import ru.otus.repositories.AuthorRepository;
import ru.otus.repositories.BookRepository;
import ru.otus.repositories.GenreRepository;
import ru.otus.services.BookService;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    private BookRepository bookRepository;
    private GenreRepository genreRepository;
    private AuthorRepository authorRepository;
    private JdbcMutableAclService aclService;

    public BookServiceImpl(BookRepository bookRepository,
                           GenreRepository genreRepository,
                           AuthorRepository authorRepository,
                           JdbcMutableAclService aclService) {
        this.bookRepository = bookRepository;
        this.genreRepository = genreRepository;
        this.authorRepository = authorRepository;
        this.aclService = aclService;
    }

    @Override
    public List<Book> getBookByName(String bookName) {
        return bookRepository.findByName("%" + bookName + "%");
    }

    @Override
    public Book getBookById(Long id) {
        return bookRepository.findById(id).orElse(null);
    }

    @Override
    @PostFilter("hasPermission(filterObject, 'READ')")
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public List<Book> getBookByGenre(String genre) {
        return bookRepository.findByGenreName("%" + genre + "%");
    }

    @Override
    public void addBook(String bookName, String genre, String authorName) {
        Genre foundedGenre = genreRepository
                .findByName(genre)
                .orElseThrow(() -> new EntityNotFoundException("genre not found"));
        Author foundedAuthor;
        try {
            foundedAuthor = authorRepository
                    .findByName(authorName)
                    .orElseThrow(() -> new EntityNotFoundException("author not found"));
        } catch (EntityNotFoundException e) {
            foundedAuthor = new Author(authorName);
            authorRepository.save(foundedAuthor);
        }
        Book book = new Book(foundedGenre, bookName);
        book.getAuthors().add(foundedAuthor);
        bookRepository.save(book);
        Sid sid = new GrantedAuthoritySid("ROLE_USER");
        Sid admin = new GrantedAuthoritySid("ROLE_ADMIN");
        ObjectIdentity objectIdentity = new ObjectIdentityImpl(book.getClass(), book.getId());
        MutableAcl acl;
        try {
            acl = (MutableAcl) aclService.readAclById(objectIdentity);
        } catch (NotFoundException e) {
            acl = aclService.createAcl(objectIdentity);
        }
        acl.insertAce(acl.getEntries().size(), BasePermission.READ, sid, true);
        acl.insertAce(acl.getEntries().size(), BasePermission.ADMINISTRATION, admin, true);
        aclService.updateAcl(acl);
    }

    @Override
    public List<Book> getBookByAuthor(String authorName) {
        return bookRepository.findByAuthorName("%" + authorName + "%");
    }

    @Override
    public void addCommentToBook(Long bookId, String comment) {
        Book book = bookRepository
                .findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("book not found"));
        book.getComments().add(new Comment(comment));
        bookRepository.save(book);
    }

    @Override
    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }

    @Override
    public void addAuthorToBook(Long bookId, String authorName) {
        Book book = bookRepository
                .findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("book not found"));
        Author author = authorRepository
                .findByName(authorName)
                .orElseThrow(() -> new EntityNotFoundException("author not found"));
        book.getAuthors().add(author);
        bookRepository.save(book);
    }
}