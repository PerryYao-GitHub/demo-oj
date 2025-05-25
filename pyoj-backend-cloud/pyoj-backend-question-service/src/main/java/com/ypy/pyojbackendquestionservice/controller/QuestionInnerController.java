package com.ypy.pyojbackendquestionservice.controller;

import com.ypy.pyojbackendcommon.feignclient.QuestionFeignClient;
import com.ypy.pyojbackendcommon.model.entity.Question;
import com.ypy.pyojbackendquestionservice.service.QuestionService;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Hidden
@RestController
@RequestMapping("/inner")
public class QuestionInnerController implements QuestionFeignClient {

    @Resource
    private QuestionService questionService;

    @Override
    @GetMapping("/get/by/id")
    public Question getById(
            @RequestParam("id") Long id
    ) {
        return questionService.getById(id);
    }

    @Override
    @PostMapping("/update/by/id")
    public boolean updateById(
            @RequestBody Question question
    ) {
        return questionService.updateById(question);
    }
}
