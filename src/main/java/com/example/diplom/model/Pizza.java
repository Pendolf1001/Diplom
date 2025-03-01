package com.example.diplom.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@Table(name="pizzas")
public class Pizza extends Dish {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;



    @Column( name="name", nullable=false)
    private String name;


    @Column( name="status",  nullable=false)
    @Enumerated(EnumType.STRING)
    private ProductStatus productStatus;

    @Override
    public String toString() {
        return "Pizza{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", progressStatus=" + productStatus +
                ", order=" + order +
                ", description='" + getDescription() + '\'' +
                ", price=" + getPrice() +
                '}';
    }

    @JoinColumn (name="order_id", nullable=true)
    @ManyToOne

    private Order order;


    public Pizza(String name, String description, double price) {
        super(name, description, price);
    }
}
