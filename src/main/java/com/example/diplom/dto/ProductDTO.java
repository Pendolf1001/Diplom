package com.example.diplom.dto;


import com.example.diplom.model.ProductStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.context.annotation.Bean;

@AllArgsConstructor
@Data
public class ProductDTO {
    private Long id;
    private String name;
    private String description;
    private double price;
    private ProductStatus productStatus;
    private int diameter;
    private String dtype;
}
