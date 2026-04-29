package com.amreshmaurya.bookstoreapp.controller;


import org.springframework.web.bind.annotation.*;

import com.amreshmaurya.bookstoreapp.service.CartService;

@RestController
@RequestMapping("/api/v1/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

   
    
}