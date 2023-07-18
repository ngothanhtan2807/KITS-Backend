package com.kits.ecommerce.services;

import com.kits.ecommerce.dtos.GeneralProductDto;
import com.kits.ecommerce.entities.GeneralProduct;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.util.List;

public interface GeneralProductService {
    GeneralProductDto getGeneralProductById(Integer generalId);
    List<GeneralProductDto> getAllGeneral();
    GeneralProductDto createGeneral(GeneralProductDto generalProductDto) throws IOException;
    GeneralProductDto updateGeneral(GeneralProductDto generalProductDto, Integer generalId);
    void deleteGeneral(Integer generalId);
    Resource load (String name);
}
