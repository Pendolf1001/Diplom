package com.example.diplom;

import com.example.diplom.model.PizzaMenuItem;
import com.example.diplom.model.RollMenuItem;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class MenuItemTest {
    @Test
    void testPizzaMenuItem() {
        PizzaMenuItem pizza = new PizzaMenuItem();
        pizza.setDiameter(25);
        assertEquals(25, pizza.getDiameter());
    }

    @Test
    void testRollMenuItem() {
        RollMenuItem roll = new RollMenuItem();
        roll.setPieceCount(8);
        assertEquals(8, roll.getPieceCount());
    }
}