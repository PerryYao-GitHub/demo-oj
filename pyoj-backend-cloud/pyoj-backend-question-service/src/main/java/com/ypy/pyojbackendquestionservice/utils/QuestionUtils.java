package com.ypy.pyojbackendquestionservice.utils;

import com.ypy.pyojbackendcommon.model.entity.Question;
import com.ypy.pyojbackendcommon.model.enums.TagEnum;
import com.ypy.pyojbackendcommon.model.vo.QuestionBriefVO;
import com.ypy.pyojbackendcommon.model.vo.QuestionVO;

import java.util.stream.Collectors;

public class QuestionUtils {

    public static QuestionVO toVO(Question question) {
        QuestionVO vo = new QuestionVO();
        vo.setId(question.getId());
        vo.setTitle(question.getTitle());
        vo.setDescription(question.getDescription());
        vo.setTags(question.getTags().stream().map(TagEnum.value2text::get).collect(Collectors.toList()));
        vo.setSubmitCnt(question.getSubmitCnt());
        vo.setAcceptedCnt(question.getAcceptedCnt());
        vo.setJudgeConfig(question.getJudgeConfig());
        return vo;
    }

    public static QuestionBriefVO toBriefVO(Question question) {
        QuestionBriefVO vo = new QuestionBriefVO();
        vo.setId(question.getId());
        vo.setTitle(question.getTitle());
        vo.setTags(question.getTags().stream().map(TagEnum.value2text::get).collect(Collectors.toList()));
        vo.setAcRate(question.getSubmitCnt().equals(0) ? 0f : (float) question.getAcceptedCnt() / question.getSubmitCnt());
        return vo;
    }
}
