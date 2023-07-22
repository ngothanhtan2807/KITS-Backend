package com.kits.ecommerce.controllers.PublicApi;

import com.kits.ecommerce.dtos.ApiResponse;
import com.kits.ecommerce.dtos.Cart;
import com.kits.ecommerce.dtos.CartItem;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/api/public/cart")
public class CartPublicController {
    @GetMapping("")

    public ResponseEntity<Cart> getCart(final ModelMap model,
                                        final HttpServletRequest request, final HttpServletResponse response) {

        HttpSession httpSession = request.getSession();
        Cart cart = null;

        if (httpSession.getAttribute("cart") != null) {
            cart = (Cart) httpSession.getAttribute("cart");
        } else {
            cart = new Cart();
            httpSession.setAttribute("cart", cart);
        }

        return ResponseEntity.ok(cart);
    }

    @PostMapping("")
    public ResponseEntity<Cart> addToCart(final ModelMap model, @RequestBody CartItem cartItem,
                                          final HttpServletRequest request, final HttpServletResponse response) {
        HttpSession httpSession = request.getSession();
        Cart cart = null;

        if (httpSession.getAttribute("cart") != null) {
            cart = (Cart) httpSession.getAttribute("cart");
        } else {
            cart = new Cart();
            httpSession.setAttribute("cart", cart);
        }
        List<CartItem> itemList = cart.getItemList();
        int count = 0;
        for (CartItem item : itemList) {
            if (item.getProductID() == cartItem.getProductID()) {
                item.setQuantity(item.getQuantity() + cartItem.getQuantity());
            } else {
                count++;
            }
        }
        if (count == itemList.size()) {
            itemList.add(cartItem);
        }
        for (CartItem item :itemList) {
            item.setTotalPrice(item.getPrice()*item.getQuantity());
        }
        cart.setItemList(itemList);

        httpSession.setAttribute("cart", cart);
        return ResponseEntity.ok(cart);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?>deleteCartItem(@PathVariable("id")int id,
                                           final HttpServletRequest request, final HttpServletResponse response){

        Cart cart = (Cart) request.getSession().getAttribute("cart");
        List<CartItem>itemList = cart.getItemList();
        int size = itemList.size();
        for (int i = 0; i < size; i++) {
            if(itemList.get(i).getProductID() == id){
                itemList.remove(i);
            }
        }
        cart.setItemList(itemList);
        request.getSession().setAttribute("cart", cart);
        return new ResponseEntity<>(new ApiResponse("Delete success!!!", true), HttpStatus.OK);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<CartItem> updateCartItem(final HttpServletRequest request, final HttpServletResponse response,
                                                   @PathVariable("id")int id, @RequestBody CartItem cartItem){
        Cart cart = (Cart)request.getSession().getAttribute("cart");
        List<CartItem> itemList = cart.getItemList();

        cartItem.setTotalPrice(cartItem.getPrice()* cartItem.getQuantity());

        for (int i = 0; i < itemList.size(); i++) {
            if(itemList.get(i).getProductID() == id){
                itemList.set(i, cartItem);
            }
        }
        cart.setItemList(itemList);
        request.getSession().setAttribute("cart", cart);
        return ResponseEntity.ok(cartItem);
    }
    @DeleteMapping("/delete-cart")
    public ResponseEntity<?>deleteCart(final HttpServletRequest request, final HttpServletResponse response){
//        Cart cart = (Cart) request.getSession().getAttribute("cart");
//
        request.getSession().invalidate();
        return new ResponseEntity<>(new ApiResponse("Delete success", true), HttpStatus.OK);
    }
    @GetMapping("/count")
    public ResponseEntity<Integer>countItem(final HttpServletRequest request, final HttpServletResponse response){
        Cart cart = (Cart) request.getSession().getAttribute("cart");
        int count = 0;
        if(cart == null){
            count = 0;
        }else{
            count = cart.getItemList().size();
        }
        return ResponseEntity.ok(count);
    }
}
