package com.ypy.pyojbackend.model.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum LangEnum {
    JAVA((byte) 0, "java");

    @EnumValue
    private final byte value;

    private final String text;

    public static LangEnum fromValue(byte value) {
        for (LangEnum e : values()) {
            if (e.value == value) return e;
        }
        throw new IllegalArgumentException("Invalid lang value: " + value);
    }
}
