package com.aikon.wht.utils;


import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

@Slf4j
public class CookieUtil {

	public static final ResourceBundle OTHER = ResourceBundle.getBundle("other");

	public static final String POJ_DOMAIN = ResourceBundle.getBundle("other").getString("domain");

	public static Map<String, String> getCookieMap(HttpServletRequest request) {
		HashMap<String, String> ckMap = new HashMap<String, String>();
		Cookie[] ckies = request.getCookies();
		if (ckies != null) {
			for (int i = 0; i < ckies.length; i++) {
				try {
					ckMap.put(ckies[i].getName(), URLDecoder.decode(ckies[i].getValue(), "utf-8"));
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
		}
		return ckMap;
	}

	public static String getValueByCookieName(HttpServletRequest request, String cookieName) {
		Cookie[] ckies = request.getCookies();
		if (ckies != null) {
			for (int i = 0; i < ckies.length; i++) {
				if (ckies[i].getName().equals(cookieName)) {
					try {
						return URLDecoder.decode(ckies[i].getValue(), "utf-8");
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return null;
	}

	public static void addCookie(HttpServletResponse resp, int maxAge, String key, String value) {
		addCookie(resp, maxAge, key, value, POJ_DOMAIN);
	}

	public static void addCookie(HttpServletResponse resp, int maxAge, String key, String value, String domain) {
		Cookie cookie = null;
		try {
			cookie = new Cookie(key, URLEncoder.encode(value, "utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		if (maxAge >= 0) {
            cookie.setMaxAge(maxAge);
        }
		if (domain != null) {
			cookie.setDomain(domain);
		} else {
			cookie.setDomain(POJ_DOMAIN);
		}
		cookie.setPath("/");
		resp.addCookie(cookie);
	}

	public static void removeCookie(HttpServletResponse response, String key, String domain) {
		addCookie(response, 0, key, "", domain);
	}

}
