package com.aikon.wht.event;

import com.aikon.wht.model.EventModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * @author haitao.wang
 */
@Slf4j
public class NoticeCaller implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

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
