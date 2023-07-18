package com.kits.ecommerce.services.impl;

import com.kits.ecommerce.dtos.PageDto;
import com.kits.ecommerce.dtos.ProductDto;
import com.kits.ecommerce.entities.*;
import com.kits.ecommerce.exeptions.ResoureNotFoundException;
import com.kits.ecommerce.repositories.*;
import com.kits.ecommerce.services.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    GeneralProductRepo generalProductRepo;

    @Autowired
    private ModelMapper modelMapper;


    @Autowired
    ColorRepo colorRepo;

    @Autowired
    SizeRepo sizeRepo;

    @Autowired
    QuantityRepo quantityRepo;

//    @Override
//    public List<ProductDto> getAllProduct(Integer id) {
//        GeneralProduct generalProduct = generalProductRepo.findById(id).orElseThrow(() -> new ResoureNotFoundException("General", "ID", id));
//        PageDto<ProductDto> result = new PageDto<>();
//
//        List<Product> productList = productRepo.findAllByGeneralProduct(generalProduct.getId());
//
//
//        List<ProductDto> productDtoList = new ArrayList<>();
//        for (int i = 0; i < productList.size(); i++) {
//            productDtoList.add(this.convertToProductDto(productList.get(i)));
//        }
////        result.setPageNumber(productList.getNumber() + 1);
////        result.setTotalPages(productList.getTotalPages());
////        result.setPageSize(pageable.getPageSize());
////        result.setContents(productDtoList);
//
//
//        return productDtoList;
//    }

    @Override
    public ProductDto createProduct(ProductDto productDto, Integer generalId) {
        GeneralProduct generalProduct = generalProductRepo.findById(generalId).orElseThrow(()->new ResoureNotFoundException("General", "ID", generalId));
        Product product = this.convertToProduct(productDto);
        Color color = colorRepo.findById(productDto.getColorId()).orElseThrow(()->new ResoureNotFoundException("Color", "ID", productDto.getColorId()));
        Size size = sizeRepo.findById(productDto.getSizeId()).orElseThrow(()->new ResoureNotFoundException("Size", "ID", productDto.getSizeId()));
//        Quantity quantity = quantityRepo.findById(productDto.getSize()).orElseThrow(()->new ResoureNotFoundException("Quantity", "ID", productDto.getQuantity()));
        product.setColor(color);
        product.setSize(size);

        generalProduct.addProduct(product);
        product.setGeneralProduct(generalProduct);

        return this.convertToProductDto(product);
    }

    //
    @Override
    public ProductDto updateProduct(ProductDto productDto, Integer productId) {
        Product product = this.productRepo.findById(productId).orElseThrow(() -> new ResoureNotFoundException("Product", "product id", productId));


        product.setPrice(productDto.getPrice());


        Product updatedProduct = this.productRepo.save(product);
        return this.modelMapper.map(updatedProduct, ProductDto.class);
    }

    @Override
    public void deleteProduct(Integer productId) {

    }

    @Override
    public PageDto<ProductDto> getAllProducts(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {

        return null;
    }

    @Override
    public ProductDto getProductById(Integer productId) {
        return null;
    }

    @Override
    public List<ProductDto> getProductsByCatalog(Integer catalogId) {
        return null;
    }

    @Override
    public List<ProductDto> searchProducts(String keyword) {
        return null;
    }

    @Override
    public List<ProductDto> getAllProduct(Integer id) {
        GeneralProduct generalProduct = generalProductRepo.findById(id).orElseThrow(() -> new ResoureNotFoundException("General", "ID", id));
        PageDto<ProductDto> result = new PageDto<>();

        List<Product> productList = productRepo.findAllByGeneralProduct(generalProduct);


        List<ProductDto> productDtoList = new ArrayList<>();
        for (int i = 0; i < productList.size(); i++) {
            productDtoList.add(this.convertToProductDto(productList.get(i)));
        }
        return productDtoList;
    }

    public ProductDto convertToProductDto(Product product) {
        ProductDto productDto = this.modelMapper.map(product, ProductDto.class);
        productDto.setColorId(product.getColor().getId());
        productDto.setSizeId(product.getSize().getId());
        return productDto;
    }

    public Product convertToProduct(ProductDto productDto) {
        Product product = this.modelMapper.map(productDto, Product.class);
//        product.setColor(product.getColor().getId());
//        product.setSize(product.getSize().getId());
        return this.modelMapper.map(productDto, Product.class);
    }

//    @Override
//    public PageDto<ProductDto> getAllProducts(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
//        if (pageNumber != null && pageSize != null) {
//            Sort sort = (sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending());
//
//            Pageable p = PageRequest.of(pageNumber, pageSize, sort);
//            // nếu muốn sort từ cao đến thấp thêm .descending()
//
//            Page<Product> pageProduct = this.productRepo.findAll(p);
//            List<Product> allProducts = pageProduct.getContent();
////        List<Product> allProducts = this.productRepo.findAll();
//            List<ProductDto> productDtos = allProducts.stream().map((product) -> this.modelMapper.map(product, ProductDto.class)).collect(Collectors.toList());
//            PageDto<ProductDto> productPageResponse = new PageDto<>();
//            productPageResponse.setContents(productDtos);
//            productPageResponse.setPageNumber(pageProduct.getNumber());
//            productPageResponse.setPageSize(pageProduct.getSize());
//            productPageResponse.setTotalElements(pageProduct.getTotalElements());
//            productPageResponse.setTotalPages(pageProduct.getTotalPages());
//            productPageResponse.setLastPage(pageProduct.isLast());
//            return productPageResponse;
//        } else {
//            List<Product> allProducts  =this.productRepo.findAll();
//            List<ProductDto> productDtos = allProducts.stream().map((product) -> this.modelMapper.map(product, ProductDto.class)).collect(Collectors.toList());
//            PageDto<ProductDto> productPageResponse = new PageDto<>();
//            productPageResponse.setContents(productDtos);
//            return productPageResponse;
//        }
//
//    }
}
