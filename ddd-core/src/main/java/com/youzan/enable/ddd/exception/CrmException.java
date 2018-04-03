package com.youzan.enable.ddd.exception;

/**
 * 
 * CRM Exception
 * 
 * @author fulan.zjf 2017年10月22日 上午12:00:39
 */
public abstract class CrmException extends RuntimeException{

    private static final long serialVersionUID = 1L;
    
    private ErrorCode errCode;
    
    public CrmException(String errMessage){
        super(errMessage);
    }
    
    public CrmException(String errMessage, Throwable e) {
        super(errMessage, e);
    }
    
    public ErrorCode getErrCode() {
        return errCode;
    }
    
    public void setErrCode(ErrorCode errCode) {
        this.errCode = errCode;
    }
    
}
