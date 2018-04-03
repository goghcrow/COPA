package com.youzan.enable.ddd.rule;

import com.youzan.enable.ddd.extension.ExtensionExecutor;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * RuleExecutor
 *
 * Note that Rule is extensible as long as @Extension is added
 *
 * @author fulan.zjf 2017-11-04
 */
@Component
public class RuleExecutor extends ExtensionExecutor {

    @Resource
    private PlainRuleRepository plainRuleRepository;

    @Override
    protected <C> C locateComponent(Class<C> targetClz) {
        C rule = (C) plainRuleRepository.getPlainRules().get(targetClz);
        return null != rule ? rule : super.locateComponent(targetClz);
    }

    public void validate(Class<? extends IRule> targetClz, Object... candidate) {
        IRule rule = this.locateComponent(targetClz);
        rule.validate(candidate);
    }
}
