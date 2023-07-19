package com.kits.ecommerce.controllers.PublicApi;

import com.kits.ecommerce.config.AppConstants;
import com.kits.ecommerce.dtos.ApiResponse;
import com.kits.ecommerce.dtos.PageDto;
import com.kits.ecommerce.dtos.ProductDto;
import com.kits.ecommerce.services.ProductService;
import org.hibernate.engine.jdbc.StreamUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;


@RestController
@RequestMapping("/api/public/")
public class ProductPublicController {
    @Autowired
    private ProductService productService;
    @GetMapping("/{name}")
    public ResponseEntity<?> searchProduct(@PathVariable("name") String name){
        return ResponseEntity.status(HttpStatus.OK).body(productService.searchProductByName(name));
    }
}
