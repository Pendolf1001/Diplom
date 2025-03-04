package com.example.diplom.controllers;


//import com.example.diplom.cases.AddProductToOrderUseCase;
import com.example.diplom.dto.OrderResponse;
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
    public ResponseEntity<OrderResponse> getOrder(@PathVariable Long id) {
        Order orderById;
        try {
            orderById = orderService.getOrderById(id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new OrderResponse());
        }
        double total = orderService.calculateOrderTotal(id);
        OrderResponse response = orderService.orderToOrderResponse(orderById, total);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<OrderResponse>> getAllOrders() {
        return new ResponseEntity<>(orderService.getAllOrdersWithTotals(),HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Order>  updateOrder(@PathVariable Long id, @RequestBody Order order) {


        try {
            return new ResponseEntity<>(orderService.updateOrder(order), HttpStatus.OK);
        } catch (RuntimeException e) {
            log.warn("Заказ с таким Id не найден");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Order());
        }
    }

    @PostMapping("/{orderId}/menu-items/{menuItemId}")
    public ResponseEntity<Order> addProductFromMenu(
            @PathVariable Long orderId,
            @PathVariable Long menuItemId
    ) {

        try {
            Order updatedOrder = orderService.addProductFromMenu(orderId, menuItemId);
            return new ResponseEntity<>(updatedOrder, HttpStatus.OK);
        } catch (RuntimeException e) {
            log.warn("Ошибка при добавлении продукта в заказ: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Order());
        }
    }
}
