package com.youzan.enable.ddd.boot.register;

import com.youzan.enable.ddd.boot.IRegister;
import com.youzan.enable.ddd.common.CoreConstant;
import com.youzan.enable.ddd.exception.InfraException;
import com.youzan.enable.ddd.extension.Extension;
import com.youzan.enable.ddd.extension.ExtensionRepository;
import com.youzan.enable.ddd.extension.IExtensionPoint;
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
public class ExtensionRegister implements IRegister, ApplicationContextAware {

    @Resource
    private ExtensionRepository extensionRepository;
    
    private ApplicationContext applicationContext;
    
    @Override
    public void doRegistration(Class<?> targetClz) {
        IExtensionPoint extension = (IExtensionPoint) applicationContext.getBean(targetClz);
        Extension extensionAnn = targetClz.getDeclaredAnnotation(Extension.class);

        String extensionPoint = calculateExtensionPoint(targetClz);

        ExtensionRepository.ExtPtEntry extPtrEntry =
                new ExtensionRepository.ExtPtEntry(targetClz, extension, extensionAnn.order());
        boolean newAdded = extensionRepository.addExtensionPoint(extensionPoint, extPtrEntry);
        if (!newAdded) {
            throw new InfraException("Duplicate registration is not allowed for :" + extensionPoint);
        }
    }


    /**
     * 注意这里逻辑, 寻找扩展点接口名称
     * @param targetClz
     * @return
     */
    private String calculateExtensionPoint(Class<?> targetClz) {
        Class[] interfaces = targetClz.getInterfaces();
        if (ArrayUtils.isEmpty(interfaces)) {
            throw new InfraException("Please assign a extension point interface for " + targetClz);
        }
        for (Class intf : interfaces) {
            String extensionPoint = intf.getSimpleName();
            if (StringUtils.contains(extensionPoint, CoreConstant.EXTENSION_EXTPT_NAMING)) {
                return extensionPoint;
            }
        }

        throw new InfraException("Your name of ExtensionPoint for " + targetClz +
                " is not valid, must be end of " + CoreConstant.EXTENSION_EXTPT_NAMING);
    }



    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}