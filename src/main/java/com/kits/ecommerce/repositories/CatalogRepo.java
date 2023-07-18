package com.kits.ecommerce.repositories;

import com.kits.ecommerce.entities.Catalog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CatalogRepo extends JpaRepository<Catalog,Integer> {


}
