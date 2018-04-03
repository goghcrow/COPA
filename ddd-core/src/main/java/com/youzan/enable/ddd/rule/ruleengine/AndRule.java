package com.youzan.enable.ddd.rule.ruleengine;

import com.youzan.enable.ddd.rule.IRule;

/**
 * 组合And的业务规则
 * @author xueliang.sxl
 *
 */
public class AndRule extends AbstractRule {
	IRule one;
	IRule other;

	public AndRule(IRule x, IRule y){
		one = x;
		other = y;
	}

	@Override
	public boolean check(Object candidate) {
		return one.check(candidate) && other.check(candidate);
	}

}
