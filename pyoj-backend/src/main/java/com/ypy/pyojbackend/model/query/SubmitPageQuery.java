package com.ypy.pyojbackend.model.query;

import lombok.Data;

@Data
public class SubmitPageQuery extends PageQuery {

    private Long userId;

    private Long questionId;
}
