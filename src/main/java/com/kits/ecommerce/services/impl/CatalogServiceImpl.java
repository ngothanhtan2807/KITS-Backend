package com.kits.ecommerce.services.impl;

import com.kits.ecommerce.dtos.CatalogDto;
import com.kits.ecommerce.entities.Catalog;
import com.kits.ecommerce.exeptions.ResoureNotFoundException;
import com.kits.ecommerce.repositories.CatalogRepo;
import com.kits.ecommerce.services.CatalogService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CatalogServiceImpl implements CatalogService {
    @Autowired
    CatalogRepo catalogRepo;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public CatalogDto createCatalog(CatalogDto catalogDto) {
        Catalog catalog = this.convertToCatalog(catalogDto);
        catalogRepo.save(catalog);
        return catalogDto;

    }

    @Override
    public CatalogDto updateCatalog(CatalogDto catalogDto, Integer catelogId) {
        Catalog catalog = catalogRepo.findById(catelogId).orElseThrow(()-> new ResoureNotFoundException("Catalog", "ID", catelogId));
        Catalog catalogNew = this.convertToCatalog(catalogDto);
        catalogNew.setCatalogId(catalog.getCatalogId());
        catalogRepo.save(catalogNew);
        return this.convertToCatalogDto(catalogNew);
    }

    @Override
    public CatalogDto getCatalogById(Integer catelogId) {
        Catalog catalog = catalogRepo.findById(catelogId).orElseThrow(()->new ResoureNotFoundException("Catalog", "ID", catelogId));

        return this.convertToCatalogDto(catalog);
    }

    @Override
    public List<CatalogDto> getAllCatalogs() {
        List<Catalog> catalogList = catalogRepo.findAll();
        List<CatalogDto> catalogDtos = new ArrayList<>();
        for (int i = 0; i< catalogList.size(); i++){
            catalogDtos.add(this.convertToCatalogDto(catalogList.get(i)));
        }
        return catalogDtos;
    }

    @Override
    public void deleteCatalog(Integer catelogId) {
        Catalog catalog = catalogRepo.findById(catelogId).orElseThrow(()->new ResoureNotFoundException("Catalog", "ID", catelogId));
        catalogRepo.deleteById(catalog.getCatalogId());
    }
    public CatalogDto convertToCatalogDto(Catalog catalog){
        return this.modelMapper.map(catalog, CatalogDto.class);

    }
    public Catalog convertToCatalog(CatalogDto catalogDto){
        return this.modelMapper.map(catalogDto, Catalog.class);

    }
}
