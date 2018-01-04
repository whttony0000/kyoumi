package com.aikon.wht.event;

import com.aikon.wht.model.EventModel;
import org.springframework.context.ApplicationEvent;

/**
 * 系统内事件抽象.
 *
 * @author haitao.wang
 */
public abstract class AbstractNoticeEvent<T extends EventModel> extends ApplicationEvent {

    /**
     * 构造器.
     *
     * @param t
     */
    public AbstractNoticeEvent(T t) {
        super(t);
    }
}
