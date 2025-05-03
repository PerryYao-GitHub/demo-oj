package com.ypy.pyojbackend.model.vo;

import com.ypy.pyojbackend.judge.model.JudgeResult;
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

    private String username;

    private Long questionId;

    private String questionTitle;

    private String status;

    private String lang;

    private String code;

    private JudgeResult judgeResult;

    private Date createTime;
}
