package com.ypy.pyojbackend.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.ypy.pyojbackend.model.enums.LangEnum;
import com.ypy.pyojbackend.model.enums.TagEnum;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@TableName(value = "t_question", autoResultMap = true) // 使用 JSON 映射需要加 autoResultMap
public class Question {

    @TableId(type = IdType.ASSIGN_ID)
    private Long id; // question is important, using random id

    private String title;

    private String description;

    @TableField(typeHandler = JacksonTypeHandler.class)
    private List<TagEnum> tags;

    @TableField(typeHandler = JacksonTypeHandler.class)
    private JudgeCase judgeCase;

    @TableField(typeHandler = JacksonTypeHandler.class)
    private JudgeConfig judgeConfig;

    private Integer submitCnt;

    private Integer acceptedCnt;

    @TableLogic
    private Integer isDeleted;

    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    @Data
    public static class JudgeCase {
        private List<String> inputs;
        private List<String> outputs;
    }

    @Data
    public static class JudgeConfig {
        private Long timeLimit;      // ms
        private Long memoryLimit;    // MB
        private List<LangEnum> allowedLanguages; // Java: 0
    }
}
