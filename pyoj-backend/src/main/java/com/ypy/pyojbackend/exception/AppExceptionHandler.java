package com.ypy.pyojbackend.exception;

import com.ypy.pyojbackend.app.AppResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class AppExceptionHandler {

    @ExceptionHandler(AppException.class)
    public AppResponse<?> handle(AppException e) {
        log.error(e.getMessage(), e);
        return new AppResponse<>(e.getCode(), e.getMessage(), null);
    }
}
