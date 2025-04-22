package com.ypy.pyojbackend.model.vo;

import com.ypy.pyojbackend.common.TagEnum;
import com.ypy.pyojbackend.model.entity.Question;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

/**
 * question simple info, used in list
 */
@Data
public class QuestionPageVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    private String title;

    private List<String> tags;

    private Float acRate;

    public static QuestionPageVO from(Question question) {
        QuestionPageVO vo = new QuestionPageVO();
        vo.setId(question.getId());
        vo.setTitle(question.getTitle());
        vo.setTags(question.getTags().stream().map(TagEnum.valueTextMap::get).collect(Collectors.toList()));
        vo.setAcRate(question.getSubmitCnt().equals(0) ? 0f : (float) question.getAcceptedCnt() / question.getSubmitCnt());
        return vo;
    }
}
