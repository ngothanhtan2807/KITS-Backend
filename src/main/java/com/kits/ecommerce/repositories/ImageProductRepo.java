package com.kits.ecommerce.repositories;

import com.kits.ecommerce.entities.ImageProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageProductRepo extends JpaRepository<ImageProduct, Integer> {
    ImageProduct getImageProductByTitle(String title);
    @Query("delete from ImageProduct i where i.path = :path")
    void deleteByPath(@Param("path") String path);
}
