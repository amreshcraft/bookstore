package com.amreshmaurya.bookstoreapp.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.amreshmaurya.bookstoreapp.dto.BookDTO;
import com.amreshmaurya.bookstoreapp.service.BookService;
import com.amreshmaurya.bookstoreapp.wrapper.ApiResponse;

import jakarta.validation.Valid;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/v1/book")
public class BookController {
    private final BookService bookService;

   public BookController(BookService bookService){
        this.bookService = bookService;
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<BookDTO> createBook(@Valid @RequestBody BookDTO bookDTO){
        return ApiResponse.success(bookService.createBook(bookDTO));
    }
    
    // @GetMapping("/:id")
    // public ApiResponse<BookDTO> getBookById(@Valid @PathVariable UUID id){
    //     return ApiResponse.success();
    // }
  
    
    

}
