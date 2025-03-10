package com.example.diplom;

import com.example.diplom.model.Pizza;
import com.example.diplom.model.Product;
import com.example.diplom.repositories.ProductRepository;
import com.example.diplom.service.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

// ProductServiceTest.java
@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {
    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @Test
    void testCreateProduct() {
        Product product = new Pizza();
        when(productRepository.save(any())).thenReturn(product);

        Product result = productService.createProduct(product);
        assertNotNull(result);
        verify(productRepository).save(product);
    }
}