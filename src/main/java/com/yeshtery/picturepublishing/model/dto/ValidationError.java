package com.yeshtery.picturepublishing.model.dto;

import lombok.Data;

@Data
public class ValidationError {
    private String fieldName;
    private String error;
    
}
