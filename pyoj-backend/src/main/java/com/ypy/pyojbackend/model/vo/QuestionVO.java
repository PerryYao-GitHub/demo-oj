package com.ypy.pyojbackend.model.vo;

import com.ypy.pyojbackend.model.entity.Question;
import com.ypy.pyojbackend.model.enums.TagEnum;
import lombok.Data;

import java.util.List;

/**
 * question detail info
 * hide sensitive info, like judge case
 */
@Data
public class QuestionVO {

    private Long id;

    private String title;

    private String description;

    private List<TagEnum> tags;

    private Integer submitCnt;

    private Integer acceptedCnt;

    private Question.JudgeConfig judgeConfig;
}
