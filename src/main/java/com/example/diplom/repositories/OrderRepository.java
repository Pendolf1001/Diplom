package com.example.diplom.repositories;

import com.example.diplom.model.Order;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository {
    Order save(Order order);
    Order findById(Long id);


    void deleteById(Long id);

    Order update(Order order);

    List<Order> findAll();
}
