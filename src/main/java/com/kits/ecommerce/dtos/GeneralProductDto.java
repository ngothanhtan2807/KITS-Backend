package com.kits.ecommerce.dtos;

import com.kits.ecommerce.entities.ImageProduct;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class GeneralProductDto {
    private Integer id;
    private String name;
    private String description;
    private String detail;

    private int catalogID;
    private List<MultipartFile> files = new ArrayList<>();
    List<ImageProductDto> listImage = new ArrayList<>();
}
