package com.amreshmaurya.bookstoreapp.entity;


import java.math.BigDecimal;
import java.util.UUID;

import com.amreshmaurya.bookstoreapp.constant.BookStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(
    name = "books",
        uniqueConstraints = {
        @UniqueConstraint(columnNames = "isbn")
    }
)


public class Book  extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(nullable = false, updatable = false)
    private UUID id;


    @Column(nullable = false, length = 200)
    private String title;

    @Column(nullable = false)
    private String author;

    @Column(unique = true, nullable = false, length = 20)
    private String isbn;

    @Column(length = 1000)
    private String description;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(nullable = false)
    private Integer stockQuantity;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BookStatus status;

    @Column(nullable = false)
    private String coverImageUrl;

}