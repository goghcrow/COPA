package com.youzan.enable.ddd.rule.ruleengine;

import com.youzan.enable.ddd.rule.Rule;

/**
 * 组合And的业务规则
 * @author xueliang.sxl
 *
 */
public class AndRule extends AbstractRule {
	Rule one;
	Rule other;

	public AndRule(Rule x, Rule y){
		one = x;
		other = y;
	}

	@Override
	public boolean check(Object candidate) {
		return one.check(candidate) && other.check(candidate);
	}

}
