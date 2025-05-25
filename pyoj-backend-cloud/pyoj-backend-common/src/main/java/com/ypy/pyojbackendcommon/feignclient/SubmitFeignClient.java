package com.ypy.pyojbackendcommon.feignclient;

import com.ypy.pyojbackendcommon.model.entity.Submit;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "pyoj-backend-submit-service", path = "/api/submit/inner")
public interface SubmitFeignClient {

    @GetMapping("/get/by/id")
    Submit getById(@RequestParam("id") Long id);

    @PostMapping("/update/by/id")
    boolean updateById(@RequestBody Submit submit);
}
