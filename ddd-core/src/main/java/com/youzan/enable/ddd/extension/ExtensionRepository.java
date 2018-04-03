package com.youzan.enable.ddd.extension;

import com.youzan.enable.ddd.exception.InfraException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * ExtensionRepository 
 * @author fulan.zjf 2017-11-05
 */
@Component
public class ExtensionRepository {

    @Getter
    private Map<String, List<ExtPtEntry>> extensionRepo = new HashMap<>();


    @Data
    @AllArgsConstructor
    @EqualsAndHashCode
    public static class ExtPtEntry implements Comparable<ExtPtEntry> {
        private Class<?> targetClz;
        private IExtensionPoint extensionPoint;
        private int order;

        @Override
        public int compareTo(@NotNull ExtensionRepository.ExtPtEntry other) {
            return Integer.compare(order, other.order);
        }
    }

    public boolean addExtensionPoint(@NotNull String extPtName, @NotNull ExtensionRepository.ExtPtEntry extPtrEntry) {
        extensionRepo.computeIfAbsent(extPtName, k -> new ArrayList<>());
        List<ExtPtEntry> lst = extensionRepo.get(extPtName);
        boolean newAdded = lst.add(extPtrEntry);
        Collections.sort(lst);
        return newAdded;
    }

    @SuppressWarnings("unchecked")
    public <Ext> Ext getExtensionPoint(@NotNull String extPtName) {
        List<ExtensionRepository.ExtPtEntry> extPts = extensionRepo.get(extPtName);
        if (extPts == null) {
            throw new InfraException("Can not find extension for ExtensionPoint: " + extPtName);
        }

        for (ExtensionRepository.ExtPtEntry extPtr : extPts) {
            if (extPtr.getExtensionPoint().match()) {
                return (Ext) extPtr.getExtensionPoint();
            }
        }
        throw new InfraException("Can not find extension for ExtensionPoint: " + extPtName);
    }

}
