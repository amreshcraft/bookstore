package com.amreshmaurya.bookstoreapp.dto.cart;

import java.math.BigDecimal;
import java.util.List;
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
public class CartDTO {

    private UUID id;

    private UUID userId;

    private List<CartItemDTO> items;

    private BigDecimal totalAmount;
}