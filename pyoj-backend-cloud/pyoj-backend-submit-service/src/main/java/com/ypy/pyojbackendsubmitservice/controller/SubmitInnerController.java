package com.ypy.pyojbackendsubmitservice.controller;

import com.ypy.pyojbackendcommon.feignclient.SubmitFeignClient;
import com.ypy.pyojbackendcommon.model.entity.Submit;
import com.ypy.pyojbackendsubmitservice.service.SubmitService;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Hidden
@RestController
@RequestMapping("/inner")
public class SubmitInnerController implements SubmitFeignClient {

    @Resource
    private SubmitService submitService;

    @Override
    @GetMapping("/get/by/id")
    public Submit getById(
            @RequestParam("id") Long id
    ) {
        return submitService.getById(id);
    }

    @Override
    @PostMapping("/update/by/id")
    public boolean updateById(
            @RequestBody Submit submit
    ) {
        return submitService.updateById(submit);
    }
}
