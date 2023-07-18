package com.kits.ecommerce.repositories;

import com.kits.ecommerce.entities.GeneralProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GeneralProductRepo  extends JpaRepository<GeneralProduct, Integer> {
}
