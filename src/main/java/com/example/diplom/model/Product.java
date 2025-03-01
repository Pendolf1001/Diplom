package com.example.diplom.model;


public abstract class Product implements Prototype {

    private Long id;
    private String name;
    private String description;
    private double price;
    private ProductStatus productStatus = ProductStatus.NOT_STARTED;

//    public Product() {
//
//    }

    public Product(String name, String description, double price) {
        this.name = name;
        this.description = description;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ProductStatus getProductStatus() {
        return productStatus;
    }

    public void setProductStatus(ProductStatus productStatus) {
        this.productStatus = productStatus;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public ProductStatus getProgressStatus() {
        return productStatus;
    }

    public void setProgressStatus(ProductStatus productStatus) {
        this.productStatus = productStatus;
    }


    public void  updateStatus( ProductStatus productStatus){
        this.productStatus = productStatus;
    }

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", progressStatus=" + productStatus +
                '}';
    }

    @Override
    public abstract Prototype clone();

}
