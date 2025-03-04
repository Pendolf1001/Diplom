package com.example.diplom.model;


import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("ROLL")
public class RollDish extends Dish {
    private int pieceCount; // Диаметр пиццы в см

    public RollDish() {
        super();
    }

    public RollDish (String name, String description, double price, int pieceCount) {
        super(name, description, price);
        this.pieceCount = pieceCount;
    }

    public int getPieceCount() {
        return pieceCount;
    }

    public void setPieceCount(int pieceCount) {
        this.pieceCount = pieceCount;
    }

    @Override
    public RollDish clone() {
        RollDish clone = new RollDish(this.getName(), this.getDescription(), this.getPrice(), this.pieceCount);
        clone.setId(null); // Сбрасываем ID для создания новой записи
        clone.setProductStatus(this.getProductStatus());
        return clone;
    }

    @Override
    public String toString() {
        return "Roll{" +
                "name='" + getName() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", price=" + getPrice() +
                ", pieceCount=" + pieceCount +
                ", progressStatus=" + getProductStatus() +
                '}';
    }
}
