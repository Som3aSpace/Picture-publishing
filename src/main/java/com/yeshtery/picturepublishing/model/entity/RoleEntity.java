package com.yeshtery.picturepublishing.model.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "Role")
@Data
public class RoleEntity {
    @Id
    private long id;

    @Column
    private String name;

    @Column
    private String description;
}
