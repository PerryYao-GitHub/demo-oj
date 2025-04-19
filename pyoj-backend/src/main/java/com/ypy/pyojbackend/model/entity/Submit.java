package com.ypy.pyojbackend.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.ypy.pyojbackend.codesandbox.ExecuteInfo;
import com.ypy.pyojbackend.common.LangEnum;
import com.ypy.pyojbackend.common.SubmitStatusEnum;
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

    private SubmitStatusEnum submitStatus;

    private Byte lang;

    private String code;

    @TableField(typeHandler = JacksonTypeHandler.class)
    private ExecuteInfo executeInfo;

    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    @TableLogic
    private Boolean isDeleted;
}
