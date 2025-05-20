package com.ypy.pyojbackendcommon.model.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * question simple info, used in list
 */
@Data
public class QuestionBriefVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    private String title;

    private List<String> tags;

    private Float acRate;
}
