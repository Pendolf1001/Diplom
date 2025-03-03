package com.example.diplom.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

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
    public Pizza clone() {
        Pizza clone = new Pizza(this.getName(), this.getDescription(), this.getPrice(), this.diameter);
        clone.setId(null); // Сбрасываем ID для создания новой записи
        clone.setProductStatus(this.getProductStatus());
        return clone;
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