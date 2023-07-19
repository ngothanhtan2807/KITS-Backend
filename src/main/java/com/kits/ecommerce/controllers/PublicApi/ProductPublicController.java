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
//
//    @Autowired
//    private ProductService productService;
//
//    //get all products
//    @GetMapping("/products")
//    public ResponseEntity<PageDto<ProductDto>> getAllProducts(
//            @RequestParam(value = "pageNumber",required = false) Integer pageNumber,
//            //pageNumber bắt đầu từ 0 trang 0 lấy bao nhiêu phần tử PageSize
//            @RequestParam(value="pageSize",required = false) Integer pageSize,
//            @RequestParam(value="sortBy",defaultValue = AppConstants.SORT_PRODUCTS_BY,required = false) String sortBy,
//            @RequestParam(value="sortDir",defaultValue = AppConstants.SORT_DIR,required = false) String sortDir //tăng hay giảm
//    ) {
//        PageDto<ProductDto> allPageProducts = this.productService.getAllProducts(pageNumber,pageSize,sortBy,sortDir);
//        return new ResponseEntity<PageDto<ProductDto>>(allPageProducts, HttpStatus.OK);
//    }
//

//    //create
//    @PostMapping("/products/catalog/{catalogId}")
//    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto productDto, @PathVariable Integer catalogId) {
//        ProductDto createProduct = this.productService.createProduct(productDto, catalogId);
//
//        return new ResponseEntity<ProductDto>(createProduct, HttpStatus.CREATED);
//    }
//    //get by catalog
//
//
//
//    //get all products
//    @GetMapping("/products")
//    public ResponseEntity<PageDto<ProductDto>> getAllProducts(
//            @RequestParam(value = "pageNumber",defaultValue = AppConstants.PAGE_NUMBER,required = false) Integer pageNumber,
//            //pageNumber bắt đầu từ 0 trang 0 lấy bao nhiêu phần tử PageSize
//            @RequestParam(value="pageSize",defaultValue = AppConstants.PAGE_SIZE,required = false) Integer pageSize,
//            @RequestParam(value="sortBy",defaultValue = AppConstants.SORT_PRODUCTS_BY,required = false) String sortBy,
//            @RequestParam(value="sortDir",defaultValue = AppConstants.SORT_DIR,required = false) String sortDir //tăng hay giảm
//    ) {
//        PageDto<ProductDto> allPageProducts = this.productService.getAllProducts(pageNumber,pageSize,sortBy,sortDir);
//        return new ResponseEntity<PageDto<ProductDto>>(allPageProducts, HttpStatus.OK);
//    }
//
//    //get all products
//    @GetMapping("/products/{productId}")
//    public ResponseEntity<ProductDto> getAllProducts(@PathVariable Integer productId) {
//        ProductDto productDto = this.productService.getProductById(productId);
//        return new ResponseEntity<ProductDto>(productDto, HttpStatus.OK);
//    }
//
//    //delete product
//    @DeleteMapping("/products/{productId}")
//    public ApiResponse deleteProduct(@PathVariable Integer productId) {
//        this.productService.deleteProduct(productId);
//        return new ApiResponse("Product is succesfully deleted !!", true);
//    }
//
//    //update product
//    @PutMapping("/products/{productId}")
//    public ResponseEntity<ProductDto> updateProduct(@RequestBody ProductDto productDto ,@PathVariable Integer productId) {
//        ProductDto updateProduct = this.productService.updateProduct(productDto,productId);
//        return new ResponseEntity<ProductDto>(updateProduct,HttpStatus.OK) ;
//    }
//
//    //search
//    @GetMapping("/products/search/{keywords}")
//    public ResponseEntity<List<ProductDto>> searchProductByName(
//            @PathVariable("keywords") String keywords
//    ) {
//        List<ProductDto> result = this.productService.searchProducts(keywords);
//        return new ResponseEntity<List<ProductDto>>(result,HttpStatus.OK);
//
//    }
//
//    //post image upload
////    @PostMapping("/products/image/upload/{productId}")
////    public ResponseEntity<ProductDto> uploadProductImage(
////            @RequestParam("image") MultipartFile image,
////            @PathVariable Integer productId
////            ) throws IOException {
////        ProductDto productDto = this.productService.getProductById(productId);
////
////      String fileName =  this.fileService.uploadImage(path,image);
////      productDto.setImageName(fileName);
////      ProductDto updateProduct = this.productService.updateProduct(productDto,productId);
////      return new ResponseEntity<ProductDto>(updateProduct,HttpStatus.OK);
////
////    }
//
//    //method to serve files
//
//    @GetMapping(value="/posts/image/{imageName}",produces = MediaType.IMAGE_JPEG_VALUE)
//    public void downloadImage(@PathVariable("imageName") String imageName, HttpServletResponse response) throws IOException {
//        InputStream resource = this.fileService.getResource(path,imageName);
//        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
//        StreamUtils.copy(resource,response.getOutputStream());
//
//
//
//
//
//    }
}
