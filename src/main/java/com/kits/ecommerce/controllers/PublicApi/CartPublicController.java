package com.kits.ecommerce.controllers.PublicApi;

import com.kits.ecommerce.dtos.*;
import com.kits.ecommerce.entities.Color;
import com.kits.ecommerce.entities.Product;
import com.kits.ecommerce.entities.Size;
import com.kits.ecommerce.exeptions.ResoureNotFoundException;
import com.kits.ecommerce.repositories.ColorRepo;
import com.kits.ecommerce.repositories.ProductRepo;
import com.kits.ecommerce.repositories.SizeRepo;
import com.kits.ecommerce.services.CartService;
import com.kits.ecommerce.services.ColorService;
import com.kits.ecommerce.services.ProductService;
import com.kits.ecommerce.services.SizeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/api/public/cart")
@CrossOrigin
@Tag(name = "Cart")
public class CartPublicController {

    @Autowired
    CartService cartService;

    @GetMapping("/{id}")

    public ResponseEntity<CartDto> getCart(@PathVariable("id") Integer id) {

//        HttpSession httpSession = request.getSession();
//        CartDto cart = null;
//
//        if (httpSession.getAttribute("cart") != null) {
//            cart = (CartDto) httpSession.getAttribute("cart");
//        } else {
//            cart = new CartDto();
//            httpSession.setAttribute("cart", cart);
//        }

//        return ResponseEntity.ok(cart);
        return ResponseEntity.ok(cartService.getCart(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CartDto> addToCart(@PathVariable("id") Integer id, @RequestBody CartItemDto cartItem,
                                             final HttpServletRequest request, final HttpServletResponse response) {
//        HttpSession httpSession = request.getSession();
//        CartDto cart = null;
//
//        if (httpSession.getAttribute("cart") != null) {
//            cart = (CartDto) httpSession.getAttribute("cart");
//        } else {
//            cart = new CartDto();
//            httpSession.setAttribute("cart", cart);
//        }
//
//        List<CartItemDto> itemList = cart.getItemList();
//
//        Product product = productRepo.findById(cartItem.getProductID()).orElseThrow(() -> new ResoureNotFoundException("Product", "ID", cartItem.getProductID()));
//        ProductDto productDto = productService.convertToProductDto(product);
//
//        Color color = colorRepo.findById(cartItem.getColorID()).orElseThrow(() -> new ResoureNotFoundException("Color", "ID", cartItem.getColorID()));
//        Size size = sizeRepo.findById(cartItem.getSizeID()).orElseThrow(() -> new ResoureNotFoundException("Size", "ID", cartItem.getSizeID()));
//
//        cartItem.setColor(colorService.convertToColorDto(color));
//        cartItem.setSize(sizeService.convertToSizeDto(size));
//
//        cartItem.setProductName(productDto.getName());
//        cartItem.setPrice(productDto.getPrice());
//        cartItem.setImage(productDto.getListImage().get(0).getTitle());
//
//
//        int count = 0;
//        for (CartItemDto item : itemList) {
//            if (item.getProductID() == cartItem.getProductID()&& item.getColorID() == cartItem.getColorID()&&item.getSizeID() == cartItem.getSizeID()) {
//                item.setQuantity(item.getQuantity() + cartItem.getQuantity());
//                if (item.getQuantity() >= productDto.getTotalQuantity()) {
//                    item.setQuantity(productDto.getTotalQuantity());
//                }
//            } else {
//                count++;
//            }
//        }
//        if (count == itemList.size()) {
//            if(cartItem.getQuantity() >= productDto.getTotalQuantity()){
//                cartItem.setQuantity(productDto.getTotalQuantity());
//            }
//            itemList.add(cartItem);
//        }
//        for (CartItemDto item : itemList) {
//            item.setTotalPrice(item.getPrice() * item.getQuantity());
//        }
//        cart.setItemList(itemList);
//
//        httpSession.setAttribute("cart", cart);
//        return ResponseEntity.ok(cart);
        return ResponseEntity.ok(cartService.addToCart(id, cartItem));
    }

    @PutMapping("/delete/{userID}")
    public ResponseEntity<?> deleteCartItem(@PathVariable("userID") Integer userID, @RequestParam("id") int id, @RequestParam("size") int sizeID, @RequestParam("color") int colorID,
                                            final HttpServletRequest request, final HttpServletResponse response) {

//        CartDto cart = (CartDto) request.getSession().getAttribute("cart");
//        List<CartItemDto> itemList = cart.getItemList();
//        int size = itemList.size();
//        for (int i = 0; i < size; i++) {
//            if (itemList.get(i).getProductID() == id && itemList.get(i).getSizeID() == sizeID && itemList.get(i).getColorID() == colorID) {
//                itemList.remove(i);
//            }
//        }
//        cart.setItemList(itemList);
//        request.getSession().setAttribute("cart", cart);
//        return new ResponseEntity<>(new ApiResponse("Delete success!!!", true), HttpStatus.OK);
        cartService.deleteCartItem(userID, id, sizeID, colorID);
        return new ResponseEntity<>(new ApiResponse("Delete success!!!", true), HttpStatus.OK);
    }

    @PutMapping("/update/{userID}/{id}")
    public ResponseEntity<CartItemDto> updateCartItem(@PathVariable("userID") int userID,
                                                      @PathVariable("id") int id, @RequestBody CartItemDto cartItem) {
//        CartDto cart = (CartDto) request.getSession().getAttribute("cart");
//        List<CartItemDto> itemList = cart.getItemList();
//
//        Product product = productRepo.findById(cartItem.getProductID()).orElseThrow(() -> new ResoureNotFoundException("Product", "ID", cartItem.getProductID()));
//        ProductDto productDto = productService.convertToProductDto(product);
//
////        Color color = colorRepo.findById(cartItem.getColorID()).orElseThrow(() -> new ResoureNotFoundException("Color", "ID", cartItem.getColorID()));
////        Size size = sizeRepo.findById(cartItem.getSizeID()).orElseThrow(() -> new ResoureNotFoundException("Size", "ID", cartItem.getSizeID()));
//
//
//
//        for (CartItemDto item : itemList) {
//
//            if ((item.getProductID() == cartItem.getProductID()) && (item.getColorID() == cartItem.getColorID()) && (item.getSizeID() == cartItem.getSizeID())) {
//
//                item.setQuantity(item.getQuantity() + cartItem.getQuantity());
//                cartItem.setColor(item.getColor());
//                cartItem.setProductName(item.getProductName());
//                cartItem.setSize(item.getSize());
//                cartItem.setPrice(item.getPrice());
//                cartItem.setImage(item.getImage());
//                if (item.getQuantity() >= productDto.getTotalQuantity()) {
//                    item.setQuantity(productDto.getTotalQuantity());
//                }
//            }
//            else{
//                continue;
//            }
//            }
//
//        cartItem.setTotalPrice(cartItem.getPrice() * cartItem.getQuantity());
//
//        for (int i = 0; i < itemList.size(); i++) {
//            if ((itemList.get(i).getProductID() == cartItem.getProductID()) && (itemList.get(i).getColorID() == cartItem.getColorID()) && (itemList.get(i).getSizeID() == cartItem.getSizeID())) {
//                itemList.set(i, cartItem);
//            }
//        }
//        cart.setItemList(itemList);
//        request.getSession().setAttribute("cart", cart);

//        return ResponseEntity.ok(cartItem);
        return ResponseEntity.ok(cartService.updateCartItem(userID, id, cartItem));
    }


    @DeleteMapping("/delete-cart/{userID}")
    public ResponseEntity<?> deleteCart(@PathVariable("userID") int userID) {
//        CartDto cart = (CartDto) request.getSession().getAttribute("cart");
//        cart = null;
//        request.getSession().setAttribute("cart", cart);
//        return new ResponseEntity<>(new ApiResponse("Delete success", true), HttpStatus.OK);
        cartService.deleteCart(userID);
        return new ResponseEntity<>(new ApiResponse("Delete success", true), HttpStatus.OK);
    }

    @GetMapping("/count/{userID}")
    public ResponseEntity<Integer> countItem(@PathVariable("userID") int userID) {
//        CartDto cart = (CartDto) request.getSession().getAttribute("cart");
//        int count = 0;
//        if (cart == null) {
//            count = 0;
//        } else {
//            count = cart.getItemList().size();
//        }
//        return ResponseEntity.ok(count);
        return ResponseEntity.ok(cartService.countItem(userID));
    }

    @GetMapping("/total-price/{userID}")
    public ResponseEntity<Double> getTotalPrice(@PathVariable("userID") int userID) {
//        double total = 0;
//        CartDto cart = (CartDto) request.getSession().getAttribute("cart");
//        if (cart == null || cart.getItemList().size() == 0) {
//            total = 0;
//        } else {
//            List<CartItemDto> cartItems = cart.getItemList();
//
//            for (int i = 0; i < cartItems.size(); i++) {
//                total += cartItems.get(i).getTotalPrice();
//            }
//        }
//
//
//        return ResponseEntity.ok(total);
        return ResponseEntity.ok(cartService.totalPrice(userID));
    }
}
