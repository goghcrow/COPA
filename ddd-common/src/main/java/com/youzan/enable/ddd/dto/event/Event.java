package com.youzan.enable.ddd.dto.event;

import com.youzan.enable.ddd.dto.DTO;
import lombok.Getter;
import lombok.Setter;

/**
 * @author shawnzhan.zxy
 * @date 2017/11/20
 */
public class Event extends DTO {
    private static final long serialVersionUID = 1L;

    @Getter
    @Setter
    protected String eventId;

    @Getter
    @Setter
    protected String eventType;
}
