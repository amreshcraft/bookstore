package com.amreshmaurya.bookstoreapp.entity;

import java.util.UUID;

import com.amreshmaurya.bookstoreapp.constant.Role;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table(name = "roles")
public class RoleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;
    @Enumerated(EnumType.STRING)
    private Role role;
}