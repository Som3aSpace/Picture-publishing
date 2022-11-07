package com.yeshtery.picturepublishing.services;

import com.yeshtery.picturepublishing.model.dto.AuthenticationRequest;
import com.yeshtery.picturepublishing.model.dto.AuthenticationResponse;
import com.yeshtery.picturepublishing.model.dto.ResponseTemplete;
import com.yeshtery.picturepublishing.model.dto.UserDto;
import com.yeshtery.picturepublishing.model.entity.UserEntity;

import java.util.List;

public interface UserService {
    UserEntity getByUsername(String username);
    ResponseTemplete<UserDto> save(AuthenticationRequest authenticationRequest);
}
