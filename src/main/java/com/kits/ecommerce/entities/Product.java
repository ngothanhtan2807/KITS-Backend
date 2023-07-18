package com.kits.ecommerce.entities;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Entity

@Table(name="product")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Product extends TimeAuditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "general_id")
//    @JsonIgnore
    private GeneralProduct generalProduct;

    private double price;


    @ManyToOne
//    @JsonIgnore
//    @JsonBackReference
    @JoinColumn(name = "color_id")
    private Color color;

    @ManyToOne
//    @JsonIgnore
//    @JsonBackReference
    @JoinColumn(name = "size_id")
    private Size size;

    //    @OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "quantity_id")
//    private Quantity quantity;
    private int quantity;

}