package com.ypy.pyojbackendcommon.app;

import lombok.Getter;

@Getter
public enum AppCode {
    OK(0, "ok"),
    ERR_PARAMETER           (40000, "err: parameter"),

    ERR_WRONG_AUTH          (40100, "err: wrong authentication"),
    ERR_NOT_LOGIN           (40101, "err: not login"),
    ERR_INVALID_USR_PWD     (40102, "err: invalid username or password"),
    ERR_WRONG_USR_PWD       (40103, "err: wrong username or password"),
    ERR_SAME_USERNAME       (40102, "err: same username"),

    ERR_FORBIDDEN           (40300, "err: forbidden"),
    ERR_TOKEN               (40301, "err: token"),
    ERR_TOKEN_EXPIRE        (40302, "err: token expired"),

    ERR_NOT_FOUND           (40400, "err: not found"),

    ERR_SYSTEM              (50000, "err: system"),
    ERR_API_REQUEST         (50001, "err: api request");

    private final int code;

    private final String message;

    AppCode(int code, String message) { this.code = code; this.message = message; }
}
