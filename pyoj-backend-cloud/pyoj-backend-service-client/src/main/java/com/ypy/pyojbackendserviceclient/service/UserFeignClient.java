package com.ypy.pyojbackendserviceclient.service;

import com.ypy.pyojbackendcommon.exception.AppException;
import com.ypy.pyojbackendcommon.model.dto.UserAuthDTO;
import com.ypy.pyojbackendcommon.model.entity.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@FeignClient(name = "pyoj-backend-user-service", path = "/api/user/inner")
public interface UserFeignClient {

    @GetMapping("/get/by/id")
    User getById(@RequestParam("id") Long id);

    default UserAuthDTO getLoginUserAuthDTO(HttpServletRequest request) throws AppException {
        return (UserAuthDTO) request.getSession().getAttribute("loginUser"); // todo: not safe, use JWT
    }
}
