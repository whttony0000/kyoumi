package com.aikon.wht.utils;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

/**
 * Shiro工具类.
 *
 * @author haitao.wang
 */
public class ShiroUtils {

	public static Session getSession() {
		return SecurityUtils.getSubject().getSession();
	}

	public static Subject getSubject() {
		return SecurityUtils.getSubject();
	}

//	public static SysUserEntity getUserEntity() {
//		return (SysUserEntity)SecurityUtils.getSubject().getPrincipal();
//	}
//
//	public static Long getUserId() {
//		return getUserEntity().getUserId();
//	}
	
	public static void setSessionAttribute(Object key, Object value) {
		getSession().setAttribute(key, value);
	}

	public static Object getSessionAttribute(Object key) {
		return getSession().getAttribute(key);
	}

	public static boolean isLogin() {
		return SecurityUtils.getSubject().getPrincipal() != null;
	}

	public static void logout() {
		SecurityUtils.getSubject().logout();
	}
	
	public static String getKaptcha(String key) {
		String kaptcha = getSessionAttribute(key).toString();
		getSession().removeAttribute(key);
		return kaptcha;
	}

	public static String getMailTimeCode(String key) {
		String timeCode = getSessionAttribute(key).toString();
		getSession().removeAttribute(key);
		return timeCode;
	}

}
