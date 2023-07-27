package com.kits.ecommerce.repositories;

import com.kits.ecommerce.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepo extends JpaRepository<Order, Integer> {
    Order findByCode(String code);

    @Query("select o from Order o join User u where o.user.userId =:userID")
    List<Order> findOrderByUserID(@Param("userID") int userID);
}
