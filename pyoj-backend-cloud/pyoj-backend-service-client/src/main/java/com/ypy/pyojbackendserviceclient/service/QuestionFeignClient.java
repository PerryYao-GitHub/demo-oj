package com.ypy.pyojbackendserviceclient.service;

import com.ypy.pyojbackendcommon.model.entity.Question;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "pyoj-backend-question-service", path = "/api/question/inner")
public interface QuestionFeignClient {

    @GetMapping("/get/by/id")
    Question getById(@RequestParam("id") Long id);

    @PostMapping("/update/by/id")
    boolean updateById(@RequestBody Question question);
}
