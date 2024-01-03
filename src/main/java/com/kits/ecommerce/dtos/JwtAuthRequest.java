package com.kits.ecommerce.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class JwtAuthRequest {

    @Email
    @Column(unique = true, nullable = false)
    private String email;

    private String password;

}
