package com.kits.ecommerce.dtos;

import com.kits.ecommerce.entities.OrderProduct;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import java.io.Serializable;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {
    private Integer id;
    private String code;
    private Double totalPrice;
    private String customerPhone;
    private String customerName;
    private String customerAddress;
    private String customerEmail;
    private int userID;
    private List<OrderProductDto>orderProducts;

    public Long vnp_Ammount;
    public String vnp_OrderInfo;
    public String vnp_OrderType = "200000";
    public Long vnp_TxnRef;
//    private UserDto user;
    public Integer type;
    private Boolean status;

}
