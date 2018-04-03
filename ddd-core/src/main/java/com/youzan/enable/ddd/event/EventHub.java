package com.youzan.enable.ddd.event;

import com.youzan.enable.ddd.dto.event.Event;
import com.youzan.enable.ddd.exception.InfraException;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 事件控制中枢
 * @author shawnzhan.zxy
 * @date 2017/11/20
 */
@SuppressWarnings("rawtypes")
@Component
public class EventHub {
    @Getter
    @Setter
    private Map<Class, IEventHandler> eventRepository = new HashMap<>();
    
    @Getter
    private Map<Class, Class> responseRepository = new HashMap<>();
    
    public IEventHandler getEventHandler(Class eventClass) {
        IEventHandler eventHandlerI = findHandler(eventClass);
        if (eventHandlerI == null) {
            throw new InfraException(eventClass + "is not registered in eventHub, please register first");
        }
        return eventHandlerI;
    }

    /**
     * 注册事件
     * @param eventClz
     * @param executor
     */
    public void register(Class<? extends Event> eventClz, IEventHandler executor){
        eventRepository.put(eventClz, executor);
    }

    private IEventHandler findHandler(Class<? extends Event> eventClass){
        return eventRepository.get(eventClass);
    }

}
