

package com.example.diplom.service;

import com.example.diplom.model.Product;
import com.example.diplom.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    /**
     * Создает новый продукт, если продукт с таким ID не существует.
     *
     * @param product Продукт для создания.
     * @return Созданный продукт.
     * @throws IllegalArgumentException Если продукт с таким ID уже существует.
     */
    @Transactional
    public Product createProduct(Product product) {
        if (product.getId() != null && productRepository.existsById(product.getId())) {
            log.warn("Продукт с ID {} уже существует", product.getId());
            throw new IllegalArgumentException("Продукт с таким ID уже существует");
        }
        return productRepository.save(product);
    }

    /**
     * Возвращает продукт по его ID.
     *
     * @param id ID продукта.
     * @return Найденный продукт.
     * @throws RuntimeException Если продукт не найден.
     */
    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Продукт с ID {} не найден", id);
                    return new RuntimeException("Продукт не найден");
                });
    }

    /**
     * Возвращает список всех продуктов.
     *
     * @return Список продуктов.
     */
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    /**
     * Удаляет продукт по его ID.
     *
     * @param id ID продукта.
     * @throws RuntimeException Если продукт не найден.
     */
    @Transactional
    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            log.warn("Продукт с ID {} не найден", id);
            throw new RuntimeException("Продукт не найден");
        }
        productRepository.deleteById(id);
    }

    /**
     * Обновляет существующий продукт.
     *
     * @param product Продукт для обновления.
     * @return Обновленный продукт.
     * @throws RuntimeException Если продукт не найден.
     */
    @Transactional
    public Product updateProduct(Product product) {
        if (product.getId() == null || !productRepository.existsById(product.getId())) {
            log.warn("Продукт с ID {} не найден", product.getId());
            throw new RuntimeException("Продукт не найден");
        }
        return productRepository.save(product);
    }



    public boolean existsById(Long id) {
        return productRepository.existsById(id);
    }
}