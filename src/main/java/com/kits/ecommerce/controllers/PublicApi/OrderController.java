package com.kits.ecommerce.controllers.PublicApi;

import com.kits.ecommerce.dtos.ApiResponse;
import com.kits.ecommerce.dtos.OrderDto;
import com.kits.ecommerce.dtos.ProductDto;
import com.kits.ecommerce.entities.Order;
import com.kits.ecommerce.services.OrderService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin
@Tag(name = "Order")
public class OrderController {

    @Autowired
    OrderService orderService;

//    @Autowired
//    OrderProductService productService;
    @GetMapping("")
    public ResponseEntity<List<OrderDto>>getAllOrder(){
        return ResponseEntity.ok(orderService.findOrder());
    }
//    @PostMapping("")
//    public ResponseEntity<?>createOrder(@RequestBody OrderDto orderDto, HttpSession httpSession){
//        orderService.saveOrderService(orderDto, httpSession);
//return new ResponseEntity(new ApiResponse("success", true), HttpStatus.OK);
//
//    }
    @GetMapping("/{userID}")
    public ResponseEntity<List<OrderDto>>getOrdersByUser(@PathVariable("userID")Integer id){
        return  ResponseEntity.ok(orderService.findOrderByUserID(id));
    }
    @GetMapping("/order/{id}")
    public ResponseEntity<OrderDto>getOrderById(@PathVariable("id")Integer id){
        return ResponseEntity.ok(orderService.findOrderById(id));
    }
    @PostMapping("/update/{id}")
    public ResponseEntity<?>updateOrder(@PathVariable("id")Integer id){
        orderService.updateOrderByID(id);
        return new ResponseEntity<>(new ApiResponse("Success", true), HttpStatus.OK);
    }
    @GetMapping("/hot-saler")
    public ResponseEntity<List<ProductDto>> getHotSaler(){
        return ResponseEntity.ok(orderService.hotSaler());
    }
}
