package com.ypy.pyojbackend.model.vo;

import com.ypy.pyojbackend.codesandbox.ExecuteInfo;
import com.ypy.pyojbackend.common.LangEnum;
import com.ypy.pyojbackend.common.SubmitStatusEnum;
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

    private SubmitStatusEnum submitStatus;

    private LangEnum lang;

    private String code;

    private ExecuteInfo executeInfo;

    private Date createTime;
}
