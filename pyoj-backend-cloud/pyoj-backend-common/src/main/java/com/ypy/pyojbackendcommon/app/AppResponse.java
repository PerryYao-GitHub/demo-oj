package com.ypy.pyojbackendcommon.app;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class AppResponse<T> implements Serializable {

    private int code;

    private String message;

    private T data;

    public AppResponse(AppCode code, T data) {
        this.code = code.getCode();
        this.message = code.getMessage();
        this.data = data;
    }
}
