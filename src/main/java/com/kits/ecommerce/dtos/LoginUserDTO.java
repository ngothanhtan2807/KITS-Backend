package com.kits.ecommerce.dtos;

import java.util.Collection;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;


import lombok.EqualsAndHashCode;


@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
public class LoginUserDTO extends User {
    private static final long serialVersionUID = 1L;
    private int id;
    private String name;

    public LoginUserDTO(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }
}
