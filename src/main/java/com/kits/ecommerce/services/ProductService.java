package com.kits.ecommerce.services;

import com.kits.ecommerce.dtos.PageDto;
import com.kits.ecommerce.dtos.ProductDto;
import com.kits.ecommerce.dtos.SearchDto;
import com.kits.ecommerce.entities.Product;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.util.List;

public interface ProductService {
    ProductDto getProductById(Integer generalId);

    List<ProductDto> getAllProduct();

    ProductDto createProduct(ProductDto productDto) throws IOException;

    ProductDto updateProduct(ProductDto productDto, Integer generalId);

    void deleteProduct(Integer generalId);

    Resource load(String name);
    List<ProductDto> searchProductByName(String productName);

    PageDto<ProductDto> getProductsHomePage(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

    List<ProductDto>search(SearchDto searchDto);

    List<ProductDto>filterByLength(int id);

    List<ProductDto>filterByCatalog(int id);

    Product convertToProduct(ProductDto productDto);

    ProductDto convertToProductDto(Product product);

    int count();
}
