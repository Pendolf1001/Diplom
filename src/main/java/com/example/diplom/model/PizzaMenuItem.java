package com.example.diplom.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("PIZZA")
public class PizzaMenuItem extends MenuItem {

    private int diameter; // Уникальное поле для пиццы


    public int getDiameter() {
        return diameter;
    }

    public void setDiameter(int diameter) {
        this.diameter = diameter;
    }
}