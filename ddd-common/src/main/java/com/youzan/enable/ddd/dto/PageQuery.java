package com.youzan.enable.ddd.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * page query
 * <p/>
 * Created by Danny.Lee on 2017/11/1.
 */
public abstract class PageQuery extends Query {

    @Getter
    @Setter
    private int pageNum;

    @Getter
    @Setter
    private int pageSize;

    @Getter
    @Setter
    private boolean needTotalCount = true;

    @Getter
    @Setter
    private List<OrderDesc> orderDescs;


    public void addOrderDesc(OrderDesc orderDesc) {
        if (null == orderDescs) {
            orderDescs = new ArrayList<>();
        }
        orderDescs.add(orderDesc);
    }

    public int getOffset() {
        return pageNum > 0 ? (pageNum - 1) * pageSize : 0;
    }
}
