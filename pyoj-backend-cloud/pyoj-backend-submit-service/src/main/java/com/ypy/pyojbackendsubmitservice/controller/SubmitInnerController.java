package com.ypy.pyojbackendsubmitservice.controller;

import com.ypy.pyojbackendcommon.model.entity.Submit;
import com.ypy.pyojbackendserviceclient.service.SubmitFeignClient;
import com.ypy.pyojbackendsubmitservice.service.SubmitService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/inner")
public class SubmitInnerController implements SubmitFeignClient {

    @Resource
    private SubmitService submitService;

    @Override
    @GetMapping("/get/by/id")
    public Submit getById(Long id) {
        return submitService.getById(id);
    }

    @Override
    @PostMapping("/update/by/id")
    public boolean updateById(Submit submit) {
        return submitService.updateById(submit);
    }
}
