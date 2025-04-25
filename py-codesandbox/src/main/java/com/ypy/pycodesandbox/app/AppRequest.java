package com.ypy.pycodesandbox.app;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AppRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    private List<String> inputs;

    private String code;

    private Byte lang;
}
