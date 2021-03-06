package com.youzan.enable.ddd.command;

import com.youzan.api.common.enums.IErrorCode;
import com.youzan.api.common.response.BaseResult;
import com.youzan.enable.ddd.common.CoreConstant;
import com.youzan.enable.ddd.context.Context;
import com.youzan.enable.ddd.dto.Command;
import com.youzan.enable.ddd.exception.BasicErrorCode;
import com.youzan.enable.ddd.exception.CrmException;
import com.youzan.enable.ddd.exception.InfraException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Just send Command to CommandBus, 
 * 
 * @author fulan.zjf 2017年10月24日 上午12:47:18
 */
@Component
@Slf4j
public class CommandBusImpl implements CommandBus {

    @Resource
    private CommandHub commandHub;

    @SuppressWarnings("unchecked")
    @Override
    public BaseResult send(Command cmd) {
        BaseResult baseResult;

        try {
            Context.set(CoreConstant.CTX_KEY_CMD, cmd);
            baseResult = commandHub.getCommandInvocation(cmd.getClass()).invoke(cmd);
        } catch (Exception exception) {
            baseResult = handleException(cmd, exception);
        } finally {
            //Clean up context
            Context.remove();
        }

        return baseResult;
    }

    private BaseResult handleException(Command cmd, Exception exception) {
        log.error(exception.getMessage(), exception);
        Class responseClz = commandHub.getResponseRepository().get(cmd.getClass());

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
            baseResult.setMessage(exception.getMessage());
        } else {
            // 一些异常, 比如 npe , 没有 message
            String exName = exception.getClass().getSimpleName();
            baseResult.setMessage(exName + ":" + exception.getMessage());
            baseResult.setCode(BasicErrorCode.SYS_ERROR.getCode());
        }

        return baseResult;
    }
}
