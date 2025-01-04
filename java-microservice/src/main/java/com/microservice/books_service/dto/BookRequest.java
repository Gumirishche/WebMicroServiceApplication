package com.microservice.books_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookRequest {
    private int id;
    private String name;
    private String genre;
    private int price;
    private String author;
}
