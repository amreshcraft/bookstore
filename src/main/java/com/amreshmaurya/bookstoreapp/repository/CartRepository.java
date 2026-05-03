package com.amreshmaurya.bookstoreapp.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.amreshmaurya.bookstoreapp.entity.Cart;

public interface CartRepository extends JpaRepository<Cart, UUID> {
    Optional<Cart> findByUserIdAndIsActiveTrue(UUID userId);
}

