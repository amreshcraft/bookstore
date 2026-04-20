package com.amreshmaurya.bookstoreapp.dto.book;

import java.math.BigDecimal;

import com.amreshmaurya.bookstoreapp.constant.BookStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateBookDTO {

    private String title;
    private String author;
    private BigDecimal price;
    private Integer stockQuantity;
    private BookStatus status;
    private String description;
    private String coverImageUrl;
}
