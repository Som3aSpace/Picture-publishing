package com.yeshtery.picturepublishing.model.dto;

import lombok.Data;


@Data
public class ImageDto {
    private int id;
    private String category;
    private String description;
    private String filePath;
    private String userName;
}
