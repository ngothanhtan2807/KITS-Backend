package com.kits.ecommerce.repositories;

import com.kits.ecommerce.entities.Catalog;
import com.kits.ecommerce.entities.Length;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LengthRepo extends JpaRepository<Length,Integer> {
}
