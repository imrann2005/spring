package com.bookstore.service;

import com.bookstore.dto.BookDTO;
import com.bookstore.entity.BookEntity;
import com.bookstore.repository.BookRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@Slf4j
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    /**
     * Add a new book
     */
    public BookEntity addBook(BookEntity book) {
        log.info("Adding new book: {}", book.getTitle());
        
        if (bookRepository.existsByIsbn(book.getIsbn())) {
            throw new IllegalArgumentException("Book with ISBN already exists");
        }
        
        BookEntity savedBook = bookRepository.save(book);
        log.info("Book added with ID: {}", savedBook.getId());
        return savedBook;
    }

    /**
     * Get book by ID
     */
    public Optional<BookEntity> getBookById(Long id) {
        log.info("Fetching book with ID: {}", id);
        return bookRepository.findById(id);
    }

    /**
     * Get all books
     */
    public List<BookEntity> getAllBooks() {
        log.info("Fetching all books");
        return bookRepository.findAll();
    }

    /**
     * Get books by category
     */
    public List<BookEntity> getBooksByCategory(String category) {
        log.info("Fetching books for category: {}", category);
        return bookRepository.findByCategory(category);
    }

    /**
     * Get books by author
     */
    public List<BookEntity> getBooksByAuthor(String author) {
        log.info("Fetching books by author: {}", author);
        return bookRepository.findByAuthor(author);
    }

    /**
     * Search books by keyword
     */
    public List<BookEntity> searchBooks(String keyword) {
        log.info("Searching books with keyword: {}", keyword);
        return bookRepository.searchBooks(keyword);
    }

    /**
     * Get all categories
     */
    public List<String> getAllCategories() {
        log.info("Fetching all categories");
        return bookRepository.findAllCategories();
    }

    /**
     * Get all authors
     */
    public List<String> getAllAuthors() {
        log.info("Fetching all authors");
        return bookRepository.findAllAuthors();
    }

    /**
     * Update book
     */
    public BookEntity updateBook(Long id, BookEntity bookDetails) {
        log.info("Updating book with ID: {}", id);
        
        BookEntity book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found with ID: " + id));

        if (bookDetails.getTitle() != null) {
            book.setTitle(bookDetails.getTitle());
        }
        if (bookDetails.getAuthor() != null) {
            book.setAuthor(bookDetails.getAuthor());
        }
        if (bookDetails.getDescription() != null) {
            book.setDescription(bookDetails.getDescription());
        }
        if (bookDetails.getCategory() != null) {
            book.setCategory(bookDetails.getCategory());
        }
        if (bookDetails.getPrice() != null) {
            book.setPrice(bookDetails.getPrice());
        }
        if (bookDetails.getStockQuantity() != null) {
            book.setStockQuantity(bookDetails.getStockQuantity());
        }

        BookEntity updatedBook = bookRepository.save(book);
        log.info("Book updated with ID: {}", id);
        return updatedBook;
    }

    /**
     * Delete book
     */
    public void deleteBook(Long id) {
        log.info("Deleting book with ID: {}", id);
        
        if (!bookRepository.existsById(id)) {
            throw new RuntimeException("Book not found with ID: " + id);
        }
        
        bookRepository.deleteById(id);
        log.info("Book deleted with ID: {}", id);
    }

    /**
     * Get total book count
     */
    public long getTotalBookCount() {
        return bookRepository.count();
    }

    /**
     * Convert BookEntity to DTO
     */
    public BookDTO convertToDTO(BookEntity book) {
        BookDTO dto = new BookDTO();
        dto.setId(book.getId());
        dto.setTitle(book.getTitle());
        dto.setAuthor(book.getAuthor());
        dto.setIsbn(book.getIsbn());
        dto.setDescription(book.getDescription());
        dto.setCategory(book.getCategory());
        dto.setPrice(book.getPrice());
        dto.setStockQuantity(book.getStockQuantity());
        dto.setPublisher(book.getPublisher());
        dto.setPublishedDate(book.getPublishedDate());
        dto.setRating(book.getRating());
        dto.setReviewCount(book.getReviewCount());
        dto.setImageUrl(book.getImageUrl());
        dto.setCreatedAt(book.getCreatedAt());
        dto.setUpdatedAt(book.getUpdatedAt());
        return dto;
    }
}
