package com.ypy.pyojbackendcommon.model.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UserAuthRequest {

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    private String oldPassword;
}
