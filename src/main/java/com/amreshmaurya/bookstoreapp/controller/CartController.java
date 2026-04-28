package com.amreshmaurya.bookstoreapp.controller;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.amreshmaurya.bookstoreapp.dto.cart.CartDTO;
import com.amreshmaurya.bookstoreapp.service.CartService;
import com.amreshmaurya.bookstoreapp.wrapper.ApiResponse;

@RestController
@RequestMapping("/api/v1/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    // Get Cart
    @GetMapping
    public ApiResponse<CartDTO> getCart() {
        return ApiResponse.success(
                cartService.getCart()
        );
    }

    // ➕ Add to Cart
    @PostMapping("/add")
    public ApiResponse<CartDTO> addToCart(
            @RequestParam UUID bookId,
            @RequestParam int qty
    ) {
        return ApiResponse.success(
                cartService.addToCart(bookId, qty)
        );
    }

    // 🔁 Update Quantity
    @PutMapping("/item/{cartItemId}")
    public ApiResponse<CartDTO> updateQuantity(
            @PathVariable UUID cartItemId,
            @RequestParam int qty
    ) {
        return ApiResponse.success(
                cartService.updateQuantity(cartItemId, qty)
        );
    }

    // ❌ Remove Item
    @DeleteMapping("/item/{cartItemId}")
    public ApiResponse<Void> removeItem(
            @PathVariable UUID cartItemId
    ) {
        cartService.removeItem(cartItemId);
        return ApiResponse.success(null);
    }

    // 🧹 Clear Cart
    @DeleteMapping("/clear")
    public ApiResponse<Void> clearCart() {
        cartService.clearCart();
        return ApiResponse.success(null);
    }
}