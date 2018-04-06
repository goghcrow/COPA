package com.youzan.enable.ddd.boot;

import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

/**
 * @author fulan.zjf
 * @date 2017/12/21
 */
public abstract class ComponentExecutor {

    public <Ret, Com> Ret execute(@NotNull Class<Com> targetClz, @NotNull Function<Com, Ret> exeFunction) {
        Com component = locateComponent(targetClz);
        return exeFunction.apply(component);
    }

    protected abstract <Com> Com locateComponent(@NotNull Class<Com> targetClz);

}
