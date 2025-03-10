package com.example.diplom.model;

import jakarta.persistence.*;


/**
 * Сущность роли пользователя.
 * Используется для системы аутентификации и авторизации.
 */
@Entity
@Table(name = "roles")
public class Role {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}