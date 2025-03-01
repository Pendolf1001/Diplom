package com.example.diplom.model;


import java.util.ArrayList;
import java.util.List;

public class Order {

    private Long id;
    private List<Product> products = new ArrayList<>();
    private OrderStatus status = OrderStatus.NEW;

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


    private void updateOrderStatus() {
        boolean allReady = products.stream().allMatch(p -> p.getProgressStatus() == ProductStatus.READY);
        boolean anyInProgress = products.stream().anyMatch(p -> p.getProgressStatus() == ProductStatus.IN_PROGRESS);

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
