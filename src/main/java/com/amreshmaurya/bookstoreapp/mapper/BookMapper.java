package com.amreshmaurya.bookstoreapp.mapper;

import org.springframework.stereotype.Component;

import com.amreshmaurya.bookstoreapp.dto.BookDTO;
import com.amreshmaurya.bookstoreapp.entity.Book;

@Component
public class BookMapper {
    
    public Book toBook(BookDTO bookDTO){
        return Book.builder()
        .id(bookDTO.getId())
        .author(bookDTO.getAuthor())
        .title(bookDTO.getTitle())
        .price(bookDTO.getPrice())
        .status(bookDTO.getStatus())
        .stockQuantity(bookDTO.getStockQuantity())
        .description(bookDTO.getDescription())
        .coverImageUrl(bookDTO.getCoverImageUrl())
        .build();
    }

    public BookDTO toBookDTO(Book book){
        return BookDTO.builder()
        .id(book.getId())
        .author(book.getAuthor())
        .title(book.getTitle())
        .status(book.getStatus())
        .price(book.getPrice())
        .stockQuantity(book.getStockQuantity())
        .description(book.getDescription())
        .coverImageUrl(book.getCoverImageUrl())
        .build();
    }
}
