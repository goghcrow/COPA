package com.youzan.enable.ddd.extension;

import com.youzan.enable.ddd.annotation.Extension;
import com.youzan.enable.ddd.common.CoreConstant;
import com.youzan.enable.ddd.exception.InfraException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.apache.commons.lang3.ArrayUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * ExtensionRepository 
 * @author fulan.zjf 2017-11-05
 */
@Component
public class ExtensionRepository {

    /**
     * List<ExtPtEntry> 优先级从高到底排列
     */
    @Getter
    private Map<String, List<ExtPtEntry>> extensionRepo = new HashMap<>();


    @Data
    @AllArgsConstructor
    @EqualsAndHashCode
    private static class ExtPtEntry implements Comparable<ExtPtEntry> {
        private Class<?> targetClz;
        private ExtensionPoint extensionPoint;
        private int order;

        @Override
        public int compareTo(@NotNull ExtPtEntry other) {
            return -Integer.compare(order, other.order);
        }
    }

    /**
     *
     * @param extClz 扩展点接口{@link ExtensionPoint}派生类的实现类 class
     * @param extension extClz的实例
     * @return
     */
    public boolean addExtensionPoint(@NotNull Class<?> extClz, @NotNull ExtensionPoint extension) {
        Extension extensionAnn = extClz.getDeclaredAnnotation(Extension.class);
        Objects.requireNonNull(extensionAnn);

        Class<?> extPtClz = getExtPtFromExt(extClz);
        String extPtName = calcExtNameFromExtPt(extPtClz);

        ExtPtEntry extPtEntry = new ExtPtEntry(extClz, extension, extensionAnn.order());

        return addExtensionPoint(extPtName, extPtEntry);
    }

    /**
     *
     * @param extPtClz 扩展点接口, 必须继承{@link ExtensionPoint}
     * @return
     */
    @SuppressWarnings("unchecked")
    public <Ext> Ext getExtensionPoint(Class<Ext> extPtClz) {
        assert extPtClz.isInterface();
        String extPtName =  calcExtNameFromExtPt(extPtClz);
        return getExtensionPoint(extPtName);
    }

    private boolean addExtensionPoint(@NotNull String extPtName, @NotNull ExtPtEntry extPtEntry) {
        extensionRepo.computeIfAbsent(extPtName, k -> new ArrayList<>());
        List<ExtPtEntry> lst = extensionRepo.get(extPtName);
        boolean newAdded = lst.add(extPtEntry);
        Collections.sort(lst);
        return newAdded;
    }

    @SuppressWarnings("unchecked")
    private <Ext> Ext getExtensionPoint(@NotNull String extPtName) {
        List<ExtPtEntry> extPts = extensionRepo.get(extPtName);
        if (extPts == null) {
            throw new InfraException("Can not find extension for ExtensionPoint: " + extPtName);
        }

        for (ExtPtEntry extPt : extPts) {
            if (extPt.getExtensionPoint().match()) {
                return (Ext) extPt.getExtensionPoint();
            }
        }

        if (extPts.size() > 0) {
            throw new InfraException("We have " + extPts.size() + " extensions for extensionPoint: " + extPtName
                    + ", but can not match any");
        } else {
            throw new InfraException("Can not find any extension for ExtensionPoint: " + extPtName);
        }
    }

    private Class<?> getExtPtFromExt(Class<?> targetClz) {
        Class[] interfaces = targetClz.getInterfaces();
        if (ArrayUtils.isEmpty(interfaces)) {
            throw new InfraException("Please assign a extension point interface for " + targetClz);
        }
        for (Class intf : interfaces) {
            // 注意这里逻辑, 寻找扩展点接口名称
            String extensionPoint = intf.getSimpleName();
            if (extensionPoint.endsWith(CoreConstant.EXTENSION_EXTPT_NAMING)) {
                return intf;
            }
        }

        throw new InfraException("Your name of ExtensionPoint for " + targetClz +
                " is not valid, must be end of " + CoreConstant.EXTENSION_EXTPT_NAMING);
    }

    private String calcExtNameFromExtPt(Class<?> extPtClz) {
        return extPtClz.getName();
    }
}
