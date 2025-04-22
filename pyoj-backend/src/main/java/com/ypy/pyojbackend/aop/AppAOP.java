package com.ypy.pyojbackend.aop;

import com.ypy.pyojbackend.common.AppCode;
import com.ypy.pyojbackend.common.UserRoleEnum;
import com.ypy.pyojbackend.exception.AppException;
import com.ypy.pyojbackend.model.entity.User;
import com.ypy.pyojbackend.service.UserService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
public class AppAOP {

    @Resource
    private UserService userService;

    @Around("@annotation(authCheck)")
    public Object doAuthCheck(ProceedingJoinPoint joinPoint, AuthCheck authCheck) throws Throwable {
        // Step 1: attain current request object
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes == null) return joinPoint.proceed(); // for non HTTP request, go ahead

        HttpServletRequest request = attributes.getRequest();

        // Step 2: attain login user through request
        User loginUser = userService.getLoginUser(request); // this method's implement return fake user in dev-process

        // Step 3: attain role
        UserRoleEnum requiredRole = authCheck.mustRole(); // mustRole return required role

        // Step 4: is matching?
        if (requiredRole != null && loginUser.getRole() != requiredRole) {
            throw new AppException(AppCode.ERR_WRONG_AUTH);
        }

        // Step 5: go ahead
        return joinPoint.proceed();
    }

    @Around("@annotation(LoginRequired)")
    public Object doLoginCheck(ProceedingJoinPoint joinPoint) throws Throwable {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes == null) return joinPoint.proceed();

        HttpServletRequest request = attributes.getRequest();
        User loginUser = userService.getLoginUser(request);
        if (loginUser == null) {
            throw new AppException(AppCode.ERR_WRONG_AUTH);
        }
        return joinPoint.proceed();
    }
}
