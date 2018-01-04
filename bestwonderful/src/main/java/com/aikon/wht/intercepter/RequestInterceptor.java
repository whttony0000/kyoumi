package com.aikon.wht.intercepter;

import com.aikon.wht.utils.CookieUtil;
import com.aikon.wht.utils.ShiroUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.subject.Subject;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * request拦截器.
 *
 * @author haitao.wang
 */
@Slf4j
public class RequestInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        CookieUtil.addCookie(response,86400,"kyoumi", "com/aikon/wht");
        if (request.getParameter("mock") != null) {
            Subject subject = ShiroUtils.getSubject();
            //sha256加密
            String password = new Sha256Hash("123").toHex();
            UsernamePasswordToken token = new UsernamePasswordToken("982980292@qq.com", password);
            subject.login(token);
        }
        return super.preHandle(request, response, handler);
    }
}
