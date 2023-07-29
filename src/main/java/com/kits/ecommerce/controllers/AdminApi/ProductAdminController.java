package com.kits.ecommerce.controllers.AdminApi;


import com.kits.ecommerce.dtos.ApiResponse;
import com.kits.ecommerce.dtos.ProductDto;
import com.kits.ecommerce.services.CatalogService;
import com.kits.ecommerce.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.apache.commons.io.FileUtils;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/admin/products")
@CrossOrigin

public class ProductAdminController {

    @Autowired
    CatalogService catalogService;

    @Autowired
    ProductService productService;

    @GetMapping("/")
    public ResponseEntity<List<ProductDto>> getAll(){
        return ResponseEntity.ok(productService.getAllProduct());
    }
    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getOne(@PathVariable("id")Integer id){
        return  ResponseEntity.ok(productService.getProductById(id));
    }
    @PostMapping(value = "/")
    public ResponseEntity<ProductDto>createGeneral(@ModelAttribute ProductDto productDto)throws IOException{
//    return ResponseEntity.ok(generalProductService.createGeneral(generalProductDto));

        return ResponseEntity.ok(productService.createProduct(productDto));
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<ProductDto>updateGeneral(@ModelAttribute ProductDto productDto, @PathVariable("id")Integer id)throws IOException{
        return  ResponseEntity.ok(productService.updateProduct(productDto, id));
    }
    @DeleteMapping ("/delete/{id}")
    public ResponseEntity<?>deleteGeneral(@PathVariable("id")Integer id){
        productService.deleteProduct(id);
        return new ResponseEntity(new ApiResponse("General delete success!!!", true), HttpStatus.OK);
    }

    @GetMapping("/count")
    public ResponseEntity<Integer> countProduct(){
        return ResponseEntity.ok(productService.count());
    }

}
