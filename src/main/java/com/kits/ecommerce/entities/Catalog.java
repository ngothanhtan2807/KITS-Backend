package com.kits.ecommerce.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name="catalog")
@NoArgsConstructor
@Data
public class Catalog extends TimeAuditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer catalogId;

    @Column(name="name",length = 100,nullable = false)
    private String catalogName;

    @OneToMany(mappedBy = "catalog",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JsonIgnore
    private  List<Product> products =new ArrayList<>();
}
