package com.example.diplom.model;

import jakarta.persistence.*;



/**
 * Базовый класс продуктов.
 * Использует SINGLE_TABLE наследование.
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype", discriminatorType = DiscriminatorType.STRING)
@Table(name = "PRODUCTS")
public abstract class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    private double price;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ProductStatus productStatus = ProductStatus.NOT_STARTED;

    @Column(name = "menu_id")
    private Long menuId;

    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }

    public Product(){}

    public Product(String name, String description, double price, Long menuId) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.menuId = menuId;
    }

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



}
