package com.yeshtery.picturepublishing.services.serviceImpl;

import com.yeshtery.picturepublishing.model.dto.AuthenticationRequest;
import com.yeshtery.picturepublishing.model.dto.AuthenticationResponse;
import com.yeshtery.picturepublishing.model.dto.ResponseTemplete;
import com.yeshtery.picturepublishing.model.dto.UserDto;
import com.yeshtery.picturepublishing.model.entity.RoleEntity;
import com.yeshtery.picturepublishing.model.entity.UserEntity;
import com.yeshtery.picturepublishing.model.mappers.UserMapper;
import com.yeshtery.picturepublishing.repositories.RoleRepository;
import com.yeshtery.picturepublishing.repositories.UserRepository;
import com.yeshtery.picturepublishing.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public UserEntity getByUsername(String username) {
        return userRepository.getByUserName(username);
    }


    @Override
    public ResponseTemplete<UserDto> save(AuthenticationRequest authenticationRequest) {
        UserMapper userMapper = new UserMapper();
        ModelMapper modelMapper = new ModelMapper();
        ResponseTemplete<UserDto> responseTemplete = new ResponseTemplete<>();
        UserEntity userEntity = userMapper.toEntity(authenticationRequest);
        RoleEntity role = roleRepository.getByName("USER");
        Set<RoleEntity> roleEntities = new HashSet<>();
        roleEntities.add(role);
        if(userEntity.getEmail().split("@")[1].contains("admin.yeshtery")){
            role = roleRepository.getByName("ADMIN");
            roleEntities.add(role);
        }
        userEntity.setRoles(roleEntities);
        try {
            userEntity = userRepository.save(userEntity);
            responseTemplete.setData(modelMapper.map(userEntity, UserDto.class));
            responseTemplete.setStatus(true);
            responseTemplete.setMessage("User is registered successfully");
        }
        catch (Exception e){
            responseTemplete.setData(null);
            responseTemplete.setStatus(false);
            responseTemplete.setMessage("Error while saving user");
        }
        return responseTemplete;
    }
}
