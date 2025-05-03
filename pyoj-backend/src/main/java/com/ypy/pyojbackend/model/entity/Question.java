package com.ypy.pyojbackend.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.ypy.pyojbackend.handler.JudgeCaseTypeHandler;
import com.ypy.pyojbackend.handler.JudgeConfigTypeHandler;
import com.ypy.pyojbackend.judge.model.JudgeCase;
import com.ypy.pyojbackend.judge.model.JudgeConfig;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@TableName(value = "t_question", autoResultMap = true) // 使用 JSON 映射需要加 autoResultMap
public class Question implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.ASSIGN_ID)
    private Long id; // question is important, using random id

    private String title;

    private String description;

    @TableField(typeHandler = JacksonTypeHandler.class)
    private List<Integer> tags;

    @TableField(typeHandler = JudgeCaseTypeHandler.class)
    private JudgeCase judgeCase;

    @TableField(typeHandler = JudgeConfigTypeHandler.class)
    private JudgeConfig judgeConfig;

    private Integer submitCnt;

    private Integer acceptedCnt;

    @TableLogic
    private Integer isDeleted;

    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
}
