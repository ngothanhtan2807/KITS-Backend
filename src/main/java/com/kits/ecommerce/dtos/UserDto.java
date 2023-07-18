package com.kits.ecommerce.dtos;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kits.ecommerce.entities.Role;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.*;

@NoArgsConstructor
@Data
public class UserDto {


    private Integer userId;

    @NotEmpty

    private String name;

    @Email(message = "Email address is not valid !!")
    private String email;

    @NotEmpty
    @Size(min = 3,max = 10 ,message = "Password must be min of 3 chars and max of 10 chars !!")
    private String password;


    @JsonFormat(pattern = "dd/MM/yyyy", timezone = "Asia/Ho_Chi_Minh")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date birthdate;

    private String avatar;

    @JsonIgnore
    private MultipartFile file;



    @JsonFormat(pattern = "dd/MM/yyyy", timezone = "Asia/Ho_Chi_Minh")
    private Date createdAt;


    private List<RoleDto> roles = new ArrayList<>();//roles[0].id=1







}
