package com.ypy.pycodesandbox.app;

import lombok.*;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AppResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    private List<String> outputs;

    private String message;

    private Byte status;

    private Long memory; // kb

    private Long time; // ms

    public AppResponse(List<String> outputs, Long time, Long memory) {
        this.outputs = outputs;
        this.time = time;
        this.memory = memory;
        this.status = Status.OK.getValue();
        this.message = "ok";
    }

    public AppResponse(Status status, String message) {
        this.status = status.getValue();
        this.message = message;
    }

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
