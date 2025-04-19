package com.ypy.pyojbackend.common;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserRoleEnum {
    ADMIN((byte) 0), USER((byte) 1), BANNED((byte) 2);

    @EnumValue
    private final byte value;

    public static UserRoleEnum fromValue(byte value) {
        for (UserRoleEnum e : values()) {
            if (e.getValue() == value) return e;
        }
        throw new IllegalArgumentException("Invalid role value: " + value);
    }
}
