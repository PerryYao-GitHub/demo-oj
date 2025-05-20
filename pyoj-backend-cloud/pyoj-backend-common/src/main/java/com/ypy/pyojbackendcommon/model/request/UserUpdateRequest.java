package com.ypy.pyojbackendcommon.model.request;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class UserUpdateRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    private List<String> tags;
}
