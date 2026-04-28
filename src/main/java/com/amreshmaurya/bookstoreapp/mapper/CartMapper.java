package com.amreshmaurya.bookstoreapp.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.amreshmaurya.bookstoreapp.dto.cart.CartDTO;
import com.amreshmaurya.bookstoreapp.dto.cart.CartItemDTO;
import com.amreshmaurya.bookstoreapp.entity.Cart;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CartMapper {

    private final CartItemMapper cartItemMapper;

    public CartDTO toDTO(Cart cart) {

        List<CartItemDTO> items = cart.getCartItems() != null
                ? cart.getCartItems()
                    .stream()
                    .map(cartItemMapper::toDTO)
                    .collect(Collectors.toList())
                : List.of();

        return CartDTO.builder()
                .id(cart.getId())
                .userId(cart.getUser().getId())
                .items(items)
                .totalAmount(cart.getTotalAmount())
                .build();
    }
}