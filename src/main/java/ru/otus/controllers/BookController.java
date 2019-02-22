package ru.otus.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.domain.Book;
import ru.otus.services.AuthorService;
import ru.otus.services.BookService;
import ru.otus.services.GenreService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class BookController {

    private BookService bookService;
    private GenreService genreService;
    private AuthorService authorService;

    public BookController(BookService bookService, GenreService genreService, AuthorService authorService) {
        this.bookService = bookService;
        this.genreService = genreService;
        this.authorService = authorService;
    }

    @GetMapping(value = "/book/all")
    public String getAll(Model model) {
        prepareBasicModel(model, bookService.getAllBooks());
        return "/booksPage";
    }

    @PostMapping(value = "/book/add")
    public String addBook(HttpServletRequest request, Model model) {
        String bookName = request.getParameter("bookName");
        String authorName = request.getParameter("authorName");
        String genreName = request.getParameter("genreName");
        bookService.addBook(bookName, genreName, authorName);
        prepareBasicModel(model, bookService.getAllBooks());
        return "/booksPage";
    }

    @PostMapping(value = "/book/delete")
    public String deleteBook(@RequestParam(value = "book_id") Long id, Model model) {
        bookService.deleteBook(id);
        prepareBasicModel(model, bookService.getAllBooks());
        return "/booksPage";
    }

    @GetMapping(value = "/book/info")
    public String getBookInfo(@RequestParam(value = "book_id") Long id, Model model) {
        model.addAttribute("book", bookService.getBookById(id));
        model.addAttribute("allAuthors", authorService.getAll());
        return "/bookInfoPage";
    }

    @PostMapping(value = "/book/addComment")
    public String addCommentToBook(
            @RequestParam(value = "comment") String comment,
            @RequestParam(value = "bookName") String bookName,
            Model model) {
        bookService.addCommentToBook(bookName, comment);
        model.addAttribute("book", bookService.getBookByName(bookName));
        model.addAttribute("allAuthors", authorService.getAll());
        return "/bookInfoPage";
    }

    @PostMapping(value = "/book/addAuthorToBook")
    public String addAuthorToBook(
            @RequestParam(value = "bookName") String bookName,
            @RequestParam(value = "authorName") String authorName,
            Model model) {
        bookService.addAuthorToBook(bookName, authorName);
        model.addAttribute("book", bookService.getBookByName(bookName));
        model.addAttribute("allAuthors", authorService.getAll());
        return  "/bookInfoPage";
    }

    @GetMapping(value = "/book/findByAuthor")
    public String findByAuthor(@RequestParam(value = "authorName") String authorName, Model model) {
        List<Book> booksByAuthor = bookService.getBookByAuthor(authorName);
        prepareBasicModel(model, booksByAuthor);
        return "/booksPage";
    }

    @GetMapping(value = "/book/findByGenre")
    public String findByGenre(@RequestParam(value = "genreName") String genreName, Model model) {
        List<Book> booksByGenre = bookService.getBookByGenre(genreName);
        prepareBasicModel(model, booksByGenre);
        return "/booksPage";
    }

    private void prepareBasicModel(Model model, List<Book> books) {
        model.addAttribute("genres", genreService.getAll());
        model.addAttribute("books", books);
        model.addAttribute("authors", authorService.getAll());
    }
}
