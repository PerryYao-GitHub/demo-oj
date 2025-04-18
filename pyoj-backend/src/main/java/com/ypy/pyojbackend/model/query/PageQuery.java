package com.ypy.pyojbackend.model.query;

import lombok.Data;

/**
 * base class, for page query
 */
@Data
public class PageQuery {
    private int pageNum;

    private int pageSize;

    private String orderBy;

    private OrderType orderType;

    public enum OrderType {
        ASC, DESC;
    }
}
