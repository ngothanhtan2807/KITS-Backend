package com.kits.ecommerce.services.impl;

import com.kits.ecommerce.dtos.GeneralProductDto;
import com.kits.ecommerce.dtos.ImageProductDto;
import com.kits.ecommerce.entities.Catalog;
import com.kits.ecommerce.entities.GeneralProduct;
import com.kits.ecommerce.entities.ImageProduct;
import com.kits.ecommerce.exeptions.ResoureNotFoundException;
import com.kits.ecommerce.repositories.CatalogRepo;
import com.kits.ecommerce.repositories.GeneralProductRepo;
import com.kits.ecommerce.repositories.ImageProductRepo;
import com.kits.ecommerce.services.GeneralProductService;
import org.apache.commons.io.FilenameUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class GeneralProductServiceImpl implements GeneralProductService {
    private final Path root = Paths.get("uploads");

    @Autowired
    GeneralProductRepo generalProductRepo;

    @Autowired
    CatalogRepo catalogRepo;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    ImageProductRepo imageProductRepo;

    @Override
    public GeneralProductDto getGeneralProductById(Integer generalId) {
        GeneralProduct generalProduct = generalProductRepo.findById(generalId).orElseThrow(() -> new ResoureNotFoundException("General Product", "ID", generalId));

        return this.convertToGeneralProductDto(generalProduct);
    }

    @Override
    public List<GeneralProductDto> getAllGeneral() {
        List<GeneralProduct> generalProducts = generalProductRepo.findAll();
        List<GeneralProductDto> generalProductDtoList = new ArrayList<>();
        for (int i = 0; i < generalProducts.size(); i++) {
            GeneralProductDto generalProductDto = convertToGeneralProductDto(generalProducts.get(i));
            generalProductDtoList.add(generalProductDto);
        }
        return generalProductDtoList;
    }

    @Override
    public GeneralProductDto createGeneral(GeneralProductDto generalProductDto) {

        try {
            Catalog catalog = catalogRepo.findById(generalProductDto.getCatalogID()).orElseThrow(() -> new ResoureNotFoundException("Catalog", "ID", generalProductDto.getCatalogID()));
            List<MultipartFile> files = generalProductDto.getFiles();

            GeneralProduct generalProduct = this.convertToGeneralProduct(generalProductDto);

            for (int i = 0; i < files.size(); i++) {
                UUID uuid = UUID.randomUUID();
                String uuidString = uuid.toString();
                String ext = FilenameUtils.getExtension(files.get(i).getOriginalFilename());//ext name
                Files.copy(files.get(i).getInputStream(), this.root.resolve(uuidString + "." + ext));
//rename file
                ImageProduct imageProduct = new ImageProduct();
                imageProduct.setGeneralProduct(generalProduct);
                imageProduct.setPath(uuidString + "." + ext);
                imageProduct.setTitle(uuidString + "." + ext);


                generalProduct.addProductImages(imageProduct);
//                imageProductRepo.save(imageProduct);

            }
            generalProduct.setCatalog(catalog);
            generalProductRepo.save(generalProduct);

            return this.convertToGeneralProductDto(generalProduct);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return generalProductDto;
    }

    @Override
    public GeneralProductDto updateGeneral(GeneralProductDto generalProductDto, Integer generalId) {
        try {
            Catalog catalog = catalogRepo.findById(generalProductDto.getCatalogID()).orElseThrow(() -> new ResoureNotFoundException("Catalog", "ID", generalProductDto.getCatalogID()));
            List<MultipartFile> files = generalProductDto.getFiles();

            GeneralProduct generalProduct0 = generalProductRepo.findById(generalId).orElseThrow(() -> new ResoureNotFoundException("General", "ID", generalId));

            List<ImageProduct> imageProductList = generalProduct0.getListImage();
            for (int i = 0; i < imageProductList.size(); i++) {
                imageProductList.get(i).setGeneralProduct(null);
                imageProductRepo.save(imageProductList.get(i));
                imageProductRepo.deleteById(imageProductList.get(i).getId());
            }

            for (int i = 0; i < generalProduct0.getListImage().size(); i++) {
                File file = new File(root + "/" + generalProduct0.getListImage().get(i).getPath());

                file.delete();
            }

            generalProduct0.clearProductImages();//remove old image

            GeneralProduct generalProduct = this.convertToGeneralProduct(generalProductDto);
            generalProduct.setId(generalProduct0.getId());


            for (int i = 0; i < files.size(); i++) {
                UUID uuid = UUID.randomUUID();
                String uuidString = uuid.toString();
                String ext = FilenameUtils.getExtension(files.get(i).getOriginalFilename());//ext name
                Files.copy(files.get(i).getInputStream(), this.root.resolve(uuidString + "." + ext));
//rename file
                ImageProduct imageProduct = new ImageProduct();
                imageProduct.setGeneralProduct(generalProduct);
                imageProduct.setPath(uuidString + "." + ext);
                imageProduct.setTitle(uuidString + "." + ext);


                generalProduct.addProductImages(imageProduct);

            }
            generalProduct.setCatalog(catalog);
            generalProductRepo.save(generalProduct);

            return this.convertToGeneralProductDto(generalProduct);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return generalProductDto;
    }

    @Override
    public void deleteGeneral(Integer generalId) {
        GeneralProduct generalProduct = generalProductRepo.findById(generalId).orElseThrow(() -> new ResoureNotFoundException("General Product", "ID", generalId));
       List<ImageProduct>imageProductList = generalProduct.getListImage();
        for (int i = 0; i < imageProductList.size(); i++) {
            File file = new File(root + "/" + imageProductList.get(i).getPath());

            file.delete();
        }
        generalProductRepo.deleteById(generalProduct.getId());
    }

    public GeneralProduct convertToGeneralProduct(GeneralProductDto generalProductDto) {
        return this.modelMapper.map(generalProductDto, GeneralProduct.class);
    }

    public GeneralProductDto convertToGeneralProductDto(GeneralProduct generalProduct) {
        GeneralProductDto generalProductDto = this.modelMapper.map(generalProduct, GeneralProductDto.class);
        return generalProductDto;
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
}
