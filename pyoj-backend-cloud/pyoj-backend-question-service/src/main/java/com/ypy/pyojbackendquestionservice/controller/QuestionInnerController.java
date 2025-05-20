package com.ypy.pyojbackendquestionservice.controller;

import com.ypy.pyojbackendcommon.model.entity.Question;
import com.ypy.pyojbackendquestionservice.service.QuestionService;
import com.ypy.pyojbackendserviceclient.service.QuestionFeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/inner")
public class QuestionInnerController implements QuestionFeignClient {

    @Resource
    private QuestionService questionService;

    @Override
    @GetMapping("/get/by/id")
    public Question getById(Long id) {
        return questionService.getById(id);
    }

    @Override
    @PostMapping("/update/by/id")
    public boolean updateById(Question question) {
        return questionService.updateById(question);
    }
}
