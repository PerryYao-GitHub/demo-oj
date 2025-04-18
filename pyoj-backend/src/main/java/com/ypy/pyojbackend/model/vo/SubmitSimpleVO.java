package com.ypy.pyojbackend.model.vo;

import com.ypy.pyojbackend.model.enums.LangEnum;
import com.ypy.pyojbackend.model.enums.SubmitStatusEnum;
import lombok.Data;

import java.util.Date;

/**
 * submit simple info
 */
@Data
public class SubmitSimpleVO {

    private Long id;

    private Long userId;

    private Long questionId;

    private SubmitStatusEnum submitStatus;

    private LangEnum lang;

    private Date createTime;
}
