package com.amreshmaurya.bookstoreapp.dto;

import java.math.BigDecimal;

import com.amreshmaurya.bookstoreapp.constant.BookStatus;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateBookDTO {

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Author is required")
    private String author;

    @NotNull(message = "Price is required")
    @Positive(message = "Price must be > 0")
    private BigDecimal price;

    @NotNull(message = "Stock is required")
    @Min(0)
    private Integer stockQuantity;

    @NotNull(message = "Status is required")
    private BookStatus status;

    private String description;
    private String coverImageUrl;
}
