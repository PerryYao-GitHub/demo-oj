package com.ypy.pyojbackend.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.ypy.pyojbackend.model.enums.TagEnum;
import com.ypy.pyojbackend.model.enums.UserRoleEnum;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@TableName(value = "t_user", autoResultMap = true)
public class User {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String username;

    private String password;

    private UserRoleEnum role;

    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    @TableLogic
    private Boolean isDeleted;


    @TableField(typeHandler = JacksonTypeHandler.class)
    private List<TagEnum> tags;
}
