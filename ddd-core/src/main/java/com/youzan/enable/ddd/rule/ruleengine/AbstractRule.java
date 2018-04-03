package com.youzan.enable.ddd.rule.ruleengine;

import com.youzan.enable.ddd.rule.IRule;

/**
 * 业务规则的抽象实现，可用于组合规则
 * @author xueliang.sxl
 *
 */
public abstract class AbstractRule implements IRule {

	public IRule and(IRule other) {
		return new AndRule(this, other);
	}

	public IRule or(IRule other) {
		return new OrRule(this, other);
	}

	public IRule not() {
		return new NotRule(this);
	}

}
