package com.example.diplom.repositories;

import com.example.diplom.model.Pizza;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface PizzaRepo extends JpaRepository<Pizza, Long> {


    public List<Pizza>  findAllByOrderId (Long orderId);

}
