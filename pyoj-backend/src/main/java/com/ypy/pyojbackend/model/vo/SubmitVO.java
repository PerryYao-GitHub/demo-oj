package com.ypy.pyojbackend.model.vo;

import com.ypy.pyojbackend.codesandbox.model.ExecuteInfo;
import com.ypy.pyojbackend.common.LangEnum;
import com.ypy.pyojbackend.common.SubmitStatusEnum;
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

    private String submitStatus;

    private String lang;

    private String code;

    private ExecuteInfo executeInfo;

    private Date createTime;

    public static SubmitVO from(Submit submit) {
        SubmitVO vo = new SubmitVO();
        vo.setId(submit.getId());
        vo.setUserId(submit.getUserId());
        vo.setQuestionId(submit.getQuestionId());
        vo.setSubmitStatus(SubmitStatusEnum.valueTextMap.get(submit.getSubmitStatus()));
        vo.setLang(LangEnum.valueTextMap.get(submit.getLang()));
        vo.setCode(submit.getCode());
        vo.setCreateTime(submit.getCreateTime());
        return vo;
    }
}
