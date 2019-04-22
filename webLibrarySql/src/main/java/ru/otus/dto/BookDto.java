package ru.otus.dto;

import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Comment;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class BookDto {

    private String name;
    private String genre;
    private Set<String> authors;
    private List<String> comments;

    public BookDto(String name, String genre, Set<String> authors, List<String> comments) {
        this.name = name;
        this.genre = genre;
        this.authors = authors;
        this.comments = comments;
    }

    public static BookDto dtoFromBook(Book book) {
        Set<String> authors = book.getAuthors().stream().map(Author::getName).collect(Collectors.toSet());
        List<String> comments = book.getComments().stream().map(Comment::getComment).collect(Collectors.toList());
        return new BookDto(book.getName(), book.getGenre().getName(), authors, comments);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Set<String> getAuthors() {
        return authors;
    }

    public void setAuthors(Set<String> authors) {
        this.authors = authors;
    }

    public List<String> getComments() {
        return comments;
    }

    public void setComments(List<String> comments) {
        this.comments = comments;
    }
}
