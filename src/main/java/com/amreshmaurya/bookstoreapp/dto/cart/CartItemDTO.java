package com.amreshmaurya.bookstoreapp.dto.cart;

import java.math.BigDecimal;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartItemDTO {

    private UUID id;

    private UUID bookId;
    private String title;
    private String author;
    private String coverImageUrl;

    private BigDecimal priceAtAdded;
    private Integer quantity;
    private BigDecimal totalPrice;
}