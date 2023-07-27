package com.kits.ecommerce.controllers.PublicApi;

import com.kits.ecommerce.dtos.ApiResponse;
import com.kits.ecommerce.dtos.Cart;
import com.kits.ecommerce.dtos.OrderDto;
import com.kits.ecommerce.dtos.PaymentDto;
import com.kits.ecommerce.services.OrderService;
import com.kits.ecommerce.services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.util.Random;

@RestController
@RequestMapping("/api/payment")
public class PaymentPublicController {
    @Autowired
    PaymentService paymentService;

    @Autowired
    OrderService orderService;

    @PostMapping("/pay")
    public String pay(@RequestBody OrderDto orderDto, HttpServletRequest request) {
        try {
            Cart cart = (Cart)request.getSession().getAttribute("cart");
            long current = System.currentTimeMillis();
//            Random random = new Random();
//            long randomNumber = random.nextInt(90000) + 10000; // tạo số ngẫu nhiên từ 10000 đến 99999
            orderDto.setVnp_TxnRef(current);
            orderDto.setVnp_OrderInfo("thanh toan hoa don ORDER-"+current);
           double price = 0;
            for (int i = 0; i < cart.getItemList().size(); i++) {
                price+= cart.getItemList().get(i).getTotalPrice();
            }

            orderDto.setVnp_Ammount((long)price*100);
            return paymentService.payWithVNPAY(orderDto, request);

        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
    @PostMapping("/money")

    public ResponseEntity<?> createOrder(@RequestBody OrderDto orderDto, HttpSession httpSession){
        orderDto.setType(0);//truc tiep, status = false;
        long current = System.currentTimeMillis();
        orderDto.setCode("ORDER-"+current);
        orderDto.setVnp_OrderInfo("thanh toan hoa don ORDER-"+current);
        orderService.saveOrderService(orderDto, httpSession);
        return new ResponseEntity(new ApiResponse("success", true), HttpStatus.OK);

    }
}
