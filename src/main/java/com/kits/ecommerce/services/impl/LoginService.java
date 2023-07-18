package com.kits.ecommerce.services.impl;

import java.util.ArrayList;
import java.util.List;

import com.kits.ecommerce.dtos.LoginUserDTO;
import com.kits.ecommerce.entities.Role;
import com.kits.ecommerce.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;



@Service
public class LoginService implements UserDetailsService {

    @Autowired
    UserRepo userRepo;



    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        com.kits.ecommerce.entities.User st =
                userRepo.findByEmail(username);

        if (st == null) {
            throw new UsernameNotFoundException("not found");
        }

        List<SimpleGrantedAuthority> list =
                new ArrayList<SimpleGrantedAuthority>();

        for (Role role : st.getRoles()) {
            list.add(new SimpleGrantedAuthority(role.getRoleName()));
        }

        // tao user cua security
        // user dang nhap hien tai
        LoginUserDTO currentUser =
                new LoginUserDTO(username, st.getPassword(), list);

        currentUser.setId(st.getUserId());
        currentUser.setName(st.getName());

        //da hinh
        return currentUser;
    }
}