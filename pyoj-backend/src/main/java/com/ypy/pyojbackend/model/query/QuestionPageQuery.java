package com.ypy.pyojbackend.model.query;

import com.ypy.pyojbackend.model.enums.TagEnum;

import java.util.List;

public class QuestionPageQuery extends PageQuery {

    private String title;

    private List<TagEnum> tags;
}
