package com.youzan.enable.ddd.rule.ruleengine;

import com.youzan.enable.ddd.rule.Rule;

/**
 * 业务规则的抽象实现，可用于组合规则
 * @author xueliang.sxl
 *
 */
public abstract class AbstractRule implements Rule {

	public Rule and(Rule other) {
		return new AndRule(this, other);
	}

	public Rule or(Rule other) {
		return new OrRule(this, other);
	}

	public Rule not() {
		return new NotRule(this);
	}

}
