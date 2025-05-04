package com.ypy.pyojbackend.aop;

import com.ypy.pyojbackend.app.AppCode;
import com.ypy.pyojbackend.exception.AppException;
import com.ypy.pyojbackend.model.dto.UserAuthDTO;
import com.ypy.pyojbackend.service.UserService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@Aspect
@Component
public class AppAOP {

    @Resource
    private UserService userService;

    @Around("@annotation(authCheck) || @within(authCheck)")
    public Object doAuthCheck(ProceedingJoinPoint joinPoint, AuthCheck authCheck) throws Throwable {
        // Step 1: attain current request object
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes == null) return joinPoint.proceed(); // for non HTTP request, go ahead

        HttpServletRequest request = attributes.getRequest();

        // Step 2: attain login user through request
        UserAuthDTO loginUser = userService.getLoginUserAuthDTO(request); // this method's implement return fake user in dev-process

        // Step 3: attain role
        Byte requiredRole = authCheck.mustRole().getValue(); // mustRole return required role

        // Step 4: is matching?
        if (!Objects.equals(loginUser.getRole(), requiredRole)) {
            throw new AppException(AppCode.ERR_WRONG_AUTH);
        }

        // Step 5: go ahead
        return joinPoint.proceed();
    }

    @Around("@annotation(com.ypy.pyojbackend.aop.LoginRequired) || @within(com.ypy.pyojbackend.aop.LoginRequired)")
    public Object doLoginCheck(ProceedingJoinPoint joinPoint) throws Throwable {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes == null) return joinPoint.proceed();

        HttpServletRequest request = attributes.getRequest();
        UserAuthDTO loginUser = userService.getLoginUserAuthDTO(request);
        if (loginUser == null) {
            throw new AppException(AppCode.ERR_WRONG_AUTH);
        }
        return joinPoint.proceed();
    }
}
