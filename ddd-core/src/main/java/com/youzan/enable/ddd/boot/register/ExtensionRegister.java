package com.youzan.enable.ddd.boot.register;

import com.youzan.enable.ddd.boot.Register;
import com.youzan.enable.ddd.common.CoreConstant;
import com.youzan.enable.ddd.exception.InfraException;
import com.youzan.enable.ddd.annotation.Extension;
import com.youzan.enable.ddd.extension.ExtensionRepository;
import com.youzan.enable.ddd.extension.ExtensionPoint;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * ExtensionRegister 
 * @author fulan.zjf 2017-11-05
 */
@Component
public class ExtensionRegister implements Register, ApplicationContextAware {

    @Resource
    private ExtensionRepository extensionRepository;
    
    private ApplicationContext applicationContext;
    
    @Override
    public void doRegistration(Class<?> targetClz) {
        ExtensionPoint extension = (ExtensionPoint) applicationContext.getBean(targetClz);
        boolean newAdded = extensionRepository.addExtensionPoint(targetClz, extension);
        if (!newAdded) {
            throw new InfraException("Duplicate registration is not allowed for :" + targetClz.getName());
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}