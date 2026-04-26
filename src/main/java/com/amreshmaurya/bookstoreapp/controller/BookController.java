package com.amreshmaurya.bookstoreapp.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.amreshmaurya.bookstoreapp.dto.book.BookDTO;
import com.amreshmaurya.bookstoreapp.dto.book.CreateBookDTO;
import com.amreshmaurya.bookstoreapp.dto.book.UpdateBookDTO;
import com.amreshmaurya.bookstoreapp.service.BookService;
import com.amreshmaurya.bookstoreapp.wrapper.ApiResponse;

import jakarta.validation.Valid;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    // @PostMapping
    // @ResponseStatus(HttpStatus.CREATED)
    // public ApiResponse<BookDTO> createBook(
    // @Valid @RequestBody CreateBookDTO dto){
    // return ApiResponse.success(bookService.createBook(dto));
    // }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public BookDTO createBook(
            @RequestPart("book") CreateBookDTO dto,
            @RequestPart("file") MultipartFile file) throws IOException {
        System.out.println(dto);
        System.out.println(file.getOriginalFilename());
        return bookService.createBook(dto, file);
    }

    @GetMapping("/{id}")
    public ApiResponse<BookDTO> getBookById(@PathVariable UUID id) {
        return ApiResponse.success(bookService.getBook(id));
    }

    @PostMapping("/all")
    public ApiResponse<List<BookDTO>> getAllBooks() {
        return ApiResponse.success(bookService.getAllBooks());
    }

    @PutMapping("/{id}")
    public ApiResponse<BookDTO> updateBook(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateBookDTO dto) {
        System.out.println("put api hit");
        return ApiResponse.success(bookService.updateBook(id, dto));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ApiResponse<Void> deleteBook(@PathVariable UUID id) {
        bookService.deleteBook(id);
        return ApiResponse.success(null);
    }
}