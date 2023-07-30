package com.kits.ecommerce.dtos;

import com.kits.ecommerce.entities.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WishListDto implements Serializable {
    private Integer id;

    private Integer userID;

    private List<ProductDto> productDtos;
}
