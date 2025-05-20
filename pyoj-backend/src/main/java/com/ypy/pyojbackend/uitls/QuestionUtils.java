package com.ypy.pyojbackend.uitls;

import com.ypy.pyojbackend.model.entity.Question;
import com.ypy.pyojbackend.model.enums.TagEnum;
import com.ypy.pyojbackend.model.vo.QuestionBriefVO;
import com.ypy.pyojbackend.model.vo.QuestionVO;

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
