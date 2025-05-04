package com.ypy.pyojbackend.model.vo;

import com.ypy.pyojbackend.judge.model.JudgeConfig;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * question detail info
 * hide sensitive info, like judge case
 */
@Data
public class QuestionVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    private String title;

    private String description;

    private List<String> tags;

    private Integer submitCnt;

    private Integer acceptedCnt;

    private JudgeConfig judgeConfig;
}
