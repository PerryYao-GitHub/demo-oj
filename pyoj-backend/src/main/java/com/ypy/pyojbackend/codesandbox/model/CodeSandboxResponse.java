package com.ypy.pyojbackend.codesandbox.model;

import lombok.*;

import java.util.List;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CodeSandboxResponse {

    private List<String> outputs;

    private String message;

    private Byte status;

    private Long memory; // kb

    private Long time; // ms

    @AllArgsConstructor
    @Getter
    public enum Status {
        OK                  ((byte) 0),
        ERR_COMPILE         ((byte) 1),
        ERR_RUNTIME         ((byte) 2),
        ERR_REQUEST         ((byte) 3),
        ERR_OTHER           ((byte) 4);

        private final Byte value;
    }
}
