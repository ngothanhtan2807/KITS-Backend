package com.kits.ecommerce.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderProductDto implements Serializable {
    private Integer id;
    private Integer productID;
    private Integer sizeID;
    private Integer colorID;
    private int quantity;
    private Double totalPrice;
}
