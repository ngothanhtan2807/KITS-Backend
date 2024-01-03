package com.kits.ecommerce.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order extends TimeAuditable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "total_price")
    private double totalPrice;

    @Column(name = "customer_phone")
    private String customerPhone;

    @Column(name = "customer_name")
    private String customerName;

    @Column(name = "customer_address")
    private String customerAddress;

    @Column(name = "customer_email")
    private String customerEmail;

    private String code;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "order"
            , fetch = FetchType.LAZY)
    private List<OrderProduct> orderProducts;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private Boolean status;

    public void addOderProdcut(OrderProduct orderProduct){
        orderProduct.setOrder(this);
        orderProducts.add(orderProduct);
    }
}
