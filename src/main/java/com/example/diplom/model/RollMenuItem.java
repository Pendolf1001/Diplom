package com.example.diplom.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;



/**
 * Элемент меню для роллов.
 * Наследует базовые атрибуты MenuItem и добавляет количество кусочков.
 */
@Entity
@DiscriminatorValue("ROLL")
public class RollMenuItem extends MenuItem {

    private int pieceCount; // Уникальное поле для роллов

    public int getPieceCount() {
        return pieceCount;
    }

    public void setPieceCount(int pieceCount) {
        this.pieceCount = pieceCount;
    }
}