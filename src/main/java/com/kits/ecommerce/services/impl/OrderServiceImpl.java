package com.kits.ecommerce.services.impl;

import com.kits.ecommerce.dtos.Cart;
import com.kits.ecommerce.dtos.CartItem;
import com.kits.ecommerce.dtos.OrderDto;
import com.kits.ecommerce.entities.Order;
import com.kits.ecommerce.entities.OrderProduct;
import com.kits.ecommerce.entities.Size;
import com.kits.ecommerce.entities.User;
import com.kits.ecommerce.exeptions.ResoureNotFoundException;
import com.kits.ecommerce.repositories.*;
import com.kits.ecommerce.services.OrderService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderRepo orderRepo;

    @Autowired
    OrderProductRepo orderProductRepo;

    @Autowired
    UserRepo userRepo;

    @Autowired
    ProductRepo productRepo;

    @Autowired
    SizeRepo sizeRepo;

    @Autowired
    ColorRepo colorRepo;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public OrderDto findOrderById(int orderID) {
        Order order = orderRepo.findById(orderID).orElseThrow(() -> new ResoureNotFoundException("Order", "ID", orderID));

        return convertToOrderDto(order);
    }

    @Override
    public OrderDto findOrderByCode(String code) {
        Order order = orderRepo.findByCode(code);
        return convertToOrderDto(order);
    }

    @Override
    public List<OrderDto> findOrderByUserID(int userID) {
        List<Order> orders = orderRepo.findOrderByUserID(userID);
        List<OrderDto>orderDtos = new ArrayList<>();

        for (int i = 0; i < orders.size(); i++) {
            orderDtos.add(convertToOrderDto(orders.get(i)));
        }

        return orderDtos;
    }

    @Override
    public List<OrderDto> findOrder() {
        List<Order>orders = orderRepo.findAll();
        List<OrderDto>orderDtos = new ArrayList<>();

        for (int i = 0; i < orders.size(); i++) {
            orderDtos.add(convertToOrderDto(orders.get(i)));
        }

        return orderDtos;
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void saveOrder(Order order) {
        try {
            orderRepo.save(order);

        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void saveOrderService(OrderDto orderDto, HttpSession httpSession) {
        try {
//            Object principal = org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication()
//                    .getPrincipal();
//            String email = ((User)principal).getEmail();
            User user = userRepo.findById(orderDto.getUserID()).orElseThrow(()-> new ResoureNotFoundException("User", "ID", orderDto.getUserID()));
//           String customerPhone

            Order order = convertToOrder(orderDto);
            order.setOrderProducts(new ArrayList<>());
            order.setCode("CODE-" + System.currentTimeMillis());
//            order.setCustomerEmail(orderDto.getCustomerEmail());
//            order.setCustomerAddress(orderDto.getCustomerAddress());
//            order.setCustomerName(orderDto.getCustomerName());
//            order.setCustomerPhone(orderDto.getCustomerPhone());

            order.setUser(user);

            Cart cart = (Cart) httpSession.getAttribute("cart");

            List<CartItem> cartItems = cart.getItemList();

            double totalPrice = 0;
            for (int i = 0; i < cartItems.size(); i++) {
                OrderProduct orderProduct = new OrderProduct();
                int finalI = i;
                orderProduct.setProduct(productRepo.findById(cartItems.get(i).getProductID()).orElseThrow(() -> new ResoureNotFoundException("Product", "ID", cartItems.get(finalI).getProductID())));
                orderProduct.setSize(sizeRepo.findById(cartItems.get(i).getSizeID()).orElseThrow(() -> new ResoureNotFoundException("Size", "ID", cartItems.get(finalI).getSizeID())));
                orderProduct.setColor(colorRepo.findById(cartItems.get(i).getColorID()).orElseThrow(() -> new ResoureNotFoundException("Color", "ID", cartItems.get(finalI).getColorID())));
                orderProduct.setQuantity(cartItems.get(i).getQuantity());
                orderProduct.setTotalPrice(cartItems.get(i).getTotalPrice());


                orderProduct.setOrder(order);
                order.addOderProdcut(orderProduct);
//                orderProductRepo.save(orderProduct);

                totalPrice+= cartItems.get(i).getTotalPrice();
            }
            order.setTotalPrice(totalPrice);
            orderRepo.save(order);
            httpSession.setAttribute("cart", null);
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Order convertToOrder(OrderDto orderDto) {
        return modelMapper.map(orderDto, Order.class);
    }

    @Override
    public OrderDto convertToOrderDto(Order order) {
        OrderDto orderDto = modelMapper.map(order, OrderDto.class);



        return  orderDto;
    }

}
