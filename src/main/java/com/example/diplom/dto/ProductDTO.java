package com.example.diplom.dto;


import com.example.diplom.model.ProductStatus;
import lombok.AllArgsConstructor;
import lombok.Data;




/**
 * DTO для представления информации о продукте.
 * Содержит основные характеристики и статус продукта.
 * Используется для передачи данных между клиентом и сервером.
 */
@AllArgsConstructor
@Data
public class ProductDTO {
    public Long id;
    public String name;
    public String description;
    public double price;
    public ProductStatus productStatus;
    public int diameter;
    public int pieceCount;
    public String dtype;

}
