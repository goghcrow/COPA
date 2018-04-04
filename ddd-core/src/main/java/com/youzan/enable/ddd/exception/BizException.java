package com.youzan.enable.ddd.exception;

import com.youzan.api.common.enums.IErrorCode;

public class BizException extends CrmException{

    private static final long serialVersionUID = 1L;

    public BizException(String errMessage){
        super(errMessage);
        this.setErrCode(BasicErrorCode.BIZ_ERROR);
    }

    public BizException(IErrorCode errCode, String errMessage){
    	super(errMessage);
    	this.setErrCode(errCode);
    }

    public BizException(String errMessage, Throwable e) {
        super(errMessage, e);
        this.setErrCode(BasicErrorCode.BIZ_ERROR);
    }
}