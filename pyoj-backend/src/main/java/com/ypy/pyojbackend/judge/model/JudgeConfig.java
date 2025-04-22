package com.ypy.pyojbackend.judge.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class JudgeConfig implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long timeLimit;      // ms

    private Long memoryLimit;    // MB
}
