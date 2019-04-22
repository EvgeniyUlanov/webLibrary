package ru.otus.services.impl;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Comment;
import ru.otus.domain.Genre;
import ru.otus.exeptions.CreateEntityException;
import ru.otus.exeptions.EntityNotFoundException;
import ru.otus.repositories.AuthorRepository;
import ru.otus.repositories.BookRepository;
import ru.otus.repositories.GenreRepository;
import ru.otus.services.BookService;

import java.util.HashSet;
import java.util.Set;

@Service
public class BookServiceImpl implements BookService {

    private BookRepository bookRepository;
    private GenreRepository genreRepository;
    private AuthorRepository authorRepository;

    public BookServiceImpl(BookRepository bookRepository,
                           GenreRepository genreRepository,
                           AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.genreRepository = genreRepository;
        this.authorRepository = authorRepository;
    }

    @Override
    public Flux<Book> getBookByName(String bookName) {
        return bookRepository.findByNameContains(bookName);
    }

    @Override
    public Mono<Book> getBookById(String id) {
        return bookRepository.findById(id);
    }

    @Override
    public Flux<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public Flux<Book> getBookByGenre(String genreName) {
        Mono<Genre> genre = genreRepository.findByName(genreName);
        return bookRepository.findByGenre(genre);
    }

    @Override
    public Mono<Book> addBook(String bookName, String genre, String authorName) {
        Genre foundedGenre = genreRepository
                .findByName(genre)
                .blockOptional()
                .orElseThrow(() -> new EntityNotFoundException("genre not found"));
        Author author = authorRepository
                .findByName(authorName)
                .blockOptional()
                .orElse(null);
        if (author == null) {
            author = authorRepository.save(new Author(authorName)).block();
        }
        Book book = new Book(foundedGenre, bookName);
        book.getAuthors().add(author);
        return bookRepository.save(book);
    }

    @Override
    public Mono<Book> addBook(Book book) {
        if (bookRepository.findByName(book.getName()).blockOptional().orElse(null) != null) {
            throw new CreateEntityException(String.format("book with name %s already exist", book.getName()));
        }
        Genre foundedGenre = genreRepository.findByName(book.getGenre().getName()).blockOptional().orElse(null);
        if (foundedGenre == null) {
            foundedGenre = genreRepository
                    .save(book.getGenre())
                    .blockOptional()
                    .orElseThrow(() -> new CreateEntityException("error save genre"));
        }
        book.setGenre(foundedGenre);
        Set<Author> foundedAuthors = new HashSet<>();
        for (Author author : book.getAuthors()) {
            Author foundedAuthor = authorRepository.findByName(author.getName()).blockOptional().orElse(null);
            if (foundedAuthor == null) {
                foundedAuthor = authorRepository.save(author).blockOptional().orElseThrow(() -> new CreateEntityException("error save author"));
            }
            foundedAuthors.add(foundedAuthor);
        }
        book.setAuthors(foundedAuthors);
        return bookRepository.save(book);
    }

    @Override
    public Flux<Book> getBookByAuthor(String authorName) {
        return bookRepository.findByAuthorsContains(authorRepository.findByName(authorName));
    }

    @Override
    public Mono<Book> addCommentToBook(String bookId, String comment) {
        Book book = bookRepository
                .findById(bookId)
                .blockOptional()
                .orElseThrow(() -> new EntityNotFoundException("book not found"));
        book.getComments().add(new Comment(comment));
        return bookRepository.save(book);
    }

    @Override
    public Mono<Void> deleteBook(String id) {
        return bookRepository.deleteById(id);
    }

    @Override
    public Mono<Book> addAuthorToBook(String bookId, String authorName) {
        Book book = bookRepository
                .findById(bookId)
                .blockOptional()
                .orElseThrow(() -> new EntityNotFoundException("book not found"));
        Author author = authorRepository
                .findByName(authorName)
                .blockOptional()
                .orElseThrow(() -> new EntityNotFoundException("author not found"));
        book.getAuthors().add(author);
        return bookRepository.save(book);
    }
}