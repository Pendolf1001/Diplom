package com.example.diplom.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;


@Entity
@DiscriminatorValue("DISH")
public abstract class Dish extends Product {

    public Dish() {
        super();
    }

    public Dish(String name, String description, double price) {
        super(name, description, price);
    }

    @Override
    public abstract Dish clone();
}