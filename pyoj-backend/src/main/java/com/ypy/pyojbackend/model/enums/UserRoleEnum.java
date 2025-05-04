package com.ypy.pyojbackend.model.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
@AllArgsConstructor
public enum UserRoleEnum {
    ADMIN   ((byte) 0, "admin"),
    USER    ((byte) 1, "user"),
    BANNED  ((byte) 2, "banned");

    @EnumValue
    private final byte value;

    private final String text;

    public static final Map<Byte, String> value2text;

    public static final Map<String, Byte> text2value;

    static {
        value2text = new HashMap<>();
        text2value = new HashMap<>();
        for (UserRoleEnum e : UserRoleEnum.values()) {
            value2text.put(e.value, e.text);
            text2value.put(e.text, e.value);
        }
    }
}
