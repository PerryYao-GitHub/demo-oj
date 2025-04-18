package com.ypy.pyojbackend.model.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SubmitStatusEnum {
    WAITING((byte) 0, "waiting"),
    FAIL((byte) 1, "fail"),
    AC((byte) 2, "ac");

    @EnumValue
    private final byte value;

    private final String text;

    public static SubmitStatusEnum fromValue(byte value) {
        for (SubmitStatusEnum e : values()) {
            if (e.value == value) return e;
        }
        throw new IllegalArgumentException("Invalid status value: " + value);
    }
}
