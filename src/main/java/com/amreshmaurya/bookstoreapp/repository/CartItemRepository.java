package com.amreshmaurya.bookstoreapp.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.amreshmaurya.bookstoreapp.entity.Book;
import com.amreshmaurya.bookstoreapp.entity.Cart;
import com.amreshmaurya.bookstoreapp.entity.CartItem;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, UUID> {
    Optional<CartItem> findByCartAndBook(Cart cart, Book book);
}
