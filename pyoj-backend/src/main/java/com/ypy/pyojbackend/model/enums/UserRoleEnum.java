package com.ypy.pyojbackend.model.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserRoleEnum {
    ADMIN((byte) 0, "admin"), USER((byte) 1, "user"), BANNED((byte) 2, "banned");

    @EnumValue
    private final byte value;

    private final String text;

    public static UserRoleEnum fromValue(byte value) {
        for (UserRoleEnum e : values()) {
            if (e.getValue() == value) return e;
        }
        throw new IllegalArgumentException("Invalid role value: " + value);
    }
}
