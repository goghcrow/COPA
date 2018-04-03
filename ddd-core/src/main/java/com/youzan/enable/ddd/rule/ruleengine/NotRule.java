package com.youzan.enable.ddd.rule.ruleengine;

import com.youzan.enable.ddd.rule.IRule;

/**
 * 组合Not的业务规则
 * @author xueliang.sxl
 *
 */
public class NotRule extends AbstractRule {
	IRule wrapped;

	public NotRule(IRule x){
		wrapped = x;
	}

	@Override
	public boolean check(Object candidate) {
		return !wrapped.check(candidate);
	}

}