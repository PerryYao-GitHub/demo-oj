package com.ypy.pyojbackend.judge.model;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class JudgeCase implements Serializable {
    private static final long serialVersionUID = 1L;

    private List<String> inputs;

    private List<String> outputs;
}
