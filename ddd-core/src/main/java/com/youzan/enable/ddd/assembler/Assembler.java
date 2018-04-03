package com.youzan.enable.ddd.assembler;


/**
 * 适用于消息，查询，Dubbo等接口的参数装配
 * Assembler Interface
 *
 * @author fulan.zjf 2017-11-07
 */
public interface Assembler<F, T> {

    default T assemble(F from) {
        return null;
    }

    default void assemble(F from, T to) { }

}
