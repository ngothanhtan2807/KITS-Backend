package com.kits.ecommerce.dtos.request;

import com.kits.ecommerce.dtos.CategoryDto;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BlogRequest {
    private Integer blogId;
    private String title;
    private String content;
    private int categoryID;
}
