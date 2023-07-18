package com.kits.ecommerce.repositories;

import com.kits.ecommerce.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface UserRepo extends JpaRepository<User,Integer> {
    User findByEmail(String email);

    boolean existsByEmail(String email);
}
