package com.kits.ecommerce.services.impl;

import com.kits.ecommerce.entities.Role;
import com.kits.ecommerce.repositories.RoleRepo;
import com.kits.ecommerce.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    RoleRepo roleRepo;
    @Override
    public List<Role> getAllRoles() {
        return roleRepo.findAll();
    }
}
