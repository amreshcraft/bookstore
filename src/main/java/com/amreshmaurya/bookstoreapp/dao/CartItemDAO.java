package com.amreshmaurya.bookstoreapp.dao;


import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.amreshmaurya.bookstoreapp.entity.CartItem;
import com.amreshmaurya.bookstoreapp.repository.CartItemRepository;

@Component
public class CartItemDAO {

    private final CartItemRepository cartItemRepository;

    public CartItemDAO(CartItemRepository cartItemRepository) {
        this.cartItemRepository = cartItemRepository;
    }

    public CartItem save(CartItem cartItem) {
        return cartItemRepository.save(cartItem);
    }

    public Optional<CartItem> findById(UUID id) {
        return cartItemRepository.findById(id);
    }

    public Optional<CartItem> findByCartIdAndBookId(UUID cartId, UUID bookId) {
        return cartItemRepository.findByCartIdAndBookId(cartId, bookId);
    }

    public List<CartItem> findByCartId(UUID cartId) {
        return cartItemRepository.findByCartId(cartId);
    }

    public void delete(CartItem cartItem) {
        cartItemRepository.delete(cartItem);
    }

    public void deleteAll(List<CartItem> items) {
        cartItemRepository.deleteAll(items);
    }
}
