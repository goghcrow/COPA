package com.youzan.enable.ddd.exception;

/**
 * fixme common-base 错误码
 * 参数错误：1000 - 1999
 * 业务错误：2000 - 2999
 * 基础错误：3000 - 3999
 * 系统错误：4000 - 4999
 *
 * Created by fulan.zjf on 2017/12/18.
 */
public enum BasicErrorCode implements IErrorCode {
    PARAM_ERROR(1000 , "请求参数校验错误" , false),
    BIZ_ERROR(2000 , "业务逻辑错误" , false),
    INFRA_ERROR(3000 , "基础设施(数据库，缓存，消息等)错误" , true),
    SYS_ERROR(4000 , "未知的其它系统错误" , true);

    private int errCode;
    private String errDesc;
    private boolean retriable;

    private BasicErrorCode(int errCode, String errDesc, boolean retriable){
        this.errCode = errCode;
        this.errDesc = errDesc;
        this.retriable = retriable;
    }

    @Override
    public int getErrCode() {
        return errCode;
    }

    @Override
    public String getErrDesc() {
        return errDesc;
    }

    @Override
    public boolean isRetriable() {
        return retriable;
    }
}
