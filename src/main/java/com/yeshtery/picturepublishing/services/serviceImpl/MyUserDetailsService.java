package com.yeshtery.picturepublishing.services.serviceImpl;

import com.yeshtery.picturepublishing.model.entity.RoleEntity;
import com.yeshtery.picturepublishing.model.entity.UserEntity;
import com.yeshtery.picturepublishing.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Service
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    private UserService userService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String name = userService.getByUsername(username).getUserName();
        String pass = userService.getByUsername(username).getPassword();
        return new User(name,pass, getAuthority(userService.getByUsername(username)));
    }


    private Set<SimpleGrantedAuthority> getAuthority(UserEntity user) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
        });
        return authorities;
    }
}
