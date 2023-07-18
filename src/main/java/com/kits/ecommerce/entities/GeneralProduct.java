package com.kits.ecommerce.entities;

//import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "general")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class GeneralProduct extends  TimeAuditable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private String description;
    private String detail;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "generalProduct", fetch = FetchType.EAGER)//auto query sub query //should use LAZY
    List<ImageProduct> listImage = new ArrayList<>();

    @OneToMany(mappedBy = "generalProduct")
    List<Product> listProduct = new ArrayList<>();

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
//    @JsonBackReference
    @JoinColumn(name="catalog_id")
    private Catalog catalog;

    public void addProductImages(ImageProduct imageProduct) {
        imageProduct.setGeneralProduct(this);
        listImage.add(imageProduct);
    }
    public void addProduct(Product product) {
        product.setGeneralProduct(this);
        listProduct.add(product);
    }
    public void clearProductImages() {
        for (ImageProduct productImages : listImage) {
            productImages.setGeneralProduct(null);
        }
        listImage.clear();
    }
}
