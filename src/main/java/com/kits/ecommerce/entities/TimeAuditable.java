package com.kits.ecommerce.entities;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Data;

@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class TimeAuditable {

    @CreatedDate // auto gen new date
    @Column(updatable = false)
    private Date createdAt;// java.util

    @LastModifiedDate
    private Date updatedAt;

}