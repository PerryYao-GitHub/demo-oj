package com.ypy.pyojbackend.model.vo;

import com.ypy.pyojbackend.judge.model.JudgeInfo;
import com.ypy.pyojbackend.model.enums.LangEnum;
import com.ypy.pyojbackend.model.enums.SubmitStatusEnum;
import com.ypy.pyojbackend.model.entity.Submit;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * submit detail info
 */
@Data
public class SubmitVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    private Long userId;

    private Long questionId;

    private String status;

    private String lang;

    private String code;

    private JudgeInfo judgeInfo;

    private Date createTime;

    public static SubmitVO from(Submit submit) {
        SubmitVO vo = new SubmitVO();
        vo.setId(submit.getId());
        vo.setUserId(submit.getUserId());
        vo.setQuestionId(submit.getQuestionId());
        vo.setStatus(SubmitStatusEnum.valueTextMap.get(submit.getStatus()));
        vo.setLang(LangEnum.valueTextMap.get(submit.getLang()));
        vo.setCode(submit.getCode());
        vo.setCreateTime(submit.getCreateTime());
        return vo;
    }
}
