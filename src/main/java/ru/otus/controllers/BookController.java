package ru.otus.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.otus.services.BookService;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = "/book")
public class BookController {

    private BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping(value = "/all")
    public String getAll(Model model) {
        model.addAttribute("books", bookService.getAllBooks());
        return "/booksPage";
    }

    @PostMapping(value = "/add")
    public String addBook(HttpServletRequest request, Model model) {
        String bookName = request.getParameter("bookName");
        String authorName = request.getParameter("authorName");
        String genreName = request.getParameter("genreName");
        bookService.addBook(bookName, genreName, authorName);
        model.addAttribute("books", bookService.getAllBooks());
        return "/booksPage";
    }
}
