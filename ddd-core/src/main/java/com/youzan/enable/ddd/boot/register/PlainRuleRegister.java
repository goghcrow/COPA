package com.youzan.enable.ddd.boot.register;

import com.youzan.enable.ddd.boot.Register;
import com.youzan.enable.ddd.rule.Rule;
import com.youzan.enable.ddd.rule.PlainRuleRepository;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Plain Rule is Rule Component without ExtensionPoint
 * @author fulan.zjf
 * @date 2017/12/21
 */
@Component
public class PlainRuleRegister implements Register, ApplicationContextAware {

    @Resource
    private PlainRuleRepository plainRuleRepository;

    private ApplicationContext applicationContext;

    @Override
    public void doRegistration(Class<?> targetClz) {
        Rule plainRule = (Rule) applicationContext.getBean(targetClz);
        plainRuleRepository.getPlainRules().put(plainRule.getClass(), plainRule);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
