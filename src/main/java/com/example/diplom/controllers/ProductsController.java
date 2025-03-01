package com.example.diplom.controllers;

import com.example.diplom.cases.CreateProductUseCase;
import com.example.diplom.model.Order;
import com.example.diplom.model.Pizza;
import com.example.diplom.model.Product;
import com.example.diplom.repositories.OrderRepo;
import com.example.diplom.repositories.PizzaRepo;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/products")
public class ProductsController {


    private final CreateProductUseCase createProductUseCase;


    @GetMapping
    public ResponseEntity<List<Pizza>> getAllPizzas(){
        return new ResponseEntity<>(pizzaRepo.findAll(), HttpStatus.OK);
    }

    @PostMapping
    public Product createProduct(@RequestBody Product product) {
        return createProductUseCase.execute(product);
    }


}