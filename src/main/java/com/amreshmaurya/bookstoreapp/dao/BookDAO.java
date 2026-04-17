package com.amreshmaurya.bookstoreapp.dao;

import java.util.List;
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

    public Book save(Book book){
        return bookRepository.save(book);
    }

    public Optional<Book> findById(UUID id){
        return bookRepository.findById(id);
    }

    public List<Book> findAll(){
        return bookRepository.findAll();
    }

    public void delete(Book book){
        bookRepository.delete(book);
    }

    public boolean existsById(UUID id){
        return bookRepository.existsById(id);
    }
}