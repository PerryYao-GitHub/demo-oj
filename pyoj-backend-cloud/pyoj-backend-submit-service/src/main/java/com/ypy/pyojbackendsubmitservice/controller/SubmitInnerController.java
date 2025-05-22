package com.ypy.pyojbackendsubmitservice.controller;

import com.ypy.pyojbackendcommon.model.entity.Submit;
import com.ypy.pyojbackendserviceclient.service.SubmitFeignClient;
import com.ypy.pyojbackendsubmitservice.service.SubmitService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

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
