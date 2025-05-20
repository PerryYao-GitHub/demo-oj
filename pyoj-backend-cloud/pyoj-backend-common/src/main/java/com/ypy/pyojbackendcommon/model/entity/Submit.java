package com.ypy.pyojbackendcommon.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.ypy.pyojbackendcommon.handler.JudgeResultTypeHandler;
import com.ypy.pyojbackendcommon.model.judge.JudgeResult;
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

    @TableField(typeHandler = JudgeResultTypeHandler.class)
    private JudgeResult judgeResult;

    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    @TableLogic
    private Boolean isDeleted;
}
