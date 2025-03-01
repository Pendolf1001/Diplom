package com.example.diplom.repositories;

import com.example.diplom.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class ProductRepositoryImpl implements ProductRepository {


    @Autowired
    private ProductJpaRepository productJpaRepository;


    @Override
    public Product save(Product product) {
        return productJpaRepository.save(product);
    }

    @Override
    public Product findById(Long id) {
        return productJpaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Продукт не найден"));
    }

    @Override
    public List<Product> findAll() {
        return productJpaRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        productJpaRepository.deleteById(id);
    }

    @Override
    public Product update(Product product) {
        return productJpaRepository.save(product); // save() обновляет, если объект уже существует
    }
}
