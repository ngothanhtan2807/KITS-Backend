package com.kits.ecommerce.repositories;

import com.kits.ecommerce.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepo extends JpaRepository<Product,Integer> {
//    List<Product> findByCatalog(Catalog catalog);
//
//    @Query("select p from Product p where p.name like:key")
//    List<Product> searchByName(@Param("key") String name);

//    List<Product> findByName(String name); khong dung cau query khi search khong lay duoc het


}
