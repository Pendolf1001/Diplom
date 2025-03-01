package com.example.diplom.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
public class Pizza {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;




    @ManyToOne
    @JoinColumn(name="client_id")
    private Order order;

    public Pizza(String name) {
        this.name = name;

    }
}
