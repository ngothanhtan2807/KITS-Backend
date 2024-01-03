package com.kits.ecommerce.repositories;

import com.kits.ecommerce.entities.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepo extends JpaRepository<Cart, Integer> {
    @Query("select c from Cart c where c.user.userId = :id")
    Cart findCartByUserID(Integer id);
}
