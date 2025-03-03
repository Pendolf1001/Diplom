package com.example.diplom.repositories;

import com.example.diplom.model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {

    Product save(Product product);

    Optional<Product> findById(Long id);

    List<Product> findAll();

    void deleteById(Long id);

    Product update(Product product);

    boolean existsById(Long id);
}
