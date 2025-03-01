package com.example.diplom.repositories;

import com.example.diplom.model.Order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface OrderJpaRepository extends JpaRepository<Order, Long>{



}
