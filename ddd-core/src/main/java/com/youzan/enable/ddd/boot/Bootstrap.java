package com.youzan.enable.ddd.boot;

import com.youzan.enable.ddd.exception.InfraException;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * <B>应用的核心引导启动类</B>
 * <p>
 * 负责扫描在applicationContext.xml中配置的packages.
 * 获取到CommandExecutors, intercepters, extensions, validators等
 * 交给各个注册器进行注册。
 *
 * @author fulan.zjf 2017-11-04
 */
public class Bootstrap {
    @Getter
    @Setter
    private List<String> packages;

    @Resource
    private RegisterFactory registerFactory;


    public void init() {
        Set<Class<?>> classSet = scanConfiguredPackages();
        registerBeans(classSet);
    }

    /**
     * @param classSet
     */
    private void registerBeans(Set<Class<?>> classSet) {
        for (Class<?> targetClz : classSet) {
            Register register = registerFactory.getRegister(targetClz);
            if (register != null) {
                register.doRegistration(targetClz);
            }
        }

    }

    /**
     * Scan the packages configured in Spring xml
     *
     * @return
     */
    private Set<Class<?>> scanConfiguredPackages() {
        if (packages == null) {
            throw new InfraException("Command packages is not specified");
        }

        String[] pkgs = new String[packages.size()];
        ClassPathScanHandler handler = new ClassPathScanHandler(packages.toArray(pkgs));

        Set<Class<?>> classSet = new TreeSet<>(new ClassNameComparator());
        for (String pakName : packages) {
            classSet.addAll(handler.getPackageAllClasses(pakName, true));
        }
        return classSet;
    }
}
