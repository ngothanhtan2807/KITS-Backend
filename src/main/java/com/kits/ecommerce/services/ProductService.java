package com.kits.ecommerce.services;

import com.kits.ecommerce.dtos.PageDto;
import com.kits.ecommerce.dtos.ProductDto;
import com.kits.ecommerce.entities.Product;

import java.util.List;

public interface ProductService {
    //create
    ProductDto createProduct(ProductDto productDto, Integer generalId);

    ProductDto updateProduct(ProductDto productDto, Integer productId);

    void deleteProduct(Integer productId);
    PageDto<ProductDto> getAllProducts(Integer pageNumber, Integer pageSize,String sortBy,String sortDir);

    ProductDto getProductById(Integer productId);

    List<ProductDto> getProductsByCatalog(Integer catalogId);

    List<ProductDto> searchProducts(String keyword);

    List<ProductDto> getAllProduct(Integer id) ;
}
