package com.youzan.enable.ddd.exception;

import com.youzan.api.common.enums.IErrorCode;

public class ParamException extends CrmException{
    
    private static final long serialVersionUID = 1L;
    
    public ParamException(String errMessage){
        super(errMessage);
        this.setErrCode(BasicErrorCode.PARAM_ERROR);
    }

    public ParamException(IErrorCode errCode, String errMessage){
        super(errMessage);
        this.setErrCode(errCode);
    }

    public ParamException(String errMessage, Throwable e) {
        super(errMessage, e);
        this.setErrCode(BasicErrorCode.PARAM_ERROR);
    }
}
