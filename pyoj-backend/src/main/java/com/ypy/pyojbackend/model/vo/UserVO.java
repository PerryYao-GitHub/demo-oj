package com.ypy.pyojbackend.model.vo;

import com.ypy.pyojbackend.model.entity.User;
import com.ypy.pyojbackend.common.TagEnum;
import com.ypy.pyojbackend.common.UserRoleEnum;
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

    public static UserVO fromUser(User user) {
        if (user == null) return null;
        UserVO vo = new UserVO();
        vo.setId(user.getId());
        vo.setUsername(user.getUsername());
        vo.setRole(user.getRole());
        vo.setTags(user.getTags().stream().map(TagEnum.valueTextMap::get).collect(Collectors.toList()));
        return vo;
    }
}
