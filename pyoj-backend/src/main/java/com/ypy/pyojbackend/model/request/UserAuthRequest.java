package com.ypy.pyojbackend.model.request;

import com.ypy.pyojbackend.model.entity.User;
import lombok.Data;

@Data
public class UserAuthRequest {

    private String username;

    private String password;

    public static User toUser(UserAuthRequest userAuthRequest) {
        User user = new User();
        user.setUsername(userAuthRequest.getUsername());
        user.setPassword(userAuthRequest.getPassword());
        return user;
    }
}
