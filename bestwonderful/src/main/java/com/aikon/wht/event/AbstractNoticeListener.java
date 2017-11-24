package com.aikon.wht.event;

/**
 * @author haitao.wang
 */
public abstract class AbstractNoticeListener<T extends AbstractNoticeEvent>  {

    /**
     * 事件流程.
     *
     * @param event
     */
    public void flow(T event) {
        before(event);
        execute(event);
    }

    /**
     * 事件处理.
     *
     * @param event
     */
    protected abstract void execute(T event);

    /**
     * 前置处理.
     *
     * @param event
     */
    public void before(T event){
        if (event == null) {
            return;
        }
        if (event.getSource() == null) {
            return;
        }
    }


}
