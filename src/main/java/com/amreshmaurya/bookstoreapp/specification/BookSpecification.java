package com.amreshmaurya.bookstoreapp.specification;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.amreshmaurya.bookstoreapp.constant.BookStatus;
import com.amreshmaurya.bookstoreapp.entity.Book;

import jakarta.persistence.criteria.Predicate;



public class BookSpecification {

    public static Specification<Book> filter(
            String title,
            String author,
            BookStatus status,
            BigDecimal minPrice,
            BigDecimal maxPrice) {

        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (title != null && !title.isEmpty()) {
                predicates.add(cb.like(cb.lower(root.get("title")),
                        "%" + title.toLowerCase() + "%"));
            }

            if (author != null && !author.isEmpty()) {
                predicates.add(cb.like(cb.lower(root.get("author")),
                        "%" + author.toLowerCase() + "%"));
            }

            if (status != null) {
                predicates.add(cb.equal(root.get("status"), status));
            }

            if (minPrice != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("price"), minPrice));
            }

            if (maxPrice != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("price"), maxPrice));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}