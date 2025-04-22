package com.ypy.pycodesandbox.model;

import lombok.*;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AppResponse {

    private List<String> outputs;

    private String message;

    private Byte status;

    private Long memory; // kb

    private Long time; // ms

    @AllArgsConstructor
    @Getter
    public enum Status {
        OK((byte) 0),
        ERR_COMPILE((byte) 1),
        ERR_RUNTIME((byte) 2),
        ERR_OTHER((byte) 3);

        private final Byte value;
    }
}
