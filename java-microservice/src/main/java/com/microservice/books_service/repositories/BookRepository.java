package com.microservice.books_service.repositories;

import com.microservice.books_service.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book,Integer> {
    Optional<Book> findById(int id);
}
