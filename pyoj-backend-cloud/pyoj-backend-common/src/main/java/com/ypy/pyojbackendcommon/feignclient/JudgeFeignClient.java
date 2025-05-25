package com.ypy.pyojbackendcommon.feignclient;

import com.ypy.pyojbackendcommon.exception.AppException;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "pyoj-backend-judge-service", path = "/api/judge/inner")
public interface JudgeFeignClient {

    @GetMapping("/do")
    void doJudge(@RequestParam("submitId") Long submitId) throws AppException;
}
