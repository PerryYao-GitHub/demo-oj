package com.ypy.pyojbackend.model.query;

import lombok.Data;

import java.util.List;

@Data
public class QuestionPageQuery extends PageQuery {

    private String title;

    private List<String> tags;
}
