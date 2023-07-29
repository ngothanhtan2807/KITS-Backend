package com.kits.ecommerce.controllers.AdminApi;


import com.kits.ecommerce.dtos.ApiResponse;
import com.kits.ecommerce.dtos.CategoryDto;
import com.kits.ecommerce.entities.Category;
import com.kits.ecommerce.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/categories")
@CrossOrigin
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    @GetMapping("")
    public ResponseEntity<List<CategoryDto>> getAll() {
        return ResponseEntity.ok(categoryService.getAllCategorys());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto> getCatalog(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(categoryService.getCategoryById(id));
    }

    @PostMapping("")
    public ResponseEntity<CategoryDto> createCatalog(@RequestBody CategoryDto catalogDto) {
        return ResponseEntity.ok(categoryService.createCategory(catalogDto));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<CategoryDto> updateCatalog(@PathVariable("id") Integer id, @RequestBody CategoryDto catalogDto) {
        return ResponseEntity.ok(categoryService.updateCategory(catalogDto, id));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteCatalog(@PathVariable("id") Integer id) {
        categoryService.deleteCategory(id);
        return new ResponseEntity(new ApiResponse("Category deleted Successfully", true), HttpStatus.OK);
    }

    @PostMapping("/addcategorys")
    public ResponseEntity<?> addCatalods(@RequestBody List<Category> catalogs) {

        // TODO: Xử lý logic để thêm nhiều catalog vào trong cơ sở dữ liệu
        categoryService.addCategorysService(catalogs);
        // Trả về thông báo thành công nếu thêm thành công
        return new ResponseEntity(new ApiResponse("Add all success!!!", true), HttpStatus.OK);
    }

    @DeleteMapping("/deletecategorys")
    public ResponseEntity<?> deleteCatalogs(@RequestBody List<Integer> caterotyIds) {
        categoryService.deleteCategorysService(caterotyIds);
        return new ResponseEntity(new ApiResponse("Delete all success!!!", true), HttpStatus.OK);
    }
}