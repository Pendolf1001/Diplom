//package com.example.diplom.cases;
//
//
//import com.example.diplom.model.Order;
//import com.example.diplom.model.Product;
//import com.example.diplom.repositories.OrderRepository;
//import com.example.diplom.repositories.ProductRepository;
//
///*
//
//   Добавление продукта в заказ
//
//*/
//
//
//public class AddProductToOrderUseCase {
//
//    private final OrderRepository orderRepository;
//    private final ProductRepository productRepository;
//
//    public AddProductToOrderUseCase(OrderRepository orderRepository, ProductRepository productRepository) {
//        this.orderRepository = orderRepository;
//        this.productRepository = productRepository;
//    }
//
//    public Order execute(Long orderId, Long productId) {
//        Order order = orderRepository.findById(orderId);
//        Product product = productRepository.findById(productId);
//        order.addProduct(product);
//        return orderRepository.save(order);
//    }
//}
