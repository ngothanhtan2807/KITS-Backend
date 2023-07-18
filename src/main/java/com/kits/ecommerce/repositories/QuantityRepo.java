package com.kits.ecommerce.repositories;

import com.kits.ecommerce.entities.Quantity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuantityRepo extends JpaRepository<Quantity, Integer> {
}
