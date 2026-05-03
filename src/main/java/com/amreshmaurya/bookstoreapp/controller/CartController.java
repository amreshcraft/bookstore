package com.amreshmaurya.bookstoreapp.controller;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.amreshmaurya.bookstoreapp.dto.cart.AddToCartRequest;
import com.amreshmaurya.bookstoreapp.dto.cart.CartResponse;
import com.amreshmaurya.bookstoreapp.service.CartService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping("/add")
    public ResponseEntity<CartResponse> addToCart(
            @RequestHeader("userId") UUID userId,
            @Valid  @RequestBody AddToCartRequest request) {

        System.out.println("userId: " + userId);
        System.out.println("req: " + request);
        return ResponseEntity.ok(cartService.addToCart(userId, request));
    }

    @GetMapping
    public ResponseEntity<CartResponse> getCart(@RequestHeader("userId") UUID userId) {
        return ResponseEntity.ok(cartService.getCart(userId));
    }

    @PutMapping("/items/{cartItemId}")
    public ResponseEntity<CartResponse> updateCartItem(
            @RequestHeader("userId") UUID userId,
            @PathVariable UUID cartItemId,
            @RequestParam Integer quantity) {
        return ResponseEntity.ok(cartService.updateCartItemQuantity(userId, cartItemId, quantity));
    }

    @DeleteMapping("/items/{cartItemId}")
    public ResponseEntity<Void> removeFromCart(
            @RequestHeader("userId") UUID userId,
            @PathVariable UUID cartItemId) {
        cartService.removeFromCart(userId, cartItemId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/clear")
    public ResponseEntity<Void> clearCart(@RequestHeader("userId") UUID userId) {
        cartService.clearCart(userId);
        return ResponseEntity.noContent().build();
    }
}