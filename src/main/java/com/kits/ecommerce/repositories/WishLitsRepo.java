package com.kits.ecommerce.repositories;

import com.kits.ecommerce.entities.WishList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WishLitsRepo extends JpaRepository<WishList, Integer> {
    WishList getWishListByUserID(Integer id);
}
