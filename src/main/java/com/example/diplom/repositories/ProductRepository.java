package com.example.diplom.repositories;

import com.example.diplom.model.Product;

import java.util.List;

public interface ProductRepository {

    Product save(Product product);
    Product findById(Long id);
    List<Product> findAll();

    void deleteById(Long id);

    Product update(Product product);
}
