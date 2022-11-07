package com.yeshtery.picturepublishing.model.dto;

import com.yeshtery.picturepublishing.model.entity.RoleEntity;
import lombok.Data;

import java.util.Set;

@Data
public class UserDto {
    private String userName;
    private String email;
    private Set<RoleEntity> roles;
}
