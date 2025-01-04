package com.microservice.books_service.dto;

import com.microservice.books_service.models.Book;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookResponse {
    private int id;
    private String name;
    private String genre;
    private int price;
    private String author;

    public BookResponse(Book book){
        id = book.getId();
        name = book.getName();
        genre = book.getGenre();
        price = book.getPrice();
        author = book.getAuthor();
    }
}