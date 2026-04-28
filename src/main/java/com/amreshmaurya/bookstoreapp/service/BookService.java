package com.amreshmaurya.bookstoreapp.service;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.amreshmaurya.bookstoreapp.dao.BookDAO;
import com.amreshmaurya.bookstoreapp.dto.book.BookDTO;
import com.amreshmaurya.bookstoreapp.dto.book.CreateBookDTO;
import com.amreshmaurya.bookstoreapp.dto.book.UpdateBookDTO;
import com.amreshmaurya.bookstoreapp.entity.Book;
import com.amreshmaurya.bookstoreapp.mapper.BookMapper;

@Service
public class BookService {

    private final BookDAO bookDAO;
    private final BookMapper bookMapper;
    private final S3FileService s3FileService;

    public BookService(BookDAO bookDAO, BookMapper bookMapper, S3FileService s3FileService) {
        this.bookDAO = bookDAO;
        this.bookMapper = bookMapper;
        this.s3FileService = s3FileService;
    }

    @Transactional
    public BookDTO createBook(CreateBookDTO dto, MultipartFile file) throws IOException {

        if (file != null && !file.isEmpty()) {
            String imageUrl = s3FileService.uploadFile(file);
            dto.setCoverImageUrl(imageUrl);
        }

        Book book = bookMapper.toEntity(dto);
        Book saved = bookDAO.save(book);

        return bookMapper.toDTO(saved);
    }

    public BookDTO getBook(UUID id) {
        Book book = bookDAO.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        return bookMapper.toDTO(book);
    }

    public List<BookDTO> getAllBooks() {
        return bookDAO.findAll()
                .stream()
                .map(bookMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public BookDTO updateBook(UUID id, UpdateBookDTO dto, MultipartFile file) throws IOException {

        Book book = bookDAO.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        if (file != null && !file.isEmpty()) {

            if (book.getCoverImageUrl() != null) {
                s3FileService.deleteFileFromS3Bucket(book.getCoverImageUrl());
            }

            String newImageUrl = s3FileService.uploadFile(file);

            book.setCoverImageUrl(newImageUrl);
        }

        bookMapper.updateEntity(dto, book);

        return bookMapper.toDTO(book);
    }

    @Transactional
    public void deleteBook(UUID id) {

        Book book = bookDAO.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        try {
            if (book.getCoverImageUrl() != null) {
                s3FileService.deleteFileFromS3Bucket(book.getCoverImageUrl());
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete image from S3");
        }
        bookDAO.delete(book);
    }
}