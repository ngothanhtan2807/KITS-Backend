package com.kits.ecommerce.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;


@Entity
@Table(name = "image")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImageProduct extends  TimeAuditable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private  String title;

    private  String path;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id") // tên field khoá ngoại
    @JsonIgnore
    private Product product;
}