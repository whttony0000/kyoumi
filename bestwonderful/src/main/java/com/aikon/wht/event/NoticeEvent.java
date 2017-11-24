package com.aikon.wht.event;

import com.aikon.wht.model.EventModel;
import org.springframework.context.ApplicationEvent;

/**
 * @author haitao.wang
 */
public class NoticeEvent<T extends EventModel> extends ApplicationEvent {

    public NoticeEvent(T t) {
        super(t);
    }
}
