package com.kits.ecommerce.services;

import com.kits.ecommerce.dtos.CatalogDto;
import com.kits.ecommerce.dtos.LengthDto;
import com.kits.ecommerce.entities.Catalog;
import com.kits.ecommerce.entities.Length;

import java.util.List;

public interface LengthService {
    LengthDto createLength(LengthDto lengthDto);
    LengthDto updateLength(LengthDto lengthDto,Integer id);
    LengthDto getLengthById(Integer id);
    List<LengthDto> getAllLengths();
    void deleteLength(Integer id);
    void addLengthsService(List<Length> lengths);

    void deleteLengthsService(List<Integer> ids);
}
