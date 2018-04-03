package com.youzan.enable.ddd.exception;

/**
 * Extends your error codes in your App by implements this Interface.
 *
 * Created by fulan.zjf on 2017/12/18.
 */
public interface IErrorCode {

    int getErrCode();

    String getErrDesc();

    boolean isRetriable();
}
