package com.kits.ecommerce.dtos;

import com.kits.ecommerce.entities.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BlogDto {
    private Integer blogId;
    private String title;
    private String content;
    private int categoryID;
    private CategoryDto category;
}
