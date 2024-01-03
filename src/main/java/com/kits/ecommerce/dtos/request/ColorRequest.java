package com.kits.ecommerce.dtos.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ColorRequest {
    private  Integer id;
    private String name;
}
