package com.youzan.enable.ddd.extension;

import com.youzan.enable.ddd.boot.ComponentExecutor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
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
    protected <Com> Com locateComponent(@NotNull Class<Com> targetClz) {
        // semantically
        assert targetClz.isInterface();
        Com extension = locateExtension(targetClz);
        log.debug("[Located Extension]: " + extension.getClass().getName());
        return extension;
    }

    protected <Ext> Ext locateExtension(Class<Ext> targetClz) {
        return extensionRepository.getExtensionPoint(targetClz);
    }
}
