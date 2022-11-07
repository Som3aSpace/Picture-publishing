package com.yeshtery.picturepublishing.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseTemplete<T> {
    private boolean status;
    private String message;
    private T data;
}
