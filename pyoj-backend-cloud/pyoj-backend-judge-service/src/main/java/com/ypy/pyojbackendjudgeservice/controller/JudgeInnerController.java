package com.ypy.pyojbackendjudgeservice.controller;

import com.ypy.pyojbackendcommon.exception.AppException;
import com.ypy.pyojbackendjudgeservice.service.JudgeService;
import com.ypy.pyojbackendserviceclient.service.JudgeFeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/inner")
public class JudgeInnerController implements JudgeFeignClient {
    @Resource
    private JudgeService judgeService;

    @Override
    @GetMapping("/do")
    public void doJudge(
            @RequestParam("submitId") Long submitId
    ) throws AppException {
        judgeService.doJudge(submitId);
    }
}
