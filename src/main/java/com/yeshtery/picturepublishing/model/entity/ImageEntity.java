package com.yeshtery.picturepublishing.model.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "Image")
@Data
public class ImageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "file_path",unique = true)
    private String filePath;

    @Column(name = "description")
    private String description;

    @Column(name = "category")
    private String category;

    @OneToOne
    @JoinColumn(name = "userEntity")
    private UserEntity userEntity;

    @OneToOne
    @JoinColumn(name = "status")
    private StatusEntity statusEntity;


}
