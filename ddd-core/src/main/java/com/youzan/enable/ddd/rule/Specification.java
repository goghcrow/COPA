package com.youzan.enable.ddd.rule;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * 规约
 *
 * validating an object, querying a collection or specifying how a new object is to be created.
 *
 * @author chuxiaofeng
 *
 * 1. 排除 default 方法, Specification 接口仅包含 isSatisfiedBy 一个方法,
 *    仍旧是 FunctionalInterface, 可以改写为 lambda
 * 2. default 方法的设计目的是为了让已经存在的接口可以演化,
 *    可以方便的对已经实现 Specification2 的接口的类进行功能升级,
 *    扩展 Specification 的组合方式
 * 3. 给 Specification 构造加点甜度, 比如
 *    Specification2<Integer> init = a -> true;
 *    init.and(num -> num % 2 == 0).and(num -> num > 0);
 * 4. @NotNull IDE 分析, Objects.requireNonNull(other); 运行时检查
 */
@FunctionalInterface
public interface Specification<T> {
    
    boolean isSatisfiedBy(@NotNull T candidate);

    default Specification<T> and(@NotNull Specification<? super T> other) {
        Objects.requireNonNull(other);
        return candidate -> isSatisfiedBy(candidate) && other.isSatisfiedBy(candidate);
    }

    default Specification<T> andNot(@NotNull Specification<? super T> other) {
        Objects.requireNonNull(other);
        return candidate -> isSatisfiedBy(candidate) && !other.isSatisfiedBy(candidate);
    }

    default Specification<T> or(@NotNull Specification<? super T> other) {
        Objects.requireNonNull(other);
        return candidate -> isSatisfiedBy(candidate) || other.isSatisfiedBy(candidate);
    }

    default Specification<T> orNot(@NotNull Specification<? super T> other) {
        Objects.requireNonNull(other);
        return candidate -> isSatisfiedBy(candidate) || !other.isSatisfiedBy(candidate);
    }

    default Specification<T> not() {
        return candidate -> !isSatisfiedBy(candidate);
    }
}