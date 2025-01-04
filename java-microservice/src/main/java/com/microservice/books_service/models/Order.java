package com.microservice.books_service.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    /**
     * Уникальный идентификатор заказа.
     *  Значение генерируется автоматически и не может быть установлено вручную.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private int id;

    /**
     * Какая книга входит в заказ
     */
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_book")
    private Book book;

    /**
     * Номер заказа
     */
    @Column(name = "number")
    private String orderNumber;

    @Column(name = "client")
    private int client;
}
