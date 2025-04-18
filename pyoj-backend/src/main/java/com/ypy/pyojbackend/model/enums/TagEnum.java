package com.ypy.pyojbackend.model.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TagEnum {
    GRAMMAR         (0, "grammar"),
    ARRAY           (1, "array"),
    STRING          (2, "string"),
    LINKED_LIST     (3, "linked list"),
    BINARY_TREE     (4, "binary tree"),
    BACKTRACKING    (5, "backtracking"),
    GREEDY          (6, "greedy"),
    DP              (7, "dynamic programming"),
    DFS             (8, "depth-first search"),
    BFS             (9, "breadth-first search"),
    SLIDING_WINDOW  (10, "sliding window");

    @EnumValue
    private final int value;

    private final String text;

    public static TagEnum fromValue(int value) {
        for (TagEnum e : values()) {
            if (e.getValue() == value) return e;
        }
        throw new IllegalArgumentException("Invalid tag value: " + value);
    }
}
