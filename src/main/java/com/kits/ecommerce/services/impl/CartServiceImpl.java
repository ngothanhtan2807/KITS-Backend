package com.kits.ecommerce.services.impl;

import com.kits.ecommerce.dtos.CartDto;
import com.kits.ecommerce.dtos.CartItemDto;
import com.kits.ecommerce.dtos.ProductDto;
import com.kits.ecommerce.entities.*;
import com.kits.ecommerce.exeptions.ResoureNotFoundException;
import com.kits.ecommerce.repositories.*;
import com.kits.ecommerce.services.CartService;
import com.kits.ecommerce.services.ColorService;
import com.kits.ecommerce.services.ProductService;
import com.kits.ecommerce.services.SizeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class CartServiceImpl implements CartService {

    @Autowired
    CartRepo cartRepo;

    @Autowired
    CartItemRepo cartItemRepo;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    ProductRepo productRepo;

    @Autowired
    ColorRepo colorRepo;

    @Autowired
    SizeRepo sizeRepo;
    @Autowired
    ColorService colorService;

    @Autowired
    SizeService sizeService;
    @Autowired
    ProductService productService;
    @Autowired
    UserRepo userRepo;

    @Override
    public CartDto getCart(Integer userID) {

        Cart cart = cartRepo.findCartByUserID(userID);
        if (cart == null) {
            var user = userRepo.findById(userID).orElseThrow(() -> new ResoureNotFoundException("User", "Id", userID));
            cart = new Cart();
            cart.setUser(user);
            cart.setItemList(new ArrayList<>());
        }
        return convertToCartDto(cart);
    }

    @Override
    public CartDto addToCart(Integer userID, CartItemDto cartItemDto) {
        Cart cart = cartRepo.findCartByUserID(userID);
        if (cart == null) {
            var user = userRepo.findById(userID).orElseThrow(() -> new ResoureNotFoundException("User", "Id", userID));
            cart = new Cart();
            cart.setUser(user);
            cart.setItemList(new ArrayList<>());
        }
        System.out.println("____________" + cart.getId());
//        CartDto cartDto = convertToCartDto(cart);
//        List<CartItemDto> itemList = cartDto.getItemList();

        Product product = productRepo.findById(cartItemDto.getProductID()).orElseThrow(() -> new ResoureNotFoundException("Product", "ID", cartItemDto.getProductID()));
        ProductDto productDto = productService.convertToProductDto(product);

        Color color = colorRepo.findById(cartItemDto.getColorID()).orElseThrow(() -> new ResoureNotFoundException("Color", "ID", cartItemDto.getColorID()));
        Size size = sizeRepo.findById(cartItemDto.getSizeID()).orElseThrow(() -> new ResoureNotFoundException("Size", "ID", cartItemDto.getSizeID()));

        List<CartItem> cartItems = cart.getItemList();


        int count = 0;
        for (CartItem item : cartItems) {
            if (item.getProductID() == cartItemDto.getProductID() && item.getColorID() == cartItemDto.getColorID() && item.getSizeID() == cartItemDto.getSizeID()) {
                item.setQuantity(item.getQuantity() + cartItemDto.getQuantity());
                if (item.getQuantity() >= productDto.getTotalQuantity()) {
                    item.setQuantity(productDto.getTotalQuantity());
                }
//                cartItemRepo.save(convertToCartItem(item));
            } else {
                count++;
            }
        }

        if (count == cartItems.size()) {
            if (cartItemDto.getQuantity() >= productDto.getTotalQuantity()) {
                cartItemDto.setQuantity(productDto.getTotalQuantity());
            }
//            cartItemDto.setColor(colorService.convertToColorDto(color));
//            cartItemDto.setSize(sizeService.convertToSizeDto(size));
//
            cartItemDto.setProductName(productDto.getName());
            cartItemDto.setPrice(productDto.getPrice());
            cartItemDto.setImage(productDto.getListImage().get(0).getTitle());
            CartItem cartItem = convertToCartItem(cartItemDto);
            cartItem.setCart(cart);
            cartItems.add(cartItem);
        }
        for (CartItem item : cartItems) {
            item.setTotalPrice(item.getPrice() * item.getQuantity());
        }
        cart.setItemList(cartItems);
        cartRepo.save(cart);

        return convertToCartDto(cart);
    }

    @Override
    public void deleteCartItem(int userID, int id, int sizeID, int colorID) {

        Cart cart = cartRepo.findCartByUserID(userID);
        List<CartItem> cartItems = cart.getItemList();
        for (int i = 0; i < cartItems.size(); i++) {
            if (cartItems.get(i).getProductID() == id && cartItems.get(i).getSizeID() == sizeID && cartItems.get(i).getColorID() == colorID) {
                cartItems.get(i).setCart(null);
                cartItemRepo.delete(cartItems.get(i));
                cartItems.remove(i);

            }
        }
        cart.setItemList(cartItems);
        cartRepo.save(cart);
    }

    @Override
    public CartItemDto updateCartItem(int userID, int itemID, CartItemDto cartItemDto) {
        Cart cart = cartRepo.findCartByUserID(userID);

        List<CartItem> cartItems = cart.getItemList();
        Product product = productRepo.findById(cartItemDto.getProductID()).orElseThrow(() -> new ResoureNotFoundException("Product", "ID", cartItemDto.getProductID()));
        ProductDto productDto = productService.convertToProductDto(product);
        CartItem cartItem = null;
        for (CartItem item : cartItems) {

            if ((item.getId() == itemID) && (item.getProductID() == cartItemDto.getProductID()) && (item.getColorID() == cartItemDto.getColorID()) && (item.getSizeID() == cartItemDto.getSizeID())) {

                item.setQuantity(cartItemDto.getQuantity());

                if (item.getQuantity() >= productDto.getTotalQuantity()) {
                    item.setQuantity(productDto.getTotalQuantity());
                }
                item.setTotalPrice(cartItemDto.getPrice() * cartItemDto.getQuantity());
                cartItem = item;
            } else {
                continue;
            }
        }

        cart.setItemList(cartItems);

        cartRepo.save(cart);
        return convertToCartItemDto(cartItem);
    }

    @Override
    public void deleteCart(int userID) {
        Cart cart = cartRepo.findCartByUserID(userID);
        List<CartItem> cartItems = cart.getItemList();
        for (int i = 0; i < cartItems.size(); i++) {
            cartItems.get(i).setCart(null);
            cartItemRepo.delete(cartItems.get(i));
        }

        cartRepo.delete(cart);
    }

    @Override
    public int countItem(int userID) {
        Cart cart = cartRepo.findCartByUserID(userID);
        CartDto cartDto = convertToCartDto(cart);

        int count = cartDto.getItemList().size();

        return count;
    }

    @Override
    public double totalPrice(int userID) {

        double total = 0;
        Cart cart = cartRepo.findCartByUserID(userID);
        CartDto cartDto = convertToCartDto(cart);

        List<CartItemDto> cartItems = cartDto.getItemList();

        for (int i = 0; i < cartItems.size(); i++) {
            total += cartItems.get(i).getTotalPrice();

        }

        return total;
    }

    @Override
    public CartDto convertToCartDto(Cart cart) {

        CartDto cartDto = new CartDto();
        cartDto.setId(cart.getId());
        cartDto.setUserId(cart.getUser().getUserId());
        List<CartItem> cartItems = cart.getItemList();

        List<CartItemDto> cartItemDtos = new ArrayList<>();
        for (int i = 0; i < cartItems.size(); i++) {
            cartItemDtos.add(convertToCartItemDto(cartItems.get(i)));
        }
        cartDto.setItemList(cartItemDtos);
        return cartDto;
    }

    @Override
    public Cart convertToCart(CartDto cartDto) {
        Cart cart = new Cart();
        var user = userRepo.findById(cartDto.getUserId()).orElseThrow(() -> new ResoureNotFoundException("User", "Id", cartDto.getUserId()));

        cart.setUser(user);
        List<CartItemDto> cartItemDtos = cartDto.getItemList();

        List<CartItem> cartItems = new ArrayList<>();
        for (int i = 0; i < cartItemDtos.size(); i++) {
            cartItems.add(convertToCartItem(cartItemDtos.get(i)));
        }
        cart.setItemList(cartItems);
        return cart;
    }

    @Override
    public CartItemDto convertToCartItemDto(CartItem cartItem) {
        CartItemDto cartItemDto = this.modelMapper.map(cartItem, CartItemDto.class);
        Color color = colorRepo.findById(cartItem.getColorID()).orElseThrow(() -> new ResoureNotFoundException("Color", "ID", cartItem.getColorID()));
        Size size = sizeRepo.findById(cartItem.getSizeID()).orElseThrow(() -> new ResoureNotFoundException("Size", "ID", cartItem.getSizeID()));

        cartItemDto.setColor(colorService.convertToColorDto(color));
        cartItemDto.setSize(sizeService.convertToSizeDto(size));
        return cartItemDto;
    }

    @Override
    public CartItem convertToCartItem(CartItemDto cartItemDto) {
        CartItem cartItem = new CartItem();
        cartItem.setId(cartItemDto.getId());
        cartItem.setProductID(cartItemDto.getProductID());
        cartItem.setProductName(cartItemDto.getProductName());
        cartItem.setImage(cartItemDto.getImage());
        cartItem.setSizeID(cartItemDto.getSizeID());
        cartItem.setColorID(cartItemDto.getColorID());
        cartItem.setQuantity(cartItemDto.getQuantity());
        cartItem.setPrice(cartItemDto.getPrice());
        cartItem.setTotalPrice(cartItemDto.getTotalPrice());
        return cartItem;
    }
}
