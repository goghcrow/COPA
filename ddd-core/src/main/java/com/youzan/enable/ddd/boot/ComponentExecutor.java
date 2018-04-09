package com.youzan.enable.ddd.boot;

import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

/**
 * @author fulan.zjf
 * @date 2017/12/21
 */
public abstract class ComponentExecutor {

    /**
     * 执行自定义 组件(e.g. extensionPoint) 声明方法
     */
    public <Ret, Com> Ret execute(@NotNull Class<Com> targetClz, @NotNull Function<Com, Ret> exeFunction) {
        Com component = locateComponent(targetClz);
        return exeFunction.apply(component);
    }

    protected abstract <Com> Com locateComponent(@NotNull Class<Com> targetClz);

}
