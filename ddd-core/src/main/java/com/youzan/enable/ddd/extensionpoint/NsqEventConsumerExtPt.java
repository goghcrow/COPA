package com.youzan.enable.ddd.extensionpoint;

import com.youzan.api.common.response.BaseResult;

/**
 * Nsq消息消费扩展点
 * @author chuxiaofeng
 */
public interface NsqEventConsumerExtPt {

	BaseResult receive(String msg);
}
