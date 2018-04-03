package com.youzan.enable.ddd.event;

import com.youzan.api.common.response.BaseResult;
import com.youzan.enable.ddd.dto.event.Event;


/**
 * EventBus interface
 * @author shawnzhan.zxy
 * @date 2017/11/20
 */
public interface IEventBus {

    /**
     * Send event to EventBus
     * 
     * @param event
     * @return Response
     */
    BaseResult fire(Event event);
}
