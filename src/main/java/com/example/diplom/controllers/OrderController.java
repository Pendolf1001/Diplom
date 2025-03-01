package com.example.diplom.controllers;


import com.example.diplom.cases.AddProductToOrderUseCase;
import com.example.diplom.model.Order;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@AllArgsConstructor
@RestController
@RequestMapping("/orders")
class OrderController {
    private final AddProductToOrderUseCase addProductToOrderUseCase;



    @PostMapping("/{orderId}/products/{productId}")
    public Order addProductToOrder(@PathVariable Long orderId, @PathVariable Long productId) {
        return addProductToOrderUseCase.execute(orderId, productId);
    }
}
