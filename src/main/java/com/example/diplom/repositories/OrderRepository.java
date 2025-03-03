package com.example.diplom.repositories;

import com.example.diplom.model.Order;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository {
    Order save(Order order);
    Optional<Order> findById(Long id);


    void deleteById(Long id);

    Order update(Order order);

    List<Order> findAll();
}
