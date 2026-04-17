package com.amreshmaurya.bookstoreapp.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amreshmaurya.bookstoreapp.dao.BookDAO;
import com.amreshmaurya.bookstoreapp.dto.BookDTO;
import com.amreshmaurya.bookstoreapp.dto.CreateBookDTO;
import com.amreshmaurya.bookstoreapp.dto.UpdateBookDTO;
import com.amreshmaurya.bookstoreapp.entity.Book;
import com.amreshmaurya.bookstoreapp.mapper.BookMapper;





@Service
public class BookService {

    private final BookDAO bookDAO;
    private final BookMapper bookMapper;

    public BookService(BookDAO bookDAO, BookMapper bookMapper){
        this.bookDAO = bookDAO;
        this.bookMapper = bookMapper;
    }

    @Transactional
    public BookDTO createBook(CreateBookDTO dto){
        Book book = bookMapper.toEntity(dto);
        Book saved = bookDAO.save(book);
        return bookMapper.toDTO(saved);
    }

    public BookDTO getBook(UUID id){
        Book book = bookDAO.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        return bookMapper.toDTO(book);
    }

    public List<BookDTO> getAllBooks(){
        return bookDAO.findAll()
                .stream()
                .map(bookMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public BookDTO updateBook(UUID id, UpdateBookDTO dto){

        Book book = bookDAO.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        //  safe partial update
        bookMapper.updateEntity(dto, book);

        //  no save() needed (dirty checking)
        return bookMapper.toDTO(book);
    }

    @Transactional
    public void deleteBook(UUID id){

        Book book = bookDAO.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        bookDAO.delete(book);
    }
}