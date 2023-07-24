package com.kits.ecommerce.controllers.PublicApi;

import com.kits.ecommerce.dtos.*;
import com.kits.ecommerce.entities.Color;
import com.kits.ecommerce.entities.Product;
import com.kits.ecommerce.entities.Size;
import com.kits.ecommerce.exeptions.ResoureNotFoundException;
import com.kits.ecommerce.repositories.ColorRepo;
import com.kits.ecommerce.repositories.ProductRepo;
import com.kits.ecommerce.repositories.SizeRepo;
import com.kits.ecommerce.services.ColorService;
import com.kits.ecommerce.services.ProductService;
import com.kits.ecommerce.services.SizeService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    ColorRepo colorRepo;
    @Autowired
    SizeRepo sizeRepo;

    @Autowired
    SizeService sizeService;

    @Autowired
    ColorService colorService;

    @Autowired
    ProductService productService;

    @Autowired
    ProductRepo productRepo;

    @GetMapping("")

    public ResponseEntity<Cart> getCart(final HttpServletRequest request, final HttpServletResponse response) {

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

        Product product = productRepo.findById(cartItem.getProductID()).orElseThrow(() -> new ResoureNotFoundException("Product", "ID", cartItem.getProductID()));
        ProductDto productDto = productService.convertToProductDto(product);

        Color color = colorRepo.findById(cartItem.getColorID()).orElseThrow(() -> new ResoureNotFoundException("Color", "ID", cartItem.getColorID()));
        Size size = sizeRepo.findById(cartItem.getSizeID()).orElseThrow(() -> new ResoureNotFoundException("Size", "ID", cartItem.getSizeID()));

        cartItem.setColor(colorService.convertToColorDto(color));
        cartItem.setSize(sizeService.convertToSizeDto(size));

        cartItem.setProductName(productDto.getName());
        cartItem.setPrice(productDto.getPrice());
        cartItem.setImage(productDto.getListImage().get(0).getTitle());


        int count = 0;
        for (CartItem item : itemList) {
            if (item.getProductID() == cartItem.getProductID()) {
                item.setQuantity(item.getQuantity() + cartItem.getQuantity());
                if (item.getQuantity() >= productDto.getQuantity()) {
                    item.setQuantity(productDto.getQuantity());
                }
            } else {
                count++;
            }
        }
        if (count == itemList.size()) {
            if(cartItem.getQuantity() >= productDto.getQuantity()){
                cartItem.setQuantity(productDto.getQuantity());
            }
            itemList.add(cartItem);
        }
        for (CartItem item : itemList) {
            item.setTotalPrice(item.getPrice() * item.getQuantity());
        }
        cart.setItemList(itemList);

        httpSession.setAttribute("cart", cart);
        return ResponseEntity.ok(cart);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteCartItem(@PathVariable("id") int id,
                                            final HttpServletRequest request, final HttpServletResponse response) {

        Cart cart = (Cart) request.getSession().getAttribute("cart");
        List<CartItem> itemList = cart.getItemList();
        int size = itemList.size();
        for (int i = 0; i < size; i++) {
            if (itemList.get(i).getProductID() == id) {
                itemList.remove(i);
            }
        }
        cart.setItemList(itemList);
        request.getSession().setAttribute("cart", cart);
        return new ResponseEntity<>(new ApiResponse("Delete success!!!", true), HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<CartItem> updateCartItem(final HttpServletRequest request, final HttpServletResponse response,
                                                   @PathVariable("id") int id, @RequestBody CartItem cartItem) {
        Cart cart = (Cart) request.getSession().getAttribute("cart");
        List<CartItem> itemList = cart.getItemList();

        Product product = productRepo.findById(cartItem.getProductID()).orElseThrow(() -> new ResoureNotFoundException("Product", "ID", cartItem.getProductID()));
        ProductDto productDto = productService.convertToProductDto(product);

        Color color = colorRepo.findById(cartItem.getColorID()).orElseThrow(() -> new ResoureNotFoundException("Color", "ID", cartItem.getColorID()));
        Size size = sizeRepo.findById(cartItem.getSizeID()).orElseThrow(() -> new ResoureNotFoundException("Size", "ID", cartItem.getSizeID()));

        cartItem.setColor(colorService.convertToColorDto(color));
        cartItem.setSize(sizeService.convertToSizeDto(size));

        cartItem.setProductName(productDto.getName());
        cartItem.setPrice(productDto.getPrice());
        cartItem.setImage(productDto.getListImage().get(0).getTitle());

        for (CartItem item : itemList) {
            if (item.getProductID() == cartItem.getProductID()) {
                item.setQuantity(item.getQuantity() + cartItem.getQuantity());
                if (item.getQuantity() >= productDto.getQuantity()) {
                    item.setQuantity(productDto.getQuantity());
                }
            }}

        cartItem.setTotalPrice(cartItem.getPrice() * cartItem.getQuantity());

        for (int i = 0; i < itemList.size(); i++) {
            if (itemList.get(i).getProductID() == id) {
                itemList.set(i, cartItem);
            }
        }
        cart.setItemList(itemList);
        request.getSession().setAttribute("cart", cart);
        return ResponseEntity.ok(cartItem);
    }

    @DeleteMapping("/delete-cart")
    public ResponseEntity<?> deleteCart(final HttpServletRequest request, final HttpServletResponse response) {
//        Cart cart = (Cart) request.getSession().getAttribute("cart");
//
        request.getSession().invalidate();
        return new ResponseEntity<>(new ApiResponse("Delete success", true), HttpStatus.OK);
    }

    @GetMapping("/count")
    public ResponseEntity<Integer> countItem(final HttpServletRequest request, final HttpServletResponse response) {
        Cart cart = (Cart) request.getSession().getAttribute("cart");
        int count = 0;
        if (cart == null) {
            count = 0;
        } else {
            count = cart.getItemList().size();
        }
        return ResponseEntity.ok(count);
    }
}
