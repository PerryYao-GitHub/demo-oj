package com.ypy.pyojbackend.model.query;

import lombok.Data;

import java.util.List;

@Data
public class QuestionPageQuery {
    private int pageNum = 1;

    private int pageSize = 10;

    private String title;

    private List<String> tags;

    private OrderBy orderBy;

    private OrderType orderType;

    public enum OrderType {
        ASC, DESC;
    }

    public enum OrderBy {
        AC_RATE;
    }
}
