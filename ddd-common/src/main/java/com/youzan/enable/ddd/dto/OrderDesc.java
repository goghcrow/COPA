package com.youzan.enable.ddd.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * Order Description
 *
 * @author Danny.Lee 2017/12/19
 */
public class OrderDesc {

    @Getter
    @Setter
    private String col;

    @Getter
    @Setter
    private boolean asc = true;

}
