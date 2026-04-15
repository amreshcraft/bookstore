package com.amreshmaurya.bookstoreapp.service;

import org.springframework.stereotype.Service;

import com.amreshmaurya.bookstoreapp.dao.BookDAO;
import com.amreshmaurya.bookstoreapp.dto.BookDTO;
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

    public BookDTO createBook(BookDTO bookDTO){
        System.out.println("Service hit");
        Book book = bookMapper.toBook(bookDTO);
        return bookMapper.toBookDTO(bookDAO.createBook(book));
    }
}