package com.amreshmaurya.bookstoreapp.dto.book;

import java.math.BigDecimal;
import java.util.UUID;

import com.amreshmaurya.bookstoreapp.constant.BookStatus;

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
    private String title;
    private String author;
    private String description;
    private BigDecimal price;
    private Integer stockQuantity;
    private BookStatus status;
    private String coverImageUrl;
}