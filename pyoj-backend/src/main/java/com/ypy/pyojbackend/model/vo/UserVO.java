package com.ypy.pyojbackend.model.vo;

import com.ypy.pyojbackend.model.entity.User;
import com.ypy.pyojbackend.model.enums.TagEnum;
import com.ypy.pyojbackend.model.enums.UserRoleEnum;
import lombok.Data;

import java.util.List;

/**
 * user detail info
 */
@Data
public class UserVO {

    private Long id;

    private String username;

    private UserRoleEnum role;

    private List<TagEnum> tags;

    public static UserVO fromUser(User user) {
        if (user == null) return null;
        UserVO vo = new UserVO();
        vo.setId(user.getId());
        vo.setUsername(user.getUsername());
        vo.setRole(user.getRole());
        vo.setTags(user.getTags());
        return vo;
    }
}
