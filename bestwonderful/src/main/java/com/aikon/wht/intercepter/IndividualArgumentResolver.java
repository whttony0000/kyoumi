package com.aikon.wht.intercepter;

import com.aikon.wht.annotations.IndividualInfo;
import com.aikon.wht.entity.Individual;
import com.aikon.wht.exception.NoPermissionException;
import com.aikon.wht.utils.ShiroUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * request 参数构造器 构造个人相关request参数.
 *
 * @author haitao.wang
 */
@Slf4j
public class IndividualArgumentResolver implements HandlerMethodArgumentResolver{
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterAnnotation(IndividualInfo.class)!=null;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        Subject subject = ShiroUtils.getSubject();
        if (subject == null) {
            log.info("Shiro Not Enabled Subject Empty");
            throw new NoPermissionException();
        }
        Session session = subject.getSession();
        Individual individual = (Individual) session.getAttribute("individual");
        // 访客.
        if (individual == null) {
            Individual individualGuest = new Individual();
            individualGuest.setId(0);
            individual = individualGuest;
        }
        // 参数类型为Integer则参数赋值为individualId.
        if (parameter.getParameterType().equals(Integer.class)) {
            return individual.getId();
        }

        return individual;
    }
}