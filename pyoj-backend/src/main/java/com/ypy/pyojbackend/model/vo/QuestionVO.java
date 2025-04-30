package com.ypy.pyojbackend.model.vo;

import com.ypy.pyojbackend.model.enums.TagEnum;
import com.ypy.pyojbackend.judge.model.JudgeConfig;
import com.ypy.pyojbackend.model.entity.Question;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

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

    public static QuestionVO from(Question question) {
        QuestionVO vo = new QuestionVO();
        vo.setId(question.getId());
        vo.setTitle(question.getTitle());
        vo.setDescription(question.getDescription());
        vo.setTags(question.getTags().stream().map(TagEnum.valueTextMap::get).collect(Collectors.toList()));
        vo.setSubmitCnt(question.getSubmitCnt());
        vo.setAcceptedCnt(question.getAcceptedCnt());
        vo.setJudgeConfig(question.getJudgeConfig());
        return vo;
    }
}
