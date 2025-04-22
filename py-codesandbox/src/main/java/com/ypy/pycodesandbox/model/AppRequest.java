package com.ypy.pycodesandbox.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class AppRequest {

    private List<String> inputs;

    private String code;

    private Byte lang;
}
