package com.example.diplom.service;

import com.example.diplom.dto.OrderResponse;
import com.example.diplom.model.*;
import com.example.diplom.repositories.MenuItemRepository;
import com.example.diplom.repositories.OrderRepository;
import com.example.diplom.repositories.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@AllArgsConstructor
@Service
public class OrderService {
    private final OrderRepository orderRepository;

    private final ProductRepository productRepository;

    private final MenuItemRepository menuItemRepository;

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
                return new RuntimeException("Заказ не найден");
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



    @Transactional
    public Order addProductFromMenu(Long orderId, Long menuItemId) {
        // Получить продукт из меню
        MenuItem menuItem = menuItemRepository.findById(menuItemId)
                .orElseThrow(() -> new RuntimeException("Продукт в меню не найден"));

        // Создать копию продукта для заказа
        Product product = createProductFromMenuItem(menuItem);
        product.setMenuId(menuItem.getMenu().getId());

        // Сохранить продукт
        Product savedProduct = productRepository.save(product);

        // Добавить продукт в заказ
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Заказ не найден"));
        order.addProduct(savedProduct);

        return orderRepository.save(order);
    }

    private Product createProductFromMenuItem(MenuItem item) {
        // Реализация клонирования в зависимости от типа продукта
        if (item instanceof PizzaMenuItem) {
            PizzaMenuItem pizzaItem = (PizzaMenuItem) item;
            Pizza pizza = new Pizza();
            pizza.setName(pizzaItem.getName());
            pizza.setDescription(pizzaItem.getDescription());
            pizza.setPrice(pizzaItem.getPrice());
            pizza.setDiameter(pizzaItem.getDiameter());
            return pizza;
        }else if (item instanceof RollMenuItem) {
            RollMenuItem rollItem = (RollMenuItem) item;
            RollDish roll = new RollDish();
            roll.setName(rollItem.getName());
            roll.setDescription(rollItem.getDescription());
            roll.setPrice(rollItem.getPrice());
            roll.setPieceCount(rollItem.getPieceCount()); // Только pieceCount
            return roll;
        }

        // Аналогично для других типов
        throw new IllegalArgumentException("Тип продукта не поддерживается");
    }



    /**
     * Подсчитывает сумму заказа.
     *
     * @param orderId ID заказа.
     * @return Сумма заказа.
     */
    public double calculateOrderTotal(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Заказ не найден"));

        return order.getProducts().stream()
                .mapToDouble(Product::getPrice)
                .sum();
    }


    public OrderResponse orderToOrderResponse(Order orderById, double total) {

        OrderResponse response = new OrderResponse();
        response.setId(orderById.getId());
        response.setStatus(orderById.getStatus());
        response.setProducts(orderById.getProducts());
        response.setTotal(total);
        return response;
    }

    public List<OrderResponse> getAllOrdersWithTotals() {
        List<Order> orders=orderRepository.findAll();
        return orders.stream()
                .map(order -> {
                    double total = calculateOrderTotal(order.getId());
                    return orderToOrderResponse(order, total);
                })
                .collect(Collectors.toList());
    }
}
