package com.kits.ecommerce.services;

import com.kits.ecommerce.dtos.CategoryDto;
import com.kits.ecommerce.entities.Category;

import java.util.List;

public interface CategoryService {
    CategoryDto createCategory(CategoryDto categoryDto);
    CategoryDto updateCategory(CategoryDto categoryDto,Integer categoryId);
    CategoryDto getCategoryById(Integer categoryId);
    List<CategoryDto> getAllCategorys();
    void deleteCategory(Integer categoryId);
    void addCategorysService(List<Category> categoryId);
    void deleteCategorysService(List<Integer> ids);
    CategoryDto convertToCategoryDto(Category category);
    Category convertToCategory(CategoryDto categoryDto);
}
