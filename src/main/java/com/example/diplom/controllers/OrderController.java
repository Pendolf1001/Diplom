package com.example.diplom.controllers;


import com.example.diplom.model.Order;
import com.example.diplom.repositories.OrderRepo;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/orders")
public class OrderController {

    private final OrderRepo orderRepo;

    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders(){
        return new ResponseEntity<>(orderRepo.findAll(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody Order order){
        return new  ResponseEntity<>(orderRepo.save(order),HttpStatus.CREATED);
    }


}
