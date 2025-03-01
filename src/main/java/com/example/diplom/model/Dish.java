package com.example.diplom.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;


@Entity
@DiscriminatorValue("DISH")
public class Dish extends Product{

    public Dish(){

    }

    public Dish(String name, String description, double price) {
        super(name, description, price);
    }

//    public Dish() {
//
//    }


    @Override
    public Dish clone() {
        return new Dish(this.getName(), this.getDescription(), this.getPrice());
    }


    @Override
    public String toString() {
        return "Dish{" +
                "name='" + getName() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", price=" + getPrice() +
                ", progressStatus=" + getProgressStatus() +
                '}';
    }
}
