package com.kits.ecommerce.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "quantity")
@NoArgsConstructor
@Data
public class Quantity {
    @Id

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @OneToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private int quantity;

}
