package com.ypy.pyojbackend.model.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
@AllArgsConstructor
public enum LangEnum {
    JAVA((byte) 0, "java");

    @EnumValue
    private final byte value;

    private final String text;

    public static final Map<Byte, String> valueTextMap;

    public static final Map<String, Byte> textValueMap;

    static {
        valueTextMap = new HashMap<>();
        textValueMap = new HashMap<>();
        for (LangEnum e : values()) {
            valueTextMap.put(e.getValue(), e.getText());
            textValueMap.put(e.getText(), e.getValue());
        }
    }
}
