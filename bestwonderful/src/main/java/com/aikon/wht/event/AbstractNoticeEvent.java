package com.aikon.wht.event;

import com.aikon.wht.model.EventModel;
import org.springframework.context.ApplicationEvent;

/**
 * @author haitao.wang
 */
public abstract class AbstractNoticeEvent<T extends EventModel> extends ApplicationEvent {

    public AbstractNoticeEvent(T t) {
        super(t);
    }
}
