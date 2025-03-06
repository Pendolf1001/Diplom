package com.example.diplom.dto;


import com.example.diplom.model.OrderStatus;
import com.example.diplom.model.Product;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

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
