package com.example.diplom.controllers;


//import com.example.diplom.cases.AddProductToOrderUseCase;
import com.example.diplom.model.Order;

import com.example.diplom.service.OrderService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/orders")
class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody Order order) {
        return  new  ResponseEntity<> (orderService.createOrder(order),HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrder(@PathVariable Long id) {
        Order orderById;
        try {
            orderById = orderService.getOrderById(id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Order());
        }

        return new ResponseEntity<>(orderById, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        return new ResponseEntity<>(orderService.getAllOrders(),HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Order>  updateOrder(@PathVariable Long id, @RequestBody Order order) {

        return new ResponseEntity<>(orderService.updateOrder(order), HttpStatus.OK);
    }

    @PostMapping("/{orderId}/products/{productId}")
    public ResponseEntity<Order> addProductToOrder(
            @PathVariable Long orderId,
            @PathVariable Long productId
    ) {
        try {
            Order updatedOrder = orderService.addProductToOrder(orderId, productId);
            return new ResponseEntity<>(updatedOrder, HttpStatus.OK);
        } catch (RuntimeException e) {
            log.warn("Ошибка при добавлении продукта в заказ: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
}
