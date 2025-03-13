package com.example.diplom.model;


import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Сущность заказа с автоматическим обновлением статуса.
 * Содержит логику синхронизации статуса заказа со статусами продуктов.
 */
@Entity
@Table(name = "orders")
public class Order {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "orders_products",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name="product_id")
    )
    private List<Product> products;

    @Enumerated(EnumType.STRING)
    private OrderStatus status = OrderStatus.NEW;

    public Order() {
        this.products = new ArrayList<>();
    }


    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }



    public void addProduct(Product product) {
        products.add(product);
        updateOrderStatus();
    }


    public void updateOrderStatus() {
        boolean allReady = products.stream().allMatch(p -> p.getProductStatus() == ProductStatus.READY);
        boolean anyInProgress = products.stream().anyMatch(p -> p.getProductStatus() == ProductStatus.IN_PROGRESS);

        if (allReady) {
            status = OrderStatus.COMPLETED;
        } else if (anyInProgress) {
            status = OrderStatus.IN_PROGRESS;
        } else {
            status = OrderStatus.NEW;
        }


    }

    public void updateProductStatus(Product product, ProductStatus status) {
        product.setProductStatus(status);
        updateOrderStatus();
    }




}
