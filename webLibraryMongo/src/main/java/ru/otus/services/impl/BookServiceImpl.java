package ru.otus.services.impl;

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

    public BookServiceImpl(BookRepository bookRepository,
                           GenreRepository genreRepository,
                           AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.genreRepository = genreRepository;
        this.authorRepository = authorRepository;
    }

    @Override
    public List<Book> getBookByName(String bookName) {
        return bookRepository.findByNameContains(bookName);
    }

    @Override
    public Book getBookById(String id) {
        return bookRepository.findById(id).orElse(null);
    }

    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public List<Book> getBookByGenre(String genreName) {
        Genre genre = genreRepository.findByName(genreName).orElseThrow(() -> new EntityNotFoundException("genre not found"));
        return bookRepository.findByGenre(genre);
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
    }

    @Override
    public List<Book> getBookByAuthor(String authorName) {
        Author author = authorRepository.findByName(authorName).orElseThrow(() -> new EntityNotFoundException("author not found"));
        return bookRepository.findByAuthorsContains(author);
    }

    @Override
    public void addCommentToBook(String bookId, String comment) {
        Book book = bookRepository
                .findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("book not found"));
        book.getComments().add(new Comment(comment));
        bookRepository.save(book);
    }

    @Override
    public void deleteBook(String id) {
        bookRepository.deleteById(id);
    }

    @Override
    public void addAuthorToBook(String bookId, String authorName) {
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