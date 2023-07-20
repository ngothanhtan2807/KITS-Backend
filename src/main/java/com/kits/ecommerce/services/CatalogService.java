package com.kits.ecommerce.services;

import com.kits.ecommerce.dtos.CatalogDto;
import com.kits.ecommerce.entities.Catalog;
import com.kits.ecommerce.entities.Color;


import java.util.List;

public interface CatalogService {
    CatalogDto createCatalog(CatalogDto catalogDto);
    CatalogDto updateCatalog(CatalogDto catalogDto,Integer catelogId);
    CatalogDto getCatalogById(Integer catelogId);
    List<CatalogDto> getAllCatalogs();
    void deleteCatalog(Integer catelogId);
    void addCatalogsService(List<Catalog> catalogs);

    void deleteCatalogsService(List<Integer> ids);

}
