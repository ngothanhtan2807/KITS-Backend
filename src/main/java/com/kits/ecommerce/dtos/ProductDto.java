package com.kits.ecommerce.dtos;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProductDto {
    private Integer id;

    private double price;
    private int sizeId;
    private int colorId;
    private int quantity;

}

