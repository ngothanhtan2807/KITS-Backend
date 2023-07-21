package com.kits.ecommerce.controllers.PublicApi;

import com.kits.ecommerce.config.AppConstants;
import com.kits.ecommerce.dtos.*;
import com.kits.ecommerce.services.ProductService;
import org.hibernate.engine.jdbc.StreamUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;


@RestController
@RequestMapping("/api/public/")
public class ProductPublicController {
    @Autowired
    private ProductService productService;


    @GetMapping("/{name}")
    public ResponseEntity<?> searchProduct(@PathVariable("name") String name) {
        return ResponseEntity.status(HttpStatus.OK).body(productService.searchProductByName(name));
    }

    @GetMapping("/products")
    public ResponseEntity<PageDto<ProductDto>> getAllProducts(
            @RequestParam(name = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(name = "sortBy", defaultValue = AppConstants.SORT_PRODUCTS_BY, required = false) String sortBy,
            @RequestParam(name = "sortOrder", defaultValue = AppConstants.SORT_DIR, required = false) String sortOrder) {

        PageDto<ProductDto> productResponse = productService.getProductsHomePage(pageNumber, pageSize, sortBy, sortOrder);

        return new ResponseEntity<PageDto<ProductDto>>(productResponse, HttpStatus.FOUND);
    }

    @PostMapping("/")
    public ResponseEntity<List<ProductDto>> search(@ModelAttribute SearchDto searchDto) {
        return ResponseEntity.ok(productService.search(searchDto));
    }

    @GetMapping("/cart")

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

    @PostMapping("/cart")
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
    @DeleteMapping("/cart/delete/{id}")
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
    @PutMapping("/cart/update/{id}")
    public ResponseEntity<CartItem> updateCartItem(final HttpServletRequest request, final HttpServletResponse response,
                                                   @PathVariable("id")int id, @RequestBody CartItem cartItem){
        Cart cart = (Cart)request.getSession().getAttribute("cart");
        List<CartItem> itemList = cart.getItemList();

//        for (CartItem item:itemList) {
//            if(item.getProductID() == id){
//                item = cartItem;
//
//            }
//        }
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
}
