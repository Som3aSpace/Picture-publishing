package com.yeshtery.picturepublishing.utils;

import com.yeshtery.picturepublishing.annotations.SecurityValid;
import com.yeshtery.picturepublishing.model.dto.AuthenticationRequest;
import com.yeshtery.picturepublishing.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SecurityValidator implements ConstraintValidator<SecurityValid, AuthenticationRequest> {
    @Autowired
    UserRepository userRepository;

    @Override
    public void initialize(SecurityValid constraintAnnotation) {
//        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(AuthenticationRequest authenticationRequest, ConstraintValidatorContext constraintValidatorContext) {
        constraintValidatorContext.disableDefaultConstraintViolation();
        if(userRepository.getByUserName(authenticationRequest.getUsername()) != null){

            constraintValidatorContext
                    .buildConstraintViolationWithTemplate("User already exists!")
                    .addPropertyNode("username").addConstraintViolation();
            return false;
        }
        if(userRepository.getByEmail(authenticationRequest.getEmail()) != null){

            constraintValidatorContext
                    .buildConstraintViolationWithTemplate("email already exists!")
                    .addPropertyNode("email").addConstraintViolation();
            return false;
        }
        if(!validateEmail(authenticationRequest.getEmail())){
            constraintValidatorContext
                    .buildConstraintViolationWithTemplate("email Not Valid!")
                    .addPropertyNode("email").addConstraintViolation();
            return false;
        }
        return true;
    }

    private boolean validateEmail(String email){
        String regex = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
