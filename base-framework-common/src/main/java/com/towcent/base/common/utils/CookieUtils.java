package com.towcent.base.common.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Cookie 辅助类
 * 
 * @author liufang
 * 
 */
public class CookieUtils {
	
	/**
	 * 获得cookie
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @param name
	 *            cookie name
	 * @return if exist return cookie, else return null.
	 */
	public static Cookie getCookie(HttpServletRequest request, String name,String domain) {
		Assert.notNull(request);
		Cookie[] cookies = request.getCookies();
		if (cookies != null && cookies.length > 0) {
			for (Cookie c : cookies) {
				if (c.getName().equals(name)) {
					return c;
				}
			}
		}
		return null;
	}
	
	/**
	 * 获得cookie
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @param name
	 *            cookie name
	 * @return if exist return cookie, else return null.
	 */
	public static Cookie getCookie(HttpServletRequest request, String name) {
		return getCookie(request,name,null);
	}

	/**
	 * 根据部署路径，将cookie保存在根目录。
	 * 
	 * @param request
	 * @param response
	 * @param name
	 * @param value
	 * @param expiry
	 * @return
	 */
	public static Cookie addCookie(HttpServletRequest request,
			HttpServletResponse response, String name, String value,
			Integer expiry) {
		Cookie cookie = new Cookie(name, value);
		cookie.setHttpOnly(true);
		if (expiry != null) {
			cookie.setMaxAge(expiry);
		}
		String ctx = request.getContextPath();
		cookie.setPath(StringUtils.isBlank(ctx) ? "/" : ctx);
		response.addCookie(cookie);
		return cookie;
	}
	
	/**
	 * 根据部署路径，将cookie保存在根目录。
	 * @param response
	 * @param name
	 * @param value
	 * @param path
	 * @param domain
	 * @param expiry
	 * @return
	 */
	public static Cookie addCookie(HttpServletResponse response, String name, String value,String path,String domain,
			Integer expiry) {
		Cookie cookie = new Cookie(name, value);
		cookie.setHttpOnly(true);
		if (expiry != null) {
			cookie.setMaxAge(expiry);
		}
		cookie.setPath(StringUtils.isBlank(path) ? "/" : path);
		cookie.setDomain(domain);
		response.addCookie(cookie);
		return cookie;
	}
	
	/**
	 * 添加cookie
	 * @param response
	 * @param cookie
	 * @param path
	 * @param domain
	 * @param expiry
	 * @return
	 */
	public static Cookie addCookie(HttpServletResponse response,Cookie cookie,String path,String domain,
			Integer expiry) {
		if(null==cookie){
			return null;
		}
		if (expiry != null) {
			cookie.setMaxAge(expiry);
		}
		cookie.setHttpOnly(true);
		cookie.setPath(StringUtils.isBlank(path) ? "/" : path);
		cookie.setDomain(domain);
		response.addCookie(cookie);
		return cookie;
	}

	/**
	 * 取消cookie
	 * 
	 * @param response
	 * @param name
	 * @param domain
	 */
	public static void cancleCookie(HttpServletResponse response, String name,
			String domain) {
		Cookie cookie = new Cookie(name, null);
		cookie.setMaxAge(0);
		cookie.setPath("/");
		if (!StringUtils.isBlank(domain)) {
			cookie.setDomain(domain);
		}
		response.addCookie(cookie);
	}
}
