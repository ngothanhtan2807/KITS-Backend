package com.kits.ecommerce.services.impl;

import com.kits.ecommerce.dtos.LengthDto;
import com.kits.ecommerce.entities.Length;
import com.kits.ecommerce.exeptions.ResoureNotFoundException;
import com.kits.ecommerce.repositories.LengthRepo;
import com.kits.ecommerce.services.LengthService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LengthServiceImpl implements LengthService {
    @Autowired
    LengthRepo lengthRepo;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public LengthDto createLength(LengthDto lengthDto) {
        Length length = this.convertToLength(lengthDto);
        lengthRepo.save(length);
        return convertToLengthDto(length);
    }

    @Override
    public LengthDto updateLength(LengthDto lengthDto, Integer id) {
        Length length = lengthRepo.findById(id).orElseThrow(() -> new ResoureNotFoundException("Length", "ID", id));

        Length length1 = convertToLength(lengthDto);
        length1.setId(length.getId());
        lengthRepo.save(length1);
        return convertToLengthDto(length1);
    }

    @Override
    public LengthDto getLengthById(Integer id) {
        Length length = lengthRepo.findById(id).orElseThrow(() -> new ResoureNotFoundException("Length", "ID", id));

        return convertToLengthDto(length);
    }

    @Override
    public List<LengthDto> getAllLengths() {
        List<Length> lengths = lengthRepo.findAll();
        List<LengthDto> lengthDtos = new ArrayList<>();
        for (int i = 0; i < lengths.size(); i++) {
            lengthDtos.add(convertToLengthDto(lengths.get(i)));
        }
        return lengthDtos;
    }

    @Override
    public void deleteLength(Integer id) {
        Length length = lengthRepo.findById(id).orElseThrow(() -> new ResoureNotFoundException("Length", "ID", id));

        lengthRepo.deleteById(length.getId());
    }

    @Override
    public void addLengthsService(List<Length> lengths) {
        lengthRepo.saveAll(lengths);
    }

    @Override
    public void deleteLengthsService(List<Integer> ids) {
        lengthRepo.deleteAllById(ids);
    }

    public LengthDto convertToLengthDto(Length length) {
        return this.modelMapper.map(length, LengthDto.class);

    }

    public Length convertToLength(LengthDto lengthDto) {
        return this.modelMapper.map(lengthDto, Length.class);

    }
}
