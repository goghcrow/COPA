package com.youzan.enable.ddd.rule;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * 业务规则接口，适合领域层的业务规则检查
 *
 * @author chuxiaofeng
 */
@FunctionalInterface
public interface Rule {

	/**
	 * 默认的规则检查接口，子接口可自定义
	 * 如果子类不需要使用 check, 且不需要规则组合
	 * 则使用默认方法覆盖即可
	 * default boolean check(Object candidate) { return true; }
	 * @param candidate
	 * @return
	 */
	boolean check(Object candidate);

	/**
	 * This is specific for Validation cases where no Return is needed
	 * @param candidate
	 */
	default void validate(Object... candidate){}

	default Rule and(@NotNull Rule other) {
		Objects.requireNonNull(other);
		return candidate -> check(candidate) && other.check(candidate);
	}

	default Rule andNot(@NotNull Rule other) {
		Objects.requireNonNull(other);
		return candidate -> check(candidate) && !other.check(candidate);
	}

	default Rule or(@NotNull Rule other) {
		Objects.requireNonNull(other);
		return candidate -> check(candidate) || other.check(candidate);
	}

	default Rule orNot(@NotNull Rule other) {
		Objects.requireNonNull(other);
		return candidate -> check(candidate) || !other.check(candidate);
	}

	default Rule not() {
		return candidate -> !check(candidate);
	}
}
