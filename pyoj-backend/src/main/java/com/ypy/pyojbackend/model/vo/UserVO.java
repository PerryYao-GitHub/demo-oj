package com.ypy.pyojbackend.model.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * user detail info
 */
@Data
public class UserVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    private String username;

    private String role;

    private List<String> tags;
}
