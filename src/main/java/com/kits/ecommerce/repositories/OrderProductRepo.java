package com.kits.ecommerce.repositories;

import com.kits.ecommerce.entities.OrderProduct;
import com.kits.ecommerce.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderProductRepo extends JpaRepository<OrderProduct, Integer> {

//    @Query("select o.product from OrderProduct o group by o.product.id order by sum (o.quantity) DESC limit 5")
//   @Query(value = "", nativeQuery = true)
//    List<Product> hotSaler();
}
