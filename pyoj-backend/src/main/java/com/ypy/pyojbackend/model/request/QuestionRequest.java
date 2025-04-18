package com.ypy.pyojbackend.model.request;

import com.ypy.pyojbackend.model.entity.Question;
import lombok.Data;

import java.util.List;

@Data
public class QuestionRequest {

    private String title;

    private String description;

    private List<String> tags;

    private Question.JudgeCase judgeCase;

    private Question.JudgeConfig judgeConfig;
}
