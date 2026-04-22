package com.amreshmaurya.bookstoreapp.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.amreshmaurya.bookstoreapp.entity.User;

public interface UserRepository extends JpaRepository<User, UUID> {

    @Query("SELECT u FROM User u LEFT JOIN FETCH u.roles WHERE u.id = :id")
    Optional<User> findByIdWithRoles(UUID id);

    @Query("""
                SELECT DISTINCT u FROM User u
                LEFT JOIN FETCH u.roles
            """)
    List<User> findAllWithRoles();
}
