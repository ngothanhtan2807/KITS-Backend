package com.kits.ecommerce.repositories;

import com.kits.ecommerce.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.validation.Valid;
import java.util.List;

@Repository
public interface ProductRepo extends JpaRepository<Product, Integer> {

    @Query("select p from Product p where p.name like %:key% order by p.name")
    List<Product> searchByName(@Param("key") String name);

    @Query("SELECT distinct p FROM Product p JOIN p.sizes s JOIN p.colors c JOIN p.catalog ct WHERE 1=1 and (:sizeID is null or s.id = :sizeID) AND (:catalogID is null or ct.catalogId = :catalogID) AND (:colorID is null or c.id = :colorID) AND (:startPrice is null or p.price >= :startPrice) AND (:endPrice is null or p.price <= :endPrice)")
    List<Product> findProductsBySizeColorAndCatalog(@Param("sizeID") Integer sizeID, @Param("catalogID") Integer catalogId, @Param("colorID") Integer colorID, @Param("startPrice") double startPrice, @Param("endPrice") Double endPrice);

    @Query("select distinct p from Product p join p.length l where 1=1 and l.id =:lengthID")
    List<Product>filterByLength(@Param("lengthID")int id);

    @Query("select distinct p from Product p join p.catalog ct where 1=1 and ct.catalogId =:id")
    List<Product>filterByCatalog(@Param("id")Integer id);
//    @Query("select count p.id from Product  p where 1=1")
//    int count();
@Query("select sum(p.totalQuantity) from Product p")
    int totalQuantity();
}
