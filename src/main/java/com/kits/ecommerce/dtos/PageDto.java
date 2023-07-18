package com.kits.ecommerce.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PageDto<T> {
    private List<T> contents;
    private int pageNumber;
    private int pageSize;

    private long totalElements;
    private int totalPages;
    private boolean lastPage;


}
