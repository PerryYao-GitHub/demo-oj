package com.ypy.pyojbackend.model.vo;

import com.ypy.pyojbackend.model.enums.TagEnum;
import lombok.Data;

import java.util.List;

/**
 * question simple info, used in list
 */
@Data
public class QuestionSimpleVO {

    private Long id;

    private String title;

    private List<TagEnum> tags;

    private Float acRate;
}
