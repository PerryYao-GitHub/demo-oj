package com.ypy.pyojbackendcommon.feignclient;

import com.ypy.pyojbackendcommon.app.AppCode;
import com.ypy.pyojbackendcommon.exception.AppException;
import com.ypy.pyojbackendcommon.model.dto.UserAuthDTO;
import com.ypy.pyojbackendcommon.model.entity.User;
import com.ypy.pyojbackendcommon.utils.JwtUtils;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@FeignClient(name = "pyoj-backend-user-service", path = "/api/user/inner")
public interface UserFeignClient {

    @GetMapping("/get/by/id")
    User getById(@RequestParam("id") Long id);

    default UserAuthDTO getLoginUserAuthDTO(HttpServletRequest request) throws AppException {
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new AppException(AppCode.ERR_FORBIDDEN);
        }

        String token = authHeader.substring(7); // 去掉 "Bearer "
        return JwtUtils.parseToken(token); // 返回 UserAuthDTO
    }
}
