package com.ypy.pyojbackendcommon.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@Getter
public enum LangEnum {
    JAVA((byte) 0, "java");

    private final byte value;

    private final String text;

    public static final Map<Byte, String> value2text;

    public static final Map<String, Byte> text2value;

    static {
        value2text = new HashMap<>();
        text2value = new HashMap<>();
        for (LangEnum e : values()) {
            value2text.put(e.getValue(), e.getText());
            text2value.put(e.getText(), e.getValue());
        }
    }
}
