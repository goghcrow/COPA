package com.youzan.enable.ddd.event;

import com.youzan.api.common.response.BaseResult;
import com.youzan.enable.ddd.dto.event.Event;

/**
 * event handler
 *
 * @author shawnzhan.zxy
 * @date 2017/11/20
 */
public interface EventHandler<Ret extends BaseResult, Evt extends Event> {

    Ret execute(Evt e);

}
