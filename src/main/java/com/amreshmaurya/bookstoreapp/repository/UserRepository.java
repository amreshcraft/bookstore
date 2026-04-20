package com.amreshmaurya.bookstoreapp.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.amreshmaurya.bookstoreapp.entity.User;

public interface UserRepository  extends JpaRepository<User,UUID>{

    
}
