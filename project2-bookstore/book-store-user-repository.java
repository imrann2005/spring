package com.bookstore.repository;

import com.bookstore.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    /**
     * Find user by username
     */
    Optional<UserEntity> findByUsername(String username);

    /**
     * Find user by email
     */
    Optional<UserEntity> findByEmail(String email);

    /**
     * Check if username exists
     */
    boolean existsByUsername(String username);

    /**
     * Check if email exists
     */
    boolean existsByEmail(String email);

    /**
     * Find all enabled users
     */
    Iterable<UserEntity> findByEnabled(Boolean enabled);
}
