package com.example.diplom.service;

import com.example.diplom.model.Order;
import com.example.diplom.model.Product;
import com.example.diplom.repositories.OrderRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Slf4j
@AllArgsConstructor
@Service
public class OrderService {
    private final OrderRepository orderRepository;

    private final ProductService productService;

//    public OrderService(OrderRepository orderRepository) {
//        this.orderRepository = orderRepository;
//    }

    public Order createOrder(Order order) {
        return orderRepository.save(order);
    }

    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> {
                log.warn("Заказ с ID {} не найден", id);
                return new RuntimeException("Продукт не найден");
                });
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }

    public Order updateOrder(Order order) {
        return orderRepository.update(order);
    }

    public Order addProductToOrder(Long orderId, Long productId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        Product originalProduct = productService.getProductById(productId);
        Product clonedProduct = (Product) originalProduct.clone();

        // Сбрасываем ID и сохраняем клон как новый продукт
        clonedProduct.setId(null);
        Product savedClone = productService.createProduct(clonedProduct);

        order.getProducts().add(savedClone);
        return orderRepository.update(order);
    }



}
