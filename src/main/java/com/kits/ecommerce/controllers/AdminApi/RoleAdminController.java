package com.kits.ecommerce.controllers.AdminApi;


import com.kits.ecommerce.entities.Role;
import com.kits.ecommerce.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin/")
@CrossOrigin
public class RoleAdminController {
    @Autowired

    private RoleService roleService;

    @GetMapping("roles")
    public List<Role> getAllRolesController() {

        return roleService.getAllRoles();
    }
}
