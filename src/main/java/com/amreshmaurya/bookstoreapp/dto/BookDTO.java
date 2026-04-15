package com.amreshmaurya.bookstoreapp.dto;

import java.math.BigDecimal;
import java.util.UUID;

import com.amreshmaurya.bookstoreapp.constant.BookStatus;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@Builder
public class BookDTO {
    private UUID id;
    @NotBlank
    private String title;
    private String author;
    private String description;
    private BigDecimal price;
    private Integer stockQuantity;
    private BookStatus status;
    private String coverImageUrl;
}
