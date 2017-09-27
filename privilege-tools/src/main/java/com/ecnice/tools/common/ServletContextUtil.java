package com.ecnice.tools.common;

import javax.servlet.ServletContext;

import org.springframework.web.context.ServletContextAware;

/**
 * ServletContentUtil工具类
 * @Title:
 * @Description:
 * @Author:wentaoxiang
 * @Since:2016年1月12日 下午2:20:28
 * @Version:1.1.0
 * @Copyright:Copyright (c) 浙江蘑菇加电子商务有限公司 2015 ~ 2016 版权所有
 */
public class ServletContextUtil implements ServletContextAware {

	private static ServletContext servletContext;

	@SuppressWarnings("static-access")
	public void setServletContext(ServletContext servletContext) {
		this.servletContext=servletContext; 
	}
	 
	/**
	 * 得到ServletContext
	 * @return
	 */
	public static ServletContext getServletContext() {
		return servletContext;
	}
	
	/**
	 * 得到servletContext中的对象
	 * @param name
	 * @return
	 */
	public static Object getAppObject(String name) {
		if(servletContext!=null) {
			return servletContext.getAttribute(name);
		}
		return null;
	}
}
