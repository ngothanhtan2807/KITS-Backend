package com.kits.ecommerce.controllers.PublicApi;


import com.kits.ecommerce.dtos.ProductDto;
import com.kits.ecommerce.dtos.WishList;
import com.kits.ecommerce.entities.Product;
import com.kits.ecommerce.exeptions.ResoureNotFoundException;
import com.kits.ecommerce.repositories.ProductRepo;
import com.kits.ecommerce.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/api/public/wish-list")
@CrossOrigin
public class WishListConrtoller {
    @Autowired
    ProductRepo productRepo;
    @Autowired
    ProductService productService;
    @GetMapping("")
    public ResponseEntity<WishList> getWish(final HttpServletRequest request) {
        HttpSession httpSession = request.getSession();
        WishList wishList = null;

        if (httpSession.getAttribute("wishList") != null) {
            wishList = (WishList) httpSession.getAttribute("wishList");
        } else {
            wishList = new WishList();
            httpSession.setAttribute("wishList", wishList);
        }
        return ResponseEntity.ok(wishList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<WishList> addToWishList(@PathVariable("id")int id, final HttpServletRequest request) {

        HttpSession httpSession = request.getSession();
        WishList wishList = null;

        if (httpSession.getAttribute("wishList") != null) {
            wishList = (WishList) httpSession.getAttribute("wishList");
        } else {
            wishList = new WishList();
            httpSession.setAttribute("wishList", wishList);
        }
        Product product = productRepo.findById(id).orElseThrow(() -> new ResoureNotFoundException("Product", "ID",id));
        ProductDto productDto = productService.convertToProductDto(product);


        List<ProductDto> productDtoListList = wishList.getWishList();
//        if(productDtoListList.contains(productDto)){
//            System.out.println("---------------------");
//            productDtoListList.remove(productDto);
//        }
//        else {
//            System.out.println("....................");
//            productDtoListList.add(productDto);
//        }
        int count = 0;
        for (int i = 0; i < productDtoListList.size(); i++) {
            if(productDtoListList.get(i).getId() != productDto.getId()){
                count++;
            }
        }
        if(count == productDtoListList.size()){
            productDtoListList.add(productDto);
        }
        else {
//            int size = ;
            for (int i = 0; i < productDtoListList.size(); i++) {
                if(productDtoListList.get(i).getId() == productDto.getId()){
                    productDtoListList.remove(i);
                }

            }
            productDtoListList.remove(productDto);
        }


        wishList.setWishList(productDtoListList);

        httpSession.setAttribute("wishList", wishList);
        return ResponseEntity.ok(wishList);
    }
}
