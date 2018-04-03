package com.youzan.enable.ddd.rule;

import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Plain Rule is Rule Component without ExtensionPoint
 * @author fulan.zjf
 * @date 2017/12/21
 */
@Component
public class PlainRuleRepository {
    @Getter
    private Map<Class<? extends IRule>, IRule> plainRules = new HashMap<>();
}
