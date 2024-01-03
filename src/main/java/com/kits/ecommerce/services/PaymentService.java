package com.kits.ecommerce.services;

import com.kits.ecommerce.dtos.OrderDto;
import com.kits.ecommerce.dtos.PaymentDto;

import jakarta.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

public interface PaymentService {
    String payWithVNPAY(OrderDto orderDto, HttpServletRequest request) throws UnsupportedEncodingException;

    String payWithMoney(PaymentDto paymentDto, HttpServletRequest request);
}
