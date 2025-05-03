package com.ypy.pyojbackend.model.query;

import lombok.Data;

@Data
public class SubmitPageQuery {
    private int pageNum = 1;

    private int pageSize = 10;

    private Long userId;

    private Long questionId;
}
