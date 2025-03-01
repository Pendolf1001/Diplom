package com.example.diplom.cases;

import com.example.diplom.model.Product;
import com.example.diplom.repositories.ProductRepository;

/*

    Создание продукта

*/


public class CreateProductUseCase {

    private final ProductRepository productRepository;

    public CreateProductUseCase(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product execute(Product product) {
        return productRepository.save(product);
    }
}

