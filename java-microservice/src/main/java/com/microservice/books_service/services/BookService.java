package com.microservice.books_service.services;

import com.microservice.books_service.dto.BookRequest;
import com.microservice.books_service.dto.BookResponse;
import com.microservice.books_service.models.Book;
import com.microservice.books_service.repositories.BookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookService {

    private final BookRepository bookRepository;

    public void createBook(@RequestBody BookRequest bookRequest){
        Book book = new Book();
        book.setName(bookRequest.getName());
        book.setGenre(bookRequest.getGenre());
        bookRepository.save(book);
        log.info("Book {} was created", book.getId());
    }

    public List<BookResponse> getAllBooks() {
        return bookRepository.findAll().stream().map(this::mapToBookResponse).toList();
    }

    public BookResponse mapToBookResponse(Book book){
        return BookResponse.builder()
                .id(book.getId())
                .name(book.getName())
                .genre(book.getGenre()).build();
    }
}
