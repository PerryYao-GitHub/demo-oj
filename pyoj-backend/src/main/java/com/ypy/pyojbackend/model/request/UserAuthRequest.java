package com.ypy.pyojbackend.model.request;

import com.ypy.pyojbackend.model.entity.User;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UserAuthRequest {

    @NotBlank
    private String username;

    @NotBlank
    private String password;
}
