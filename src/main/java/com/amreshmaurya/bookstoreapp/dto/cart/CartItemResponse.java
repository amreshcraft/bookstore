package com.amreshmaurya.bookstoreapp.dto.cart;

import java.math.BigDecimal;
import java.util.UUID;

import lombok.Data;


@Data
public class CartItemResponse {
    private UUID cartItemId;
    private UUID bookId;
    private String bookTitle;
    private String author;
    private String coverImageUrl;
    private Integer quantity;
    private BigDecimal unitPrice;
    private BigDecimal subtotal;
}