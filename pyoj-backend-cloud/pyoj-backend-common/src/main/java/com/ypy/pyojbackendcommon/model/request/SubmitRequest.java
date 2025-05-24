package com.ypy.pyojbackendcommon.model.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class SubmitRequest {

    private Long userId;

    @NotBlank
    private Long questionId;

    @NotBlank
    private String lang;

    @NotBlank
    private String code;
}
