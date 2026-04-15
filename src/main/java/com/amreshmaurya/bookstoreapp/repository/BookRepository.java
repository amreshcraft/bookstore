package com.amreshmaurya.bookstoreapp.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.amreshmaurya.bookstoreapp.entity.Book;

public interface BookRepository extends JpaRepository<Book,UUID> {
    
}