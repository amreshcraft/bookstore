package com.amreshmaurya.bookstoreapp.dao;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.amreshmaurya.bookstoreapp.entity.Book;
import com.amreshmaurya.bookstoreapp.repository.BookRepository;


@Component
public class BookDAO {

    private final BookRepository bookRepository;

    public BookDAO(BookRepository bookRepository){
        this.bookRepository = bookRepository;
    }

    public Book createBook(Book book){
        return bookRepository.save(book);
    }

    public Book findById(UUID id){
        return bookRepository.findById(id).orElseThrow(()-> new RuntimeException("Book Not found"));
    }

    public void deleteById(UUID id){
        bookRepository.deleteById(id);
    }
}