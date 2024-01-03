package com.kits.ecommerce.controllers.PublicApi;

import com.kits.ecommerce.config.AppConstants;
import com.kits.ecommerce.dtos.*;
import com.kits.ecommerce.services.ProductService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;


@RestController
@RequestMapping("/api/public/")
@CrossOrigin
@Tag(name = "Product")
public class ProductPublicController {
    @Autowired
    private ProductService productService;


    @GetMapping("/{name}")
    public ResponseEntity<?> searchProduct(@PathVariable("name") String name) {
        return ResponseEntity.status(HttpStatus.OK).body(productService.searchProductByName(name));
    }

    @GetMapping("/products")
    public ResponseEntity<PageDto<ProductDto>> getAllProducts(
            @RequestParam(name = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(name = "sortBy", defaultValue = AppConstants.SORT_PRODUCTS_BY, required = false) String sortBy,
            @RequestParam(name = "sortOrder", defaultValue = AppConstants.SORT_DIR, required = false) String sortOrder) {

        PageDto<ProductDto> productResponse = productService.getProductsHomePage(pageNumber, pageSize, sortBy, sortOrder);

        return new ResponseEntity<PageDto<ProductDto>>(productResponse, HttpStatus.FOUND);
    }

    @PostMapping("/")
    public ResponseEntity<List<ProductDto>> search(@ModelAttribute SearchDto searchDto) {
        return ResponseEntity.ok(productService.search(searchDto));
    }
     @GetMapping("/filter-by-length/{id}")
    public ResponseEntity<List<ProductDto>>filterByLength(@PathVariable("id")int id){
        return ResponseEntity.ok(productService.filterByLength(id));
     }

    @GetMapping("/filter-by-catalog/{id}")
    public ResponseEntity<List<ProductDto>>filterByCatalog(@PathVariable("id")int id){
        return ResponseEntity.ok(productService.filterByCatalog(id));
    }
}
