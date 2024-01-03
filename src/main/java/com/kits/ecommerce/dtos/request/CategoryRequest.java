package com.kits.ecommerce.dtos.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryRequest {
    private Integer categoryID;
    private String categoryName;
}
