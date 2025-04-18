package com.ypy.pyojbackend.codesandbox;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExecuteResponse {

    private List<String> outputs;

    private String message;

    private Byte status;

    private ExecuteInfo executeInfo;
}
