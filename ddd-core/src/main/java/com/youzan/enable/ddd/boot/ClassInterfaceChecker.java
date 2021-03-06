package com.youzan.enable.ddd.boot;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Modifier;

/**
 * @author fulan.zjf
 * @date 2017/12/21
 */
class ClassInterfaceChecker {

    static boolean check(Class<?> targetClz, String expectedName) {
        // 排除掉 abstract class
        if (Modifier.isAbstract(targetClz.getModifiers())) {
            return false;
        }

        //If it is not Class, just return false
        if(targetClz.isInterface()){
            return false;
        }

        Class[] interfaces = targetClz.getInterfaces();
        if (ArrayUtils.isEmpty(interfaces)) {
            return false;
        }
        for (Class intf : interfaces) {
            String intfSimpleName = intf.getSimpleName();
            if (StringUtils.equals(intfSimpleName, expectedName)) {
                return true;
            }
        }
        return false;
    }
}
