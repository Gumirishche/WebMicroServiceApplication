package com.microservice.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    /**
     * Уникальный идентификатор заказа.
     *  Значение генерируется автоматически и не может быть установлено вручную.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private int id;

    @Column(name = "login")
    private String login;

    @Column(name = "password")
    private String password;
}
