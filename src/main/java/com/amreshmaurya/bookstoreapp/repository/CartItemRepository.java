package com.amreshmaurya.bookstoreapp.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.amreshmaurya.bookstoreapp.entity.CartItem;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, UUID> {

    Optional<CartItem> findByCartIdAndBookId(UUID cartId, UUID bookId);

    List<CartItem> findByCartId(UUID cartId);
}
