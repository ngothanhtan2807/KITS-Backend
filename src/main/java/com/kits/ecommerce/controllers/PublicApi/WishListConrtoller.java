package com.kits.ecommerce.controllers.PublicApi;


import com.kits.ecommerce.dtos.ProductDto;
import com.kits.ecommerce.dtos.WishListDto;
import com.kits.ecommerce.entities.Product;
import com.kits.ecommerce.exeptions.ResoureNotFoundException;
import com.kits.ecommerce.repositories.ProductRepo;
import com.kits.ecommerce.services.ProductService;
import com.kits.ecommerce.services.WishListService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/api/public/wish-list")
@CrossOrigin
@Tag(name = "Wish List")
public class WishListConrtoller {
   @Autowired
    WishListService wishListService;
    @GetMapping("/{userID}")
    public ResponseEntity<WishListDto> getWish(@PathVariable("userID")Integer userID) {
//        HttpSession httpSession = request.getSession();
//        WishListDto wishList = null;
//
//        if (httpSession.getAttribute("wishList") != null) {
//            wishList = (WishListDto) httpSession.getAttribute("wishList");
//        } else {
//            wishList = new WishListDto();
//            httpSession.setAttribute("wishList", wishList);
//        }
//        return ResponseEntity.ok(wishList);
        return ResponseEntity.ok(wishListService.getWishListByUser(userID));
    }

    @GetMapping("/{userID}/{id}")
    public ResponseEntity<WishListDto> addToWishList(@PathVariable("userID")Integer userID, @PathVariable("id")Integer id) {

//        HttpSession httpSession = request.getSession();
//        WishListDto wishList = null;
//
//        if (httpSession.getAttribute("wishList") != null) {
//            wishList = (WishListDto) httpSession.getAttribute("wishList");
//        } else {
//            wishList = new WishListDto();
//            httpSession.setAttribute("wishList", wishList);
//        }
//        Product product = productRepo.findById(id).orElseThrow(() -> new ResoureNotFoundException("Product", "ID",id));
//        ProductDto productDto = productService.convertToProductDto(product);
//
//
//        List<ProductDto> productDtoListList = wishList.getWishList();
////        if(productDtoListList.contains(productDto)){
////            System.out.println("---------------------");
////            productDtoListList.remove(productDto);
////        }
////        else {
////            System.out.println("....................");
////            productDtoListList.add(productDto);
////        }
//        int count = 0;
//        for (int i = 0; i < productDtoListList.size(); i++) {
//            if(productDtoListList.get(i).getId() != productDto.getId()){
//                count++;
//            }
//        }
//        if(count == productDtoListList.size()){
//            productDtoListList.add(productDto);
//        }
//        else {
////            int size = ;
//            for (int i = 0; i < productDtoListList.size(); i++) {
//                if(productDtoListList.get(i).getId() == productDto.getId()){
//                    productDtoListList.remove(i);
//                }
//
//            }
//            productDtoListList.remove(productDto);
//        }
//
//
//        wishList.setWishList(productDtoListList);
//
//        httpSession.setAttribute("wishList", wishList);
//        return ResponseEntity.ok(wishList);
        return ResponseEntity.ok(wishListService.addToWishList(userID,id));
    }
}
