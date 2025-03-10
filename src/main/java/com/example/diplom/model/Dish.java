package com.example.diplom.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

/**
 * Базовый абстрактный класс для блюд с общими характеристиками.
 * Использует SINGLE_TABLE наследование с дискриминатором "DISH".
 */
@Entity
@DiscriminatorValue("DISH")
public abstract class Dish extends Product {

    public Dish() {
        super();
    }

    public Dish(String name, String description, double price) {
        super(name, description, price);
    }


}