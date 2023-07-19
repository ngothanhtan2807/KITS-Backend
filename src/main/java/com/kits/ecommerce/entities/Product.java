package com.kits.ecommerce.entities;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.Set;


@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product extends TimeAuditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private String description;
    private String detail;


    @OneToMany(cascade = CascadeType.ALL, mappedBy = "product"/* tên property product trong class ProductImages */
            /*, fetch = FetchType.EAGER, orphanRemoval = true*/)
    private List<ImageProduct> listImage;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)

    @JoinColumn(name="catalog_id")
    private Catalog catalog;
    //
    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(name = "product_size", joinColumns = @JoinColumn(name = "product_id"), inverseJoinColumns = @JoinColumn(name = "size_id"))
    private List<Size> sizes;

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(name = "product_color", joinColumns = @JoinColumn(name = "product_id"), inverseJoinColumns = @JoinColumn(name = "color_id"))
    private Set<Color> colors;


    public void addProductImages(ImageProduct productImages1) {
        productImages1.setProduct(this);
        listImage.add(productImages1);
    }

    public void clearProductImages() {
        for (ImageProduct productImages : listImage) {
            productImages.setProduct(null);
        }
        listImage.clear();
    }
}