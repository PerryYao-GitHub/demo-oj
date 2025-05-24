package com.ypy.pyojbackendcommon.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class UserAuthDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    private Byte role;
}
