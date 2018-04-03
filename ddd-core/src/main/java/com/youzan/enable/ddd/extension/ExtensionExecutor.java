package com.youzan.enable.ddd.extension;

import com.youzan.enable.ddd.boot.ComponentExecutor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * ExtensionExecutor 
 * @author fulan.zjf 2017-11-05
 */
@Component
@Slf4j
public class ExtensionExecutor extends ComponentExecutor {

    @Resource
    private ExtensionRepository extensionRepository;

    @Override
    protected <C> C locateComponent(Class<C> targetClz) {
        C extension = locateExtension(targetClz);
        log.debug("[Located Extension]: "+extension.getClass().getSimpleName());
        return extension;
    }

    /**
     * @param targetClz
     */
    protected <Ext> Ext locateExtension(Class<Ext> targetClz) {
        String extPtName = targetClz.getSimpleName();
        return extensionRepository.getExtensionPoint(extPtName);
    }
}
