package com.example.diplom.controllers;

import com.example.diplom.model.Order;
import com.example.diplom.model.Pizza;
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
@RequestMapping("/pizza")
public class PizzaController {
    private final PizzaRepo pizzaRepo;
    private final OrderRepo orderRepo;


    @GetMapping
    public ResponseEntity<List<Pizza>> getAllPizzas(){
        return new ResponseEntity<>(pizzaRepo.findAll(), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<Pizza> getPizzaById(@PathVariable("id") Long id){

        Pizza pizzaById;
        try {
            pizzaById = pizzaRepo.findById(id).orElseThrow(null);;
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Pizza());
        }

        return new ResponseEntity<>(pizzaById, HttpStatus.OK);
    }


    @PostMapping
    public ResponseEntity<Pizza> createBook(@RequestBody Pizza pizza){
        return new  ResponseEntity<>(pizzaRepo.save(pizza),HttpStatus.CREATED);
    }



    @DeleteMapping("{id}")
    public ResponseEntity<Void> deletePizza(@PathVariable("id") Long id){
        pizzaRepo.deleteById(id);

        return ResponseEntity.ok().build();
    }



    @PutMapping("/{id}/client/{clientId}")
    public ResponseEntity<Pizza> asignClientToBook(@PathVariable Long id, @PathVariable Long orderId){
        Optional<Pizza> pizzaOptional= pizzaRepo.findById(id);
        Optional<Order> orderOptional;
        orderOptional = orderRepo.findById(orderId);

        if (pizzaOptional.isPresent() && orderOptional.isPresent()){
            Pizza pizza =pizzaOptional.get();
            Order order= orderOptional.get();
            pizza.setOrder(order);
            pizzaRepo.save(pizza);
            return ResponseEntity.ok(pizza);
        }else{
            return  ResponseEntity.notFound().build();
        }



    }


    @GetMapping("/client/{orderId}")
    public ResponseEntity<List<Pizza>> getPizzaByOrder(@PathVariable Long orderId){
        return new ResponseEntity<>(pizzaRepo.findAllByOrderId(orderId), HttpStatus.OK);

    }


}