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

    @Query("select distinct o from Order o join o.user u where 1=1 and o.user.userId =:userID")
//    select distinct p from Product p join p.catalog ct where 1=1 and ct.catalogId =:id
    List<Order> findOrderByUserID(@Param("userID") int userID);
}
