package com.youzan.enable.ddd.extensionpoint;

import com.youzan.api.common.response.BaseResult;

/**
 * Nsq消息消费扩展点
 * @author xueliang.sxl
 *
 */
public interface NsqEventConsumerExtPt {

	BaseResult receive(String msg);
}
