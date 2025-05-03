package com.ypy.pyojbackend.model.vo;

import com.ypy.pyojbackend.model.enums.TagEnum;
import com.ypy.pyojbackend.model.enums.UserRoleEnum;
import com.ypy.pyojbackend.model.entity.User;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

/**
 * user detail info
 */
@Data
public class UserVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    private String username;

    private UserRoleEnum role;

    private List<String> tags;
}
