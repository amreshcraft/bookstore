package com.amreshmaurya.bookstoreapp.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.amreshmaurya.bookstoreapp.dto.BookDTO;
import com.amreshmaurya.bookstoreapp.dto.CreateBookDTO;
import com.amreshmaurya.bookstoreapp.dto.UpdateBookDTO;
import com.amreshmaurya.bookstoreapp.service.BookService;
import com.amreshmaurya.bookstoreapp.wrapper.ApiResponse;

import jakarta.validation.Valid;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/api/v1/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService){
        this.bookService = bookService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<BookDTO> createBook(
            @Valid @RequestBody CreateBookDTO dto){
        return ApiResponse.success(bookService.createBook(dto));
    }

    @GetMapping("/{id}")
    public ApiResponse<BookDTO> getBookById(@PathVariable UUID id){
        return ApiResponse.success(bookService.getBook(id));
    }

    @GetMapping
    public ApiResponse<List<BookDTO>> getAllBooks(){
        return ApiResponse.success(bookService.getAllBooks());
    }

    @PutMapping("/{id}")
    public ApiResponse<BookDTO> updateBook(
            @PathVariable UUID id,
           @Valid @RequestBody UpdateBookDTO dto){
            System.out.println("put api hit");
        return ApiResponse.success(bookService.updateBook(id, dto));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ApiResponse<Void> deleteBook(@PathVariable UUID id){
        bookService.deleteBook(id);
        return ApiResponse.success(null);
    }
}