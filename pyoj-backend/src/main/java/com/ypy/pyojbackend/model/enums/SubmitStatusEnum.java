package com.ypy.pyojbackend.model.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
@AllArgsConstructor
public enum SubmitStatusEnum {
    WAITING((byte) 0, "waiting"),
    RUNNING((byte) 1, "running"),
    FAIL((byte) 10, "fail"),
    AC((byte) 11, "ac");

    @EnumValue
    private final byte value;

    private final String text;

    public static final Map<Byte, String> valueTextMap;

    public static final Map<String, Byte> textValueMap;

    static {
        valueTextMap = new HashMap<>();
        textValueMap = new HashMap<>();
        for (SubmitStatusEnum e : values()) {
            valueTextMap.put(e.getValue(), e.getText());
            textValueMap.put(e.getText(), e.getValue());
        }
    }
}
