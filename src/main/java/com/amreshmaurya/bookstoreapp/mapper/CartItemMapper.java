package com.amreshmaurya.bookstoreapp.mapper;


import org.springframework.stereotype.Component;

import com.amreshmaurya.bookstoreapp.dto.cart.CartItemDTO;
import com.amreshmaurya.bookstoreapp.entity.CartItem;

@Component
public class CartItemMapper {

    public CartItemDTO toDTO(CartItem item) {

        return CartItemDTO.builder()
                .id(item.getId())
                .bookId(item.getBook().getId())
                .title(item.getBook().getTitle())
                .author(item.getBook().getAuthor())
                .coverImageUrl(item.getBook().getCoverImageUrl())
                .priceAtAdded(item.getPriceAtAdded())
                .quantity(item.getQuantity())
                .totalPrice(item.getTotalPrice())
                .build();
    }
} 