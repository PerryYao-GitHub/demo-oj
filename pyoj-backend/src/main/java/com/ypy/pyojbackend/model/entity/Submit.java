package com.ypy.pyojbackend.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.ypy.pyojbackend.judge.model.JudgeInfo;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName(value = "r_submit", autoResultMap = true)
public class Submit implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Long id;

    private Long userId;

    private Long questionId;

    private Byte status;

    private Byte lang;

    private String code;

    @TableField(typeHandler = JacksonTypeHandler.class)
    private JudgeInfo judgeInfo;

    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    @TableLogic
    private Boolean isDeleted;
}
