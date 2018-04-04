package com.youzan.enable.ddd.event;

import com.youzan.api.common.enums.IErrorCode;
import com.youzan.api.common.response.BaseResult;
import com.youzan.enable.ddd.dto.event.Event;
import com.youzan.enable.ddd.exception.BasicErrorCode;
import com.youzan.enable.ddd.exception.CrmException;
import com.youzan.enable.ddd.exception.InfraException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;


/**
 * Event Bus
 *
 * @author shawnzhan.zxy
 * @date 2017/11/20
 */
@Component
@Slf4j
public class EventBusImpl implements EventBus {

    @Resource
    private EventHub eventHub;

    @SuppressWarnings("unchecked")
    @Override
    public BaseResult fire(Event event) {
        BaseResult baseResult = null;
        try {
            baseResult = eventHub.getEventHandler(event.getClass()).execute(event);
        }catch (Exception exception) {
            baseResult = handleException(event, exception);
        }
        return baseResult;
    }

    private BaseResult handleException(Event cmd, Exception exception) {
        Class responseClz = eventHub.getResponseRepository().get(cmd.getClass());
        BaseResult baseResult;
        try {
            baseResult = (BaseResult) responseClz.newInstance();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new InfraException(e.getMessage());
        }
        baseResult.setSuccess(false);
        if (exception instanceof CrmException) {
            IErrorCode errCode = ((CrmException) exception).getErrCode();
            baseResult.setCode(errCode.getCode());
        }
        else {
            baseResult.setCode(BasicErrorCode.SYS_ERROR.getCode());
        }
        baseResult.setMessage(exception.getMessage());
        log.error(exception.getMessage(), exception);
        return baseResult;
    }
}
