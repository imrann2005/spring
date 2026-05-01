package com.bookstore.controller;

import com.bookstore.service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
@Slf4j
public class HomeController {

    @Autowired
    private BookService bookService;

    /**
     * Home page
     */
    @GetMapping
    public String home(Model model) {
        log.info("Loading home page");
        
        try {
            model.addAttribute("totalBooks", bookService.getTotalBookCount());
            model.addAttribute("popularBooks", bookService.getPopularBooks(4.0));
            model.addAttribute("categories", bookService.getAllCategories());
        } catch (Exception e) {
            log.error("Error loading home page: {}", e.getMessage());
        }
        
        return "home";
    }

    /**
     * About page
     */
    @GetMapping("/about")
    public String about() {
        log.info("Loading about page");
        return "about";
    }

    /**
     * Contact page
     */
    @GetMapping("/contact")
    public String contact() {
        log.info("Loading contact page");
        return "contact";
    }

    /**
     * Terms and conditions page
     */
    @GetMapping("/terms")
    public String terms() {
        log.info("Loading terms page");
        return "terms";
    }

    /**
     * Privacy policy page
     */
    @GetMapping("/privacy")
    public String privacy() {
        log.info("Loading privacy page");
        return "privacy";
    }
}
