package com.kits.ecommerce.services;

import com.kits.ecommerce.dtos.CatalogDto;


import java.util.List;

public interface CatalogService {
    CatalogDto createCatalog(CatalogDto catalogDto);
    CatalogDto updateCatalog(CatalogDto catalogDto,Integer catelogId);
    CatalogDto getCatalogById(Integer catelogId);
    List<CatalogDto> getAllCatalogs();
    void deleteCatalog(Integer catelogId);
}
