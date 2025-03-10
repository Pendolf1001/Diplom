package com.example.diplom.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;



/**
 * Конкретная реализация блюда - пицца.
 * Добавляет уникальный атрибут диаметра.
 */
@Entity
@DiscriminatorValue("PIZZA")
public class Pizza extends Dish {
    private int diameter; // Диаметр пиццы в см

    public Pizza() {
        super();
    }

    public Pizza(String name, String description, double price, int diameter) {
        super(name, description, price);
        this.diameter = diameter;
    }

    public int getDiameter() {
        return diameter;
    }

    public void setDiameter(int diameter) {
        this.diameter = diameter;
    }



    @Override
    public String toString() {
        return "Pizza{" +
                "name='" + getName() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", price=" + getPrice() +
                ", diameter=" + diameter +
                ", progressStatus=" + getProductStatus() +
                '}';
    }
}