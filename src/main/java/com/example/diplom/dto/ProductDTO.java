package com.example.diplom.dto;


import com.example.diplom.model.ProductStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.context.annotation.Bean;

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
