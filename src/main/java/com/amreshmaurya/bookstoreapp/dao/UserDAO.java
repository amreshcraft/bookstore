package com.amreshmaurya.bookstoreapp.dao;


import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.amreshmaurya.bookstoreapp.entity.User;
import com.amreshmaurya.bookstoreapp.repository.UserRepository;



@Component
public class UserDAO {

    private final UserRepository userRepository;

    public UserDAO(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public User save(User user){
        return userRepository.save(user);
    }

    public Optional<User> findById(UUID id){
        return userRepository.findById(id);
    }

    public List<User> findAll(){
        return userRepository.findAll();
    }

    public void delete(User user){
        userRepository.delete(user);
    }

    public boolean existsById(UUID id){
        return userRepository.existsById(id);
    }
}
