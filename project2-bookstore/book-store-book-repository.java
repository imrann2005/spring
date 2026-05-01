package com.bookstore.repository;

import com.bookstore.entity.BookEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<BookEntity, Long> {

    /**
     * Find book by ISBN
     */
    Optional<BookEntity> findByIsbn(String isbn);

    /**
     * Find books by category
     */
    List<BookEntity> findByCategory(String category);

    /**
     * Find books by author
     */
    List<BookEntity> findByAuthor(String author);

    /**
     * Find books by title containing keyword
     */
    List<BookEntity> findByTitleContainingIgnoreCase(String keyword);

    /**
     * Search books by title or author
     */
    @Query("SELECT b FROM BookEntity b WHERE LOWER(b.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(b.author) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<BookEntity> searchBooks(@Param("keyword") String keyword);

    /**
     * Find books by category with pagination
     */
    Page<BookEntity> findByCategory(String category, Pageable pageable);

    /**
     * Find all books with stock availability
     */
    List<BookEntity> findByStockQuantityGreaterThan(Integer quantity);

    /**
     * Find books with rating greater than specified value
     */
    List<BookEntity> findByRatingGreaterThanEqual(Double rating);

    /**
     * Find books by author with pagination
     */
    Page<BookEntity> findByAuthor(String author, Pageable pageable);

    /**
     * Get categories distinct
     */
    @Query("SELECT DISTINCT b.category FROM BookEntity b")
    List<String> findAllCategories();

    /**
     * Get authors distinct
     */
    @Query("SELECT DISTINCT b.author FROM BookEntity b")
    List<String> findAllAuthors();

    /**
     * Check if ISBN exists
     */
    boolean existsByIsbn(String isbn);

    /**
     * Find available books
     */
    List<BookEntity> findByStockQuantityGreaterThanOrderByTitleAsc(Integer quantity);
}
