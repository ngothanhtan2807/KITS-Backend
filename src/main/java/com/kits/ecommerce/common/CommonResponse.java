package com.kits.ecommerce.common;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommonResponse <T>{
    private String message;
    private T data;
    private Boolean success;

    public static <T> CommonResponse<T> createSuccessData(T data){
        return CommonResponse.<T>builder().data(data).success(true).build();
    }
}
