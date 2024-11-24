package com.BookMgtSystem.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.BookMgtSystem.Entity.Book;
import com.BookMgtSystem.Service.BookService;

@Controller
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping
    public String listBooks(Model model) {
        model.addAttribute("books", bookService.getAllBooks());
        return "books";
    }

    @PostMapping
    public String addBook(@ModelAttribute Book book) {
        bookService.saveBook(book);
        return "redirect:/books";
    }

    @PostMapping("/delete/{id}")
    public String deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return "redirect:/books";
    }

    @GetMapping("/search")
    public String searchBooks(@RequestParam(value = "filter", required = false) String filter,
                              @RequestParam(value = "query", required = false) String query,
                              Model model) {
        if ("title".equalsIgnoreCase(filter)) {
            model.addAttribute("books", bookService.searchByTitle(query));
        } else if ("author".equalsIgnoreCase(filter)) {
            model.addAttribute("books", bookService.searchByAuthor(query));
        } else if ("genre".equalsIgnoreCase(filter)) {
            model.addAttribute("books", bookService.searchByGenre(query));
        } else {
            model.addAttribute("books", bookService.getAllBooks());
        }
        return "search";
    }
}
