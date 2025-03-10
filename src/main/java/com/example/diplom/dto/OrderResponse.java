package com.example.diplom.dto;


import com.example.diplom.model.OrderStatus;
import com.example.diplom.model.Product;
import lombok.Getter;
import lombok.Setter;
import java.util.List;


/**
 * DTO для представления информации о заказе.
 * Содержит данные о статусе, продуктах, общей сумме и информации о ресторане.
 * Используется для передачи данных между слоями приложения.
 */
@Getter
@Setter
public class OrderResponse {
    private Long id;
    private OrderStatus status;
    private List<Product> products;
    private double total;
    private String restaurantName;
    private String restaurantAddress;
}
