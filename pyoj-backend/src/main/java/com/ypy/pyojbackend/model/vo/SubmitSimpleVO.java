package com.ypy.pyojbackend.model.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * submit simple info
 */
@Data
public class SubmitSimpleVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    private Long userId;

    private Long questionId;

    private String submitStatus;

    private String lang;

    private Date createTime;
}
