package com.kits.ecommerce.services.impl;

import com.kits.ecommerce.dtos.PageDto;
import com.kits.ecommerce.dtos.ProductDto;
import com.kits.ecommerce.entities.*;
import com.kits.ecommerce.exeptions.ResoureNotFoundException;
import com.kits.ecommerce.repositories.*;
import com.kits.ecommerce.services.ProductService;
import org.apache.commons.io.FilenameUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    private final Path root = Paths.get("uploads");

    @Autowired
    ProductRepo productRepo;

    @Autowired
    CatalogRepo catalogRepo;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    ImageProductRepo imageProductRepo;

    @Autowired
    ColorRepo colorRepo;

    @Autowired
    SizeRepo sizeRepo;

    @Override
    public ProductDto getProductById(Integer productId) {
        Product product = productRepo.findById(productId).orElseThrow(() -> new ResoureNotFoundException("Product", "ID", productId));

        return this.convertToProductDto(product);
    }

    @Override
    public List<ProductDto> getAllProduct() {
        List<Product> products = productRepo.findAll();
        List<ProductDto> productDtoList = new ArrayList<>();
        for (int i = 0; i < products.size(); i++) {
            ProductDto productDto = convertToProductDto(products.get(i));
            productDtoList.add(productDto);
        }
        return productDtoList;
    }

    @Override
    public ProductDto createProduct(ProductDto productDto) {

        try {
            Catalog catalog = catalogRepo.findById(productDto.getCatalogID()).orElseThrow(() -> new ResoureNotFoundException("Catalog", "ID", productDto.getCatalogID()));
            Set<MultipartFile> files = productDto.getFiles();

            Product product = this.convertToProduct(productDto);

            List<Integer> sizesID = productDto.getSizesID();
            List<Size> lisSizes = new ArrayList<>();
            for (int i = 0; i < sizesID.size(); i++) {
                int finalI = i;
                Size size = sizeRepo.findById(sizesID.get(i)).orElseThrow(() -> new ResoureNotFoundException("Size", "ID", sizesID.get(finalI)));
                size.getProductList().add(product);
                lisSizes.add(size);
            }
            product.setSizes(lisSizes);


            List<Integer> colorID = productDto.getColorsID();
            Set<Color> lisColors = new HashSet<>();
            for (int i = 0; i < colorID.size(); i++) {
                int finalI = i;
                Color color = colorRepo.findById(colorID.get(i)).orElseThrow(() -> new ResoureNotFoundException("Color", "ID", colorID.get(finalI)));
                color.getProductList().add(product);
                lisColors.add(color);
            }
            product.setColors(lisColors);


            for (MultipartFile file : files) {
                UUID uuid = UUID.randomUUID();
                String uuidString = uuid.toString();
                String ext = FilenameUtils.getExtension(file.getOriginalFilename());//ext name
                Files.copy(file.getInputStream(), this.root.resolve(uuidString + "." + ext));
//rename file
                ImageProduct imageProduct = new ImageProduct();
                imageProduct.setProduct(product);
                imageProduct.setPath(uuidString + "." + ext);
                imageProduct.setTitle(uuidString + "." + ext);


                product.addProductImages(imageProduct);
            }
            product.setCatalog(catalog);
            productRepo.save(product);

            return this.convertToProductDto(product);
        } catch (Exception e) {
            e.printStackTrace();

        }
        return productDto;
    }

    @Override
    public ProductDto updateProduct(ProductDto productDto, Integer productId) {
        try {
            Catalog catalog = catalogRepo.findById(productDto.getCatalogID()).orElseThrow(() -> new ResoureNotFoundException("Catalog", "ID", productDto.getCatalogID()));

           //lấy ảnh của productDto
            Set<MultipartFile> files = productDto.getFiles();
//product gốc
            Product product0 = productRepo.findById(productId).orElseThrow(() -> new ResoureNotFoundException("Product", "ID", productId));
//ds ảnh của product gốc
            List<ImageProduct> imageProductList = product0.getListImage();
//xóa ảnh trong db
            for (ImageProduct image : imageProductList) {
                image.setProduct(null);
                imageProductRepo.save(image);
                imageProductRepo.deleteById(image.getId());
            }

            for (ImageProduct image : product0.getListImage()) {
                File file = new File(root + "/" + image.getPath());

                file.delete();
            }
            product0.clearProductImages();//remove old image

            Product product = this.convertToProduct(productDto);
            product.setId(product0.getId());

            for (MultipartFile file : files) {
                UUID uuid = UUID.randomUUID();
                String uuidString = uuid.toString();
                String ext = FilenameUtils.getExtension(file.getOriginalFilename());//ext name
                Files.copy(file.getInputStream(), this.root.resolve(uuidString + "." + ext));
//rename file
                ImageProduct imageProduct = new ImageProduct();
                imageProduct.setProduct(product);
                imageProduct.setPath(uuidString + "." + ext);
                imageProduct.setTitle(uuidString + "." + ext);


                product.addProductImages(imageProduct);
            }
            Set<Color> colors = new HashSet<>();
            List<Integer>colorID = productDto.getColorsID();
            for (Integer i: colorID) {
                colors.add(colorRepo.findById(i).orElseThrow(()->new ResoureNotFoundException("Color", "ID", i)));
            }
            product.setColors(colors);

            List<Size>sizes = new ArrayList<>();
            List<Integer>sizesID = productDto.getSizesID();
            for(Integer i : sizesID){
                sizes.add(sizeRepo.findById(i).orElseThrow(()-> new ResoureNotFoundException("Size", "ID", i)));
            }
            product.setSizes(sizes);
            product.setCatalog(catalog);

            productRepo.save(product);

            return this.convertToProductDto(product);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return productDto;
    }

    @Override
    public void deleteProduct(Integer generalId) {
        Product product = productRepo.findById(generalId).orElseThrow(() -> new ResoureNotFoundException("General Product", "ID", generalId));
        List<ImageProduct> imageProductList = product.getListImage();
//        for (int i = 0; i < imageProductList.size(); i++) {
//            File file = new File(root + "/" + imageProductList.get(i).getPath());
//
//            file.delete();
//        }
        for (ImageProduct image : imageProductList) {
            File file = new File(root + "/" + image.getPath());
            file.delete();
        }
        productRepo.deleteById(product.getId());
    }

    public Product convertToProduct(ProductDto productDto) {
        Product product = this.modelMapper.map(productDto, Product.class);
//        List<Integer> sizesID = productDto.getSizesID();
////        Set<Integer> colorID = productDto.getColorsID();
//        Set<Size>lisSizes = new HashSet<>();
//        for (int i = 0; i < sizesID.size(); i++) {
//            int finalI = i;
//          Size size =  sizeRepo.findById(sizesID.get(i)).orElseThrow(()-> new ResoureNotFoundException("Size", "ID", sizesID.get(finalI)));
//        lisSizes.add(size);
//        }
//        product.setSizes(lisSizes);
////        for (Integer i :sizesID) {
////            product.getSizes().add(sizeRepo.findById(i).orElseThrow(()-> new ResoureNotFoundException("Size", "ID", i)));
////        }
////        for (Integer i :colorID) {
////            product.getColors().add(colorRepo.findById(i).orElseThrow(()-> new ResoureNotFoundException("Color", "ID", i)));
////        }
        return product;
    }

    public ProductDto convertToProductDto(Product product) {
        ProductDto productDto = this.modelMapper.map(product, ProductDto.class);
//        Set<Color> listColor = product.getColors();
//        for (Color color : listColor) {
//            productDto.getColorsID().add(color.getId());
//        }
        List<Size> listSize = product.getSizes();
        for (Size size : listSize) {
            productDto.getSizesID().add(size.getId());
        }

        Set<Color> listColor = product.getColors();
        for (Color color : listColor) {
            productDto.getColorsID().add(color.getId());
        }
        return productDto;
    }

    public Resource load(String name) {
        try {
            Path file = root.resolve(name);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists()) {
                return resource;
            } else {
                throw new RuntimeException("Could not read the file!!!");
            }
        } catch (Exception e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    @Override
    public List<ProductDto> searchProductByName(String productName) {
        List<ProductDto> productDtoList = new ArrayList<>();
        List<Product> productList = productRepo.searchByName(productName);
        for (Product product : productList){
            productDtoList.add(this.convertToProductDto(product));
        }
        return productDtoList;
    }
}
