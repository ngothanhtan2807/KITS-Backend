package com.kits.ecommerce.controllers.AdminApi;


import com.kits.ecommerce.dtos.ProductDto;
import com.kits.ecommerce.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/product/")
public class ProductAdminController {


    @Autowired
    ProductService productService;
    @GetMapping("/{generalID}")
    public ResponseEntity<List<ProductDto>> getAll(@PathVariable("generalID")Integer id){
//        if(page<1){
//            return  new ResponseEntity(new ApiResponse("Page isnt exits", false), HttpStatus.OK);
//        }else{
//        Pageable pageable = PageRequest.of(page-1, 10);
        return ResponseEntity.ok(productService.getAllProduct(id));
    }
    @PostMapping("/")
    public ResponseEntity<ProductDto>createProduct(@PathVariable("generalID")Integer id, @RequestBody ProductDto productDto){
        return ResponseEntity.ok(productService.createProduct(productDto, id));
    }
}
