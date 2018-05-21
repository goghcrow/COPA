package com.youzan.enable.ddd.dto.event;

import com.youzan.enable.ddd.dto.DTO;
import lombok.Data;

/**
 * @author shawnzhan.zxy
 * @date 2017/11/20
 */
@Data
public class Event extends DTO {
    private static final long serialVersionUID = 1L;

    protected String eventId;

    protected String eventType;
}
