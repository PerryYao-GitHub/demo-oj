package com.ypy.pyojbackend.model.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
@AllArgsConstructor
public enum TagEnum {
    GRAMMAR         (0, "grammar"),
    ARRAY           (1, "array"),
    STRING          (2, "string"),
    HASH            (3, "hash"),
    STACK           (4, "stack"),
    QUEUE           (5, "queue"),
    LINKED_LIST     (6, "linked list"),
    BINARY_TREE     (7, "binary tree"),
    BACKTRACKING    (8, "backtracking"),
    GREEDY          (9, "greedy"),
    DP              (10, "dynamic programming"),
    DFS             (11, "depth first search"),
    BFS             (12, "breadth first search"),
    SLIDING_WINDOW  (13, "sliding window");

    @EnumValue
    private final int value;

    private final String text;

    public static final Map<Integer, String> valueTextMap;

    public static final Map<String, Integer> textValueMap;

    static {
        valueTextMap = new HashMap<>();
        textValueMap = new HashMap<>();
        for (TagEnum e : values()) {
            valueTextMap.put(e.getValue(), e.getText());
            textValueMap.put(e.getText(), e.getValue());
        }
    }
}
