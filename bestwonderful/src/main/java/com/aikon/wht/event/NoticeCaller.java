package com.aikon.wht.event;

import com.aikon.wht.model.EventModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * 事件发布器.
 *
 * @author haitao.wang
 */
@Slf4j
public class NoticeCaller implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    /**
     * 初始化时获取上下文.
     *
     * @param applicationContext
     * @throws BeansException
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    /**
     * 根据事件类型发布事件.
     *
     * @param eventModel
     * @param clazz
     */
    public void publishEvent(EventModel eventModel,Class<? extends AbstractNoticeEvent> clazz) {
        Constructor constructor;
        try {
            constructor = clazz.getConstructor(eventModel.getClass());
        } catch (NoSuchMethodException e) {
            log.info("Error Publish Event {}",e);
            return;
        }
        AbstractNoticeEvent event;
        try {
            event = (AbstractNoticeEvent) constructor.newInstance(eventModel);
        } catch (InstantiationException e) {
            log.info("Error Publish Event {}",e);
            return;
        } catch (IllegalAccessException e) {
            log.info("Error Publish Event {}",e);
            return;
        } catch (InvocationTargetException e) {
            log.info("Error Publish Event {}",e);
            return;
        }
        applicationContext.publishEvent(event);
    }

}
