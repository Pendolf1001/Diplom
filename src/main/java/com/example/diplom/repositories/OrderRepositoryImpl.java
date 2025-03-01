package com.example.diplom.repositories;

import com.example.diplom.model.Order;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


@Repository
public class OrderRepositoryImpl implements OrderRepository{


    @Autowired
    private OrderJpaRepository orderJpaRepository;

    @Override
    public Order save(Order order) {
        return orderJpaRepository.save(order);
    }

    @Override
    public Order findById(Long id) {
        return orderJpaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Заказ не найден"));
    }

    @Override
    public void deleteById(Long id) {
        orderJpaRepository.deleteById(id);
    }

    @Override
    public Order update(Order order) {
        return orderJpaRepository.save(order); // save() обновляет, если объект уже существует
    }

    @Override
    public List<Order> findAll() {
        return orderJpaRepository.findAll();
    }
}
