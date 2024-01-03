package com.kits.ecommerce.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

@Entity
@Table(name = "blogs")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Blog extends TimeAuditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer blogId;

    @Column(name="name",length = 100,nullable = false)
    private String title;
    @Column(length = 10000)
    private String content;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    @JoinColumn(name="category_id")
    private Category category;
}
