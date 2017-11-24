package com.aikon.wht.intercepter;


import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import java.lang.reflect.Method;

/**
 * Created by haitao.wang on 2016/12/16.
 */
@Slf4j
public class ValidRequestMethodInterceptor implements MethodInterceptor {
    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        Method method = methodInvocation.getMethod();
        log.info(method.getName());
        return methodInvocation.proceed();
    }
}
