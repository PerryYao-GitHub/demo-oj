package com.ypy.pyojbackendcommon.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class PageVO<T> {

    private int total;

    private int pageNum;

    private int pageSize;

    private List<T> content;
}
