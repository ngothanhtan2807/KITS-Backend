package com.kits.ecommerce.dtos.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BlogResponse {
    private Integer blogId;
    private String title;
    private String content;
    private CategoryResponse category;
}
