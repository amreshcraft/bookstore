package com.amreshmaurya.bookstoreapp.dao;


import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.amreshmaurya.bookstoreapp.entity.Cart;
import com.amreshmaurya.bookstoreapp.repository.CartRepository;



@Component
public class CartDAO {

    private final CartRepository cartRepository;

    public CartDAO(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    public Cart save(Cart cart) {
        return cartRepository.save(cart);
    }

    public Optional<Cart> findById(UUID id) {
        return cartRepository.findById(id);
    }

    public Optional<Cart> findByUserId(UUID userId) {
        return cartRepository.findByUserId(userId);
    }

    public void delete(Cart cart) {
        cartRepository.delete(cart);
    }

    public boolean existsById(UUID id) {
        return cartRepository.existsById(id);
    }
}
