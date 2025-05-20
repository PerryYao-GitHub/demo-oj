package com.ypy.pyojbackend.judge.codesandbox.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CodeSandboxRequest {
    private List<String> inputs;

    private String code;

    private Byte lang;
}
