package com.kits.ecommerce.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kits.ecommerce.dtos.ColorDto;
import com.kits.ecommerce.dtos.SizeDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItem extends TimeAuditable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private int productID;
    private String productName;
    private String image;
    private int sizeID;
    private int colorID;

    private int quantity;
    private double price;
    private Double totalPrice;

   @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Cart cart;
}
