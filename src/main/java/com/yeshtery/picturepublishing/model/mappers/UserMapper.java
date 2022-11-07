package com.yeshtery.picturepublishing.model.mappers;

import com.yeshtery.picturepublishing.model.dto.AuthenticationRequest;
import com.yeshtery.picturepublishing.model.entity.UserEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserEntity toEntity(AuthenticationRequest dto) {
        UserEntity user = new UserEntity();
        String pass = new BCryptPasswordEncoder().encode(dto.getPassword());
        user.setUserName(dto.getUsername());
        user.setPassword(pass);
        user.setEmail(dto.getEmail());
        return user;
    }
}
