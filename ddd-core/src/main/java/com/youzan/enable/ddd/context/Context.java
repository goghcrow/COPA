package com.youzan.enable.ddd.context;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

/**
 * @author chuxiaofeng
 */
public class Context {

    private static ThreadLocal<Map<String, Object>> context = new ThreadLocal<>();

    public static Map<String, Object> get() {
        return context.get();
    }

    @SuppressWarnings("unchecked")
    public static <T> T get(@NotNull String key) {
        if (context.get() == null) {
            return null;
        }
        return (T) context.get().get(key);
    }

    public static void set(@NotNull String key, Object value) {
        if (context.get() == null) {
            context.set(new HashMap<>());
        }
        context.get().put(key, value);
    }

    public static void remove() {
        context.remove();
    }

}

