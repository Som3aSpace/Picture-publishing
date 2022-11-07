package com.yeshtery.picturepublishing.model.dto;

import com.yeshtery.picturepublishing.model.entity.RoleEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.util.Set;

@Data
@AllArgsConstructor
public class AuthenticationResponse {
    private String jwt;
    private String username;
    private Set<GrantedAuthority> roles;
}
