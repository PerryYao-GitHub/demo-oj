package com.ypy.pyojbackend.model.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserAuthDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    private Byte role;
}
