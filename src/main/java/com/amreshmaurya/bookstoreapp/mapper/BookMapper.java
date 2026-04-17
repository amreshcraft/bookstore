package com.amreshmaurya.bookstoreapp.mapper;

import org.springframework.stereotype.Component;

import com.amreshmaurya.bookstoreapp.dto.BookDTO;
import com.amreshmaurya.bookstoreapp.dto.CreateBookDTO;
import com.amreshmaurya.bookstoreapp.dto.UpdateBookDTO;
import com.amreshmaurya.bookstoreapp.entity.Book;

@Component
public class BookMapper {

    public Book toEntity(CreateBookDTO dto) {
    return Book.builder()
            .title(dto.getTitle())
            .author(dto.getAuthor())
            .price(dto.getPrice())
            .stockQuantity(dto.getStockQuantity())
            .status(dto.getStatus())
            .description(dto.getDescription())
            .coverImageUrl(dto.getCoverImageUrl())
            .build();
}

public void updateEntity(UpdateBookDTO dto, Book book) {

    if (dto.getTitle() != null) {
        book.setTitle(dto.getTitle());
    }
    if (dto.getAuthor() != null) {
        book.setAuthor(dto.getAuthor());
    }
    if (dto.getPrice() != null) {
        book.setPrice(dto.getPrice());
    }
    if (dto.getStockQuantity() != null) {
        book.setStockQuantity(dto.getStockQuantity());
    }
    if (dto.getStatus() != null) {
        book.setStatus(dto.getStatus());
    }
    if (dto.getDescription() != null) {
        book.setDescription(dto.getDescription());
    }
    if (dto.getCoverImageUrl() != null) {
        book.setCoverImageUrl(dto.getCoverImageUrl());
    }
}

public BookDTO toDTO(Book book) {
    return BookDTO.builder()
            .id(book.getId())
            .title(book.getTitle())
            .author(book.getAuthor())
            .price(book.getPrice())
            .stockQuantity(book.getStockQuantity())
            .status(book.getStatus())
            .description(book.getDescription())
            .coverImageUrl(book.getCoverImageUrl())
            .build();
}
    
    // public Book toBook(BookDTO bookDTO){
    //     return Book.builder()
    //     .id(bookDTO.getId())
    //     .author(bookDTO.getAuthor())
    //     .title(bookDTO.getTitle())
    //     .price(bookDTO.getPrice())
    //     .status(bookDTO.getStatus())
    //     .stockQuantity(bookDTO.getStockQuantity())
    //     .description(bookDTO.getDescription())
    //     .coverImageUrl(bookDTO.getCoverImageUrl())
    //     .build();
    // }

    // public BookDTO toBookDTO(Book book){
    //     return BookDTO.builder()
    //     .id(book.getId())
    //     .author(book.getAuthor())
    //     .title(book.getTitle())
    //     .status(book.getStatus())
    //     .price(book.getPrice())
    //     .stockQuantity(book.getStockQuantity())
    //     .description(book.getDescription())
    //     .coverImageUrl(book.getCoverImageUrl())
    //     .build();
    // }

}
