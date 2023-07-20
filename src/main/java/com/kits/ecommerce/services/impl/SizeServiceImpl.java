package com.kits.ecommerce.services.impl;

import com.kits.ecommerce.dtos.SizeDto;
import com.kits.ecommerce.entities.Size;
import com.kits.ecommerce.exeptions.ResoureNotFoundException;
import com.kits.ecommerce.repositories.SizeRepo;
import com.kits.ecommerce.services.SizeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SizeServiceImpl implements SizeService {
    @Autowired
    SizeRepo sizeRepo;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public SizeDto createSize(SizeDto sizeDto) {
        Size size = this.convertToSize(sizeDto);
        sizeRepo.save(size);
        return this.convertToSizeDto(size);
    }

    @Override
    public SizeDto updateSize(SizeDto sizeDto, Integer sizeId) {
        Size size = sizeRepo.findById(sizeId).orElseThrow(() -> new ResoureNotFoundException("Size", "ID", sizeId));
        Size size1 = this.convertToSize(sizeDto);
        size1.setId(size.getId());
        sizeRepo.save(size1);
        return this.convertToSizeDto(size1);
    }

    @Override
    public SizeDto getSizeById(Integer sizeId) {
        Size size = sizeRepo.findById(sizeId).orElseThrow(() -> new ResoureNotFoundException("Size", "ID", sizeId));

        return this.convertToSizeDto(size);
    }

    @Override
    public List<SizeDto> getAllSize() {
        List<Size> sizes = sizeRepo.findAll();
        List<SizeDto> sizeDtoList = new ArrayList<>();

        for (int i = 0; i < sizes.size(); i++) {
            sizeDtoList.add(this.convertToSizeDto(sizes.get(i)));
        }
        return sizeDtoList;
    }

    @Override
    public void deleteSize(Integer sizeId) {
        Size size = sizeRepo.findById(sizeId).orElseThrow(() -> new ResoureNotFoundException("Size", "ID", sizeId));
        sizeRepo.deleteById(size.getId());
    }

    @Override
    public SizeDto convertToSizeDto(Size size) {
        return this.modelMapper.map(size, SizeDto.class);
    }

    @Override
    public Size convertToSize(SizeDto sizeDto) {
        return this.modelMapper.map(sizeDto, Size.class);
    }

    @Override
    public void addSizesService(List<Size> sizes) {
        sizeRepo.saveAll(sizes);
    }

    @Override
    public void deleteSizesService(List<Integer> ids) {
        sizeRepo.deleteAllById(ids);
    }
}
