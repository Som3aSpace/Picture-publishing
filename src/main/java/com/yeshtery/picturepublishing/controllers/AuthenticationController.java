package com.yeshtery.picturepublishing.controllers;

import com.yeshtery.picturepublishing.model.dto.*;
import com.yeshtery.picturepublishing.services.UserService;
import com.yeshtery.picturepublishing.services.serviceImpl.MyUserDetailsService;
import com.yeshtery.picturepublishing.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@RestController
public class AuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private MyUserDetailsService userDetailsService;

    @Autowired
    private JwtUtils jwtUtils;


    @Autowired
    private UserService userService;


    @RequestMapping(method = RequestMethod.POST,value = "/register")
    public ResponseTemplete<UserDto> register(@Valid @RequestBody AuthenticationRequest authenticationRequest){
        return userService.save(authenticationRequest);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception{
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),authenticationRequest.getPassword())
            );

        }
        catch (BadCredentialsException e){
            throw new Exception("Incorrect username or password",e);
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        final String jwt = jwtUtils.generateToken(userDetails);
        return ResponseEntity.ok(new AuthenticationResponse(jwt,userDetails.getUsername(), (Set<GrantedAuthority>) userDetails.getAuthorities()));

    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleException(MethodArgumentNotValidException ex) {

        Map<String, Object> map = new HashMap<>();
        map.put("isSuccess", false);
        map.put("data", null);
        map.put("status", HttpStatus.BAD_REQUEST);
        map.put("message", ex.getMessage());
        map.put("timeStamp", new Date());

        List<ValidationError> errors = new ArrayList<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            ValidationError vError = new ValidationError();
            vError.setFieldName(error.getField());
            vError.setError(error.getDefaultMessage());
            errors.add(vError);
        }

        map.put("fieldError", errors);
        ResponseEntity<Object> responseEntity = new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
        return responseEntity;
    }
}
