package com.ypy.pyojbackend.model.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class SubmitRequest {

    @NotBlank
    private Long questionId;

    @NotBlank
    private String lang;

    @NotBlank
    private String code;
}
