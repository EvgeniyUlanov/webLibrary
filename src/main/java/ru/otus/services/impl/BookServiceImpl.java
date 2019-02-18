package ru.otus.services.impl;

import org.springframework.context.annotation.Profile;
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

import java.util.ArrayList;
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
    public Book getBookByName(String bookName) {
        return bookRepository.findByName(bookName).orElse(null);
    }

    @Override
    public Book getBookById(Long id) {
        return bookRepository.findById(id).orElse(null);
    }

    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public List<Book> getBookByGenre(String genre) {
        return bookRepository.findByGenreName(genre);
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
        return bookRepository.findByAuthorName(authorName);
    }

    @Override
    public void addCommentToBook(String bookName, String comment) {
        Book book = bookRepository
                .findByName(bookName)
                .orElseThrow(() -> new EntityNotFoundException("book not found"));
        book.getComments().add(new Comment(comment));
        bookRepository.save(book);
    }

    @Override
    public List<String> getCommentsByBook(String bookName) {
        Book book = bookRepository
                .findByName(bookName)
                .orElseThrow(() -> new EntityNotFoundException("book not found"));
        List<String> stringComments = new ArrayList<>();
        for (Comment comment : book.getComments()) {
            stringComments.add(comment.getComment());
        }
        return stringComments;
    }

    @Override
    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }

    @Override
    public void addAuthorToBook(String bookName, String authorName) {
        Book book = bookRepository
                .findByName(bookName)
                .orElseThrow(() -> new EntityNotFoundException("book not found"));
        Author author = authorRepository
                .findByName(authorName)
                .orElseThrow(() -> new EntityNotFoundException("author not found"));
        book.getAuthors().add(author);
        bookRepository.save(book);
    }
}