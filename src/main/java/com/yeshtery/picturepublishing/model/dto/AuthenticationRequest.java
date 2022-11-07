package com.yeshtery.picturepublishing.model.dto;

import com.yeshtery.picturepublishing.annotations.SecurityValid;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@SecurityValid
@Data
public class AuthenticationRequest {
    @Valid
    @NotNull
    private String username;
    @Valid
    @NotNull
    private String email;
    @NotNull
    private String password;
}
