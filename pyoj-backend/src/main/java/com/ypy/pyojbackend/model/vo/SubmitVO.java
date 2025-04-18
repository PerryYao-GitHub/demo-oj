package com.ypy.pyojbackend.model.vo;

import com.ypy.pyojbackend.codesandbox.ExecuteInfo;
import com.ypy.pyojbackend.model.enums.LangEnum;
import com.ypy.pyojbackend.model.enums.SubmitStatusEnum;
import lombok.Data;

import java.util.Date;

/**
 * submit detail info
 */
@Data
public class SubmitVO {

    private Long id;

    private Long userId;

    private Long questionId;

    private SubmitStatusEnum submitStatus;

    private LangEnum lang;

    private String code;

    private ExecuteInfo executeInfo;

    private Date createTime;
}
