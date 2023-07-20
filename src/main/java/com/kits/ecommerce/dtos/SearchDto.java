package com.kits.ecommerce.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchDto {
    private String name;
    private String color;
    private String size;
    private Integer catalogID;
    private double startPrice;
    private double endPrice;
}
