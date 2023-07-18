package com.kits.ecommerce.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;



@NoArgsConstructor
@Data
public class RoleDto {
    @Id
    private Integer roleId;


    private String roleName;
}
