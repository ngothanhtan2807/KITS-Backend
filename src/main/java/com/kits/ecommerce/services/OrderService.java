package com.kits.ecommerce.services;

import com.kits.ecommerce.dtos.OrderDto;
import com.kits.ecommerce.dtos.ProductDto;
import com.kits.ecommerce.entities.Order;

import javax.servlet.http.HttpSession;
import java.util.List;

public interface OrderService {
    OrderDto findOrderById(int orderID);

    OrderDto updateOrderByID(int orderID);

    OrderDto findOrderByCode(String code);

    List<OrderDto>findOrderByUserID(int userID);

    List<OrderDto>findOrder();
    void saveOrder(Order order);

    void saveOrderService(OrderDto orderDto, HttpSession httpSession);

    Order convertToOrder(OrderDto orderDto);

    OrderDto convertToOrderDto(Order order);

    List<ProductDto> hotSaler();
}
