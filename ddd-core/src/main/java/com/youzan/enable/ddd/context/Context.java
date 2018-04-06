package com.youzan.enable.ddd.context;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

/**
 * @author chuxiaofeng
 */
public class Context {

    /**
     * 不支持 Command 嵌套执行, 否则可修改为
     */
    // ThreadLocal<Stack<Map<String, Object>>>

    private static ThreadLocal<Map<String, Object>> context = ThreadLocal.withInitial(HashMap::new);

    public static Map<String, Object> getAll() {
        return context.get();
    }

    @SuppressWarnings("unchecked")
    public static <T> T get(@NotNull String key) {
        return (T) context.get().get(key);
    }

    public static void set(@NotNull String key, Object value) {
        context.get().put(key, value);
    }

    public static void remove() {
        // context.remove();
        context.get().clear();
    }

}

