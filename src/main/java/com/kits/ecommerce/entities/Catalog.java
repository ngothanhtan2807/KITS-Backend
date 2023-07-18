package com.kits.ecommerce.entities;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import com.kits.ecommerce.entities.GeneralProduct;
import com.kits.ecommerce.entities.TimeAuditable;

@Entity
@Table(name="catalogs")
@NoArgsConstructor
@Data
public class Catalog extends TimeAuditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer catalogId;

    @Column(name="name",length = 100,nullable = false)
    private String catalogName;

    @OneToMany(mappedBy = "catalog",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private  List<GeneralProduct> generalProducts =new ArrayList<>();


}
