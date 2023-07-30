package com.kits.ecommerce.services.impl;

import com.kits.ecommerce.dtos.ProductDto;
import com.kits.ecommerce.dtos.WishListDto;
import com.kits.ecommerce.entities.Product;
import com.kits.ecommerce.entities.WishList;
import com.kits.ecommerce.repositories.WishLitsRepo;
import com.kits.ecommerce.services.ProductService;
import com.kits.ecommerce.services.WishListService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class WishListServiceImpl implements WishListService {

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    ProductService productService;

    @Autowired
    WishLitsRepo wishLitsRepo;

    @Override
    public WishListDto getWishListByUser(Integer userID) {
        WishList wishList = wishLitsRepo.getWishListByUserID(userID);

        if(wishList == null){
            wishList = new WishList();
            wishList.setUserID(userID);
            wishList.setProductID(new HashSet<>());
            wishLitsRepo.save(wishList);
        }
        return convertToWishListDto(wishLitsRepo.getWishListByUserID(userID));
    }

    @Override
    public WishListDto addToWishList(Integer userID, Integer productID) {
        WishList wishList = wishLitsRepo.getWishListByUserID(userID);
        ProductDto productDto = productService.getProductById(productID);

        if(wishList == null){
            wishList = new WishList();
            wishList.setUserID(userID);
            Set<Integer>productIDs = new HashSet<>();
            productIDs.add(productID);
            wishList.setProductID(productIDs);
        }
        else {
            Set<Integer>productList = wishList.getProductID();
            if(productList.contains(productID)){
                productList.remove(productID);
            }
            else {
                productList.add(productID);
            }
        }
        wishLitsRepo.save(wishList);
        return convertToWishListDto(wishList);
    }

    @Override
    public WishListDto convertToWishListDto(WishList wishList) {
        WishListDto wishListDto = modelMapper.map(wishList, WishListDto.class);
        List<ProductDto> productDtos = new ArrayList<>();
        for (Integer i: wishList.getProductID()) {
            ProductDto productDto = productService.getProductById(i);
            productDtos.add(productDto);
        }
        wishListDto.setProductDtos(productDtos);
        return wishListDto;
    }

    @Override
    public WishList convertToWishList(WishListDto wishListDto) {
        return modelMapper.map(wishListDto, WishList.class);
    }
}
