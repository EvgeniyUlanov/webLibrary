package ru.otus.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.services.AuthorService;
import ru.otus.services.BookService;
import ru.otus.services.GenreService;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = "/book")
public class BookController {

    private BookService bookService;
    private GenreService genreService;
    private AuthorService authorService;

    public BookController(BookService bookService, GenreService genreService, AuthorService authorService) {
        this.bookService = bookService;
        this.genreService = genreService;
        this.authorService = authorService;
    }

    @GetMapping(value = "/all")
    public String getAll(Model model) {
        prepareBasicModel(model);
        return "/booksPage";
    }

    @PostMapping(value = "/add")
    public String addBook(HttpServletRequest request, Model model) {
        String bookName = request.getParameter("bookName");
        String authorName = request.getParameter("authorName");
        String genreName = request.getParameter("genreName");
        bookService.addBook(bookName, genreName, authorName);
        prepareBasicModel(model);
        return "/booksPage";
    }

    @PostMapping(value = "/delete")
    public String deleteBook(@RequestParam(value = "book_id") Long id, Model model) {
        bookService.deleteBook(id);
        prepareBasicModel(model);
        return "/booksPage";
    }

    @GetMapping(value = "/info")
    public String getBookInfo(@RequestParam(value = "book_id") Long id, Model model) {
        model.addAttribute("book", bookService.getBookById(id));
        model.addAttribute("allAuthors", authorService.getAll());
        return "/bookInfoPage";
    }

    @PostMapping(value = "/addComment")
    public String addCommentToBook(
            @RequestParam(value = "comment") String comment,
            @RequestParam(value = "bookName") String bookName,
            Model model) {
        bookService.addCommentToBook(bookName, comment);
        model.addAttribute("book", bookService.getBookByName(bookName));
        model.addAttribute("allAuthors", authorService.getAll());
        return "/bookInfoPage";
    }

    @PostMapping(value = "/addAuthorToBook")
    public String addAuthorToBook(
            @RequestParam(value = "bookName") String bookName,
            @RequestParam(value = "authorName") String authorName,
            Model model) {
        bookService.addAuthorToBook(bookName, authorName);
        model.addAttribute("book", bookService.getBookByName(bookName));
        model.addAttribute("allAuthors", authorService.getAll());
        return  "/bookInfoPage";
    }

    private void prepareBasicModel(Model model) {
        model.addAttribute("genres", genreService.getAll());
        model.addAttribute("books", bookService.getAllBooks());
        model.addAttribute("authors", authorService.getAll());
    }
}
