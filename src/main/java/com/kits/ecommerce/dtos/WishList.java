package com.kits.ecommerce.dtos;

import com.kits.ecommerce.entities.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WishList implements Serializable {
    List<ProductDto> wishList = new ArrayList<>();
}
