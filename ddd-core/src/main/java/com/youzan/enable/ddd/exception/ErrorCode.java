package com.youzan.enable.ddd.exception;

import com.youzan.api.common.enums.IErrorCode;

/**
 * Extends your error codes in your App by implements this Interface.
 *
 * Created by fulan.zjf on 2017/12/18.
 */
public interface ErrorCode extends IErrorCode {

    default boolean isRetriable() { return false; }

}
