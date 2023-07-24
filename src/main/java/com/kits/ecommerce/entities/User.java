package com.kits.ecommerce.entities;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

import java.util.Date;
import java.util.List;


@Entity
@Table(name="users")
@NoArgsConstructor
@Data
public class User extends TimeAuditable implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;
    private String name;
    private String avatar;
    private String email;
    private String password;
    @Temporal(TemporalType.DATE)
    private Date birthdate;




    @ManyToMany( fetch = FetchType.EAGER)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> roles ;







}
