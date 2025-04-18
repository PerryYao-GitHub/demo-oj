package com.ypy.pyojbackend.codesandbox;

import lombok.Data;

@Data
public class ExecuteInfo {

    private Long memory; // kb

    private Long time; // ms

    private String message;
}
