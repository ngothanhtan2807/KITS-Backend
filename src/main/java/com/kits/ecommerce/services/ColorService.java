package com.kits.ecommerce.services;

import com.kits.ecommerce.dtos.CatalogDto;
import com.kits.ecommerce.dtos.ColorDto;
import com.kits.ecommerce.entities.Color;

import java.util.List;

public interface ColorService {
    ColorDto createColor(ColorDto colorDto);
    ColorDto updateColor(ColorDto colorDto,Integer colorId);
    ColorDto getColorById(Integer colorId);
    List<ColorDto> getAllColor();
    void deleteColor(Integer colorId);
    ColorDto convertToColorDto(Color color);
    Color convertToColor(ColorDto colorDto);

}

