package com.youzan.enable.ddd.validator;

import org.hibernate.validator.messageinterpolation.ResourceBundleMessageInterpolator;

import java.util.Locale;

/**
 * @author fulan.zjf
 * @date 2017/12/25
 */
public class MessageInterpolator extends ResourceBundleMessageInterpolator{

    @Override
    public String interpolate(String message, Context context) {
        String resolvedMessage = super.interpolate(message, context, Locale.SIMPLIFIED_CHINESE);
        return resolvedMessage;
    }
}
