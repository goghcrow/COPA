package com.youzan.enable.ddd.rule.ruleengine;

import com.youzan.enable.ddd.rule.Rule;

/**
 * 组合Not的业务规则
 * @author xueliang.sxl
 *
 */
public class NotRule extends AbstractRule {
	Rule wrapped;

	public NotRule(Rule x){
		wrapped = x;
	}

	@Override
	public boolean check(Object candidate) {
		return !wrapped.check(candidate);
	}

}