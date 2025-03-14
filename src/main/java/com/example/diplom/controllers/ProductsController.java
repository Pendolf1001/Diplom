package com.example.diplom.controllers;

import com.example.diplom.dto.ProductDTO;
import com.example.diplom.model.*;
import com.example.diplom.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// ProductsController.java
/**
 * REST-контроллер для управления продуктами.
 */
@RestController
@AllArgsConstructor
@RequestMapping("/products")
public class ProductsController {
    private final ProductService productService;

    /**
     * Создает новый продукт на основе DTO.
     *
     * @param productDTO DTO с данными продукта
     * @return Созданный продукт в теле ответа
     */
    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody ProductDTO productDTO) {
        Product product;
        if ("PIZZA".equals(productDTO.getDtype())) {
            product = new Pizza(productDTO.getName(), productDTO.getDescription(), productDTO.getPrice(), productDTO.getDiameter());
        } else if ("ROLL".equals(productDTO.getDtype())) {
            product = new RollDish(productDTO.getName(), productDTO.getDescription(), productDTO.getPrice(), productDTO.getPieceCount());
        } else {
            throw new IllegalArgumentException("Неизвестный тип продукта");
        }
        Product createdProduct = productService.createProduct(product);
        return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
    }

    /**
     * Получает продукт по ID.
     *
     * @param id ID продукта
     * @return Продукт в теле ответа
     */
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable Long id) {
        Product productById;
        try {
            productById = (Product) productService.getProductById(id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Pizza());
        }
        return new ResponseEntity<>(productById, HttpStatus.OK);
    }

    /**
     * Получает все продукты.
     *
     * @return Список всех продуктов в теле ответа
     */
    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        return new ResponseEntity<>(productService.getAllProducts(), HttpStatus.OK);
    }

    /**
     * Удаляет продукт по ID.
     *
     * @param id ID продукта
     * @return Пустой ответ при успехе
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        Product productById;
        try {
            productById = productService.getProductById(id);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
        productService.deleteProduct(productById.getId());
        return ResponseEntity.ok().build();
    }

    /**
     * Обновляет существующий продукт.
     *
     * @param id          ID продукта
     * @param productDTO DTO с обновленными данными
     * @return Обновленный продукт в теле ответа
     */
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody ProductDTO productDTO) {
        Product productById;
        try {
            productById = productService.getProductById(id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Pizza());
        }
        if (productDTO.getName() != null) {
            productById.setName(productDTO.getName());
        }
        if (productDTO.getDescription() != null) {
            productById.setDescription(productDTO.getDescription());
        }
        if (productDTO.getPrice() != 0) {
            productById.setPrice(productDTO.getPrice());
        }
        if (productDTO.getProductStatus() != null) {
            productById.setProductStatus(productDTO.getProductStatus());
        }
        if (productById instanceof Pizza && productDTO.getDiameter() != 0) {
            ((Pizza) productById).setDiameter(productDTO.getDiameter());
        }
        Product updatedProduct = productService.updateProduct(productById);
        return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
    }
}