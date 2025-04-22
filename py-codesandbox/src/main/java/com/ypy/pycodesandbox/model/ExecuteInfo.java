package com.ypy.pycodesandbox.model;

import lombok.Data;

@Data
public class ExecuteInfo {

    private Integer exitValue;

    private String message;

    private Long time;

    private Long memory;
}
