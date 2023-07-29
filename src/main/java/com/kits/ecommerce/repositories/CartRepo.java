package com.kits.ecommerce.repositories;

import com.kits.ecommerce.entities.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepo extends JpaRepository<Cart, Integer> {
    Cart findCartByUserId(Integer id);
}
