package com.ypy.pyojbackend.model.request;

import com.ypy.pyojbackend.judge.model.JudgeCase;
import com.ypy.pyojbackend.judge.model.JudgeConfig;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class QuestionRequest {

    private Long id;

    @NotBlank
    @Size(min = 1, max = 255)
    private String title;

    @NotBlank
    private String description;

    @NotBlank
    private List<String> tags;

    @Valid
    private JudgeCase judgeCase;

    @Valid
    private JudgeConfig judgeConfig;
}
