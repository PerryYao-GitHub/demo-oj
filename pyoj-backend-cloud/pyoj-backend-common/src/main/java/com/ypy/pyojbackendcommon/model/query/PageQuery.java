package com.ypy.pyojbackendcommon.model.query;

import lombok.Data;

@Data
public class PageQuery {

    private int pageNum = 1;

    private int pageSize = 5;

    private boolean desc = false;

    private String orderBy = "create_time";
}
