package com.ypy.pyojbackendcommon.exception;

import com.ypy.pyojbackendcommon.app.AppCode;
import lombok.Getter;

@Getter
public class AppException extends Exception {

    private final int code;

    public AppException(int code, String message) {
        super(message);
        this.code = code;
    }

    public AppException(AppCode appCode) {
        super(appCode.getMessage());
        this.code = appCode.getCode();
    }

    public AppException(AppCode appCode, String message) {
        super(message);
        this.code = appCode.getCode();
    }
}
