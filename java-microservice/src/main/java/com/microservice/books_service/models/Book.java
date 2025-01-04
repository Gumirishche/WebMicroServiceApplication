package com.microservice.books_service.models;

import jakarta.persistence.*;
import lombok.*;

/**
 * Сущьность книги
 * для работы с таблицей "books"
 * со свойствами: id, name, genre, price, author
 */

@Entity
@Table(name = "books")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    /**
     * Уникальный идентификатор книги.
     *  Значение генерируется автоматически и не может быть установлено вручную.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private int id;

    /**
     * Название книги.
     */
    @Column(name = "name")
    private String name;

    /**
     * Жанр книги.
     */
    @Column(name = "genre")
    private String genre;

    /**
     * Цена книги
     */

    @Column(name = "price")
    private int price;

    /**
     * Автор книги
     */
    @Column(name = "author")
    private String author;
}

