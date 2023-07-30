package com.kits.ecommerce.services;

import com.kits.ecommerce.dtos.WishListDto;
import com.kits.ecommerce.entities.WishList;

public interface WishListService {
    WishListDto getWishListByUser(Integer userID);

    WishListDto addToWishList(Integer userID, Integer productID);

    WishListDto convertToWishListDto(WishList wishList);

    WishList convertToWishList(WishListDto wishListDto);
}
