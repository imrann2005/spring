package com.bookstore.controller;

import com.bookstore.entity.BookEntity;
import com.bookstore.service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/catalog")
@Slf4j
public class CatalogController {

    @Autowired
    private BookService bookService;

    /**
     * Show all books in catalog
     */
    @GetMapping
    public String showCatalog(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "12") int size,
            Model model) {
        log.info("Loading catalog page");
        
        try {
            List<BookEntity> books = bookService.getAllBooks();
            model.addAttribute("books", books);
            model.addAttribute("categories", bookService.getAllCategories());
            model.addAttribute("authors", bookService.getAllAuthors());
        } catch (Exception e) {
            log.error("Error loading catalog: {}", e.getMessage());
        }
        
        return "catalog";
    }

    /**
     * Search books by keyword
     */
    @GetMapping("/search")
    public String searchBooks(@RequestParam String keyword, Model model) {
        log.info("Searching books with keyword: {}", keyword);
        
        try {
            List<BookEntity> books = bookService.searchBooks(keyword);
            model.addAttribute("books", books);
            model.addAttribute("keyword", keyword);
            model.addAttribute("categories", bookService.getAllCategories());
            model.addAttribute("authors", bookService.getAllAuthors());
        } catch (Exception e) {
            log.error("Error searching books: {}", e.getMessage());
            model.addAttribute("error", "Search failed");
        }
        
        return "catalog";
    }

    /**
     * Filter books by category
     */
    @GetMapping("/category/{category}")
    public String filterByCategory(@PathVariable String category, Model model) {
        log.info("Filtering books by category: {}", category);
        
        try {
            List<BookEntity> books = bookService.getBooksByCategory(category);
            model.addAttribute("books", books);
            model.addAttribute("selectedCategory", category);
            model.addAttribute("categories", bookService.getAllCategories());
            model.addAttribute("authors", bookService.getAllAuthors());
        } catch (Exception e) {
            log.error("Error filtering by category: {}", e.getMessage());
            model.addAttribute("error", "Filter failed");
        }
        
        return "catalog";
    }

    /**
     * Filter books by author
     */
    @GetMapping("/author/{author}")
    public String filterByAuthor(@PathVariable String author, Model model) {
        log.info("Filtering books by author: {}", author);
        
        try {
            List<BookEntity> books = bookService.getBooksByAuthor(author);
            model.addAttribute("books", books);
            model.addAttribute("selectedAuthor", author);
            model.addAttribute("categories", bookService.getAllCategories());
            model.addAttribute("authors", bookService.getAllAuthors());
        } catch (Exception e) {
            log.error("Error filtering by author: {}", e.getMessage());
            model.addAttribute("error", "Filter failed");
        }
        
        return "catalog";
    }

    /**
     * Show book details
     */
    @GetMapping("/book/{id}")
    public String showBookDetails(@PathVariable Long id, Model model) {
        log.info("Loading book details for ID: {}", id);
        
        try {
            Optional<BookEntity> book = bookService.getBookById(id);
            
            if (book.isPresent()) {
                model.addAttribute("book", book.get());
                return "book-details";
            } else {
                log.warn("Book not found with ID: {}", id);
                return "redirect:/catalog";
            }
        } catch (Exception e) {
            log.error("Error loading book details: {}", e.getMessage());
            return "redirect:/catalog";
        }
    }

    /**
     * REST API: Get all books
     */
    @GetMapping("/api/books")
    public org.springframework.web.bind.annotation.ResponseBody List<BookEntity> getAllBooks() {
        log.info("REST: Fetching all books");
        return bookService.getAllBooks();
    }

    /**
     * REST API: Get book by ID
     */
    @GetMapping("/api/books/{id}")
    public org.springframework.web.bind.annotation.ResponseBody Optional<BookEntity> getBook(@PathVariable Long id) {
        log.info("REST: Fetching book with ID: {}", id);
        return bookService.getBookById(id);
    }

    /**
     * REST API: Search books
     */
    @GetMapping("/api/books/search")
    public org.springframework.web.bind.annotation.ResponseBody List<BookEntity> searchAPI(@RequestParam String keyword) {
        log.info("REST: Searching books with keyword: {}", keyword);
        return bookService.searchBooks(keyword);
    }

    /**
     * REST API: Get books by category
     */
    @GetMapping("/api/books/category/{category}")
    public org.springframework.web.bind.annotation.ResponseBody List<BookEntity> getByCategory(@PathVariable String category) {
        log.info("REST: Getting books by category: {}", category);
        return bookService.getBooksByCategory(category);
    }

    /**
     * REST API: Get books by author
     */
    @GetMapping("/api/books/author/{author}")
    public org.springframework.web.bind.annotation.ResponseBody List<BookEntity> getByAuthor(@PathVariable String author) {
        log.info("REST: Getting books by author: {}", author);
        return bookService.getBooksByAuthor(author);
    }
}
