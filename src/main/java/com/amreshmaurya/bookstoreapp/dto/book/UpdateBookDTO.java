package com.amreshmaurya.bookstoreapp.dto.book;

import java.math.BigDecimal;

import com.amreshmaurya.bookstoreapp.constant.BookStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateBookDTO {

    private String title;
    private String author;
    private BigDecimal price;
    private Integer stockQuantity;
    private BookStatus status;
    private String description;
    private String coverImageUrl;
}
