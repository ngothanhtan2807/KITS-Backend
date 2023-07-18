package com.kits.ecommerce.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Entity

@Data
@Table(name = "image")
@NoArgsConstructor
@AllArgsConstructor
public class ImageProduct extends  TimeAuditable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private  String title;

    private  String path;

    @ManyToOne(fetch = FetchType.EAGER)//should use LAZY
//    @JsonBackReference
    @JoinColumn(name = "general_id")
    private  GeneralProduct generalProduct;


}