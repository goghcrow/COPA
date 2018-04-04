package com.youzan.enable.ddd.exception;

/**
 *
 * Created by fulan.zjf on 2017/12/18.
 */
public enum BasicErrorCode implements ErrorCode {

    PARAM_ERROR(1000 , "请求参数校验错误" , false),
    BIZ_ERROR(2000 , "业务逻辑错误" , false),
    INFRA_ERROR(3000 , "基础设施(数据库，缓存，消息等)错误" , true),
    SYS_ERROR(4000 , "未知的其它系统错误" , true);

    private int errCode;
    private String errDesc;
    private boolean retriable;

    BasicErrorCode(int errCode, String errDesc, boolean retriable){
        this.errCode = errCode;
        this.errDesc = errDesc;
        this.retriable = retriable;
    }

    @Override
    public int getCode() {
        return errCode;
    }

    @Override
    public String getMessage() {
        return errDesc;
    }

    @Override
    public boolean isRetriable() {
        return retriable;
    }
}
