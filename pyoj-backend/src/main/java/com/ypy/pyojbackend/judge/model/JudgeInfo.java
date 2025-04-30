package com.ypy.pyojbackend.judge.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
public class JudgeInfo {

    private String message;

    private Byte status;

    private Long memory; // kb

    private Long time; // ms

    @AllArgsConstructor
    @Getter
    public enum Status {
        AC                  ((byte) 0),
        RUNNING_WRONG       ((byte) 1),
        ANSWER_WRONG        ((byte) 2),
        TIME_LIMIT_EXCEED   ((byte) 3),
        MEMORY_LIMIT_EXCEED ((byte) 4);

        private final Byte value;
    }
}
