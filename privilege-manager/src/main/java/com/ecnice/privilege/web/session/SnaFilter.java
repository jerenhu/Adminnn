/*
 * Copyright (c) 2011 OECP All Rights Reserved.                	
 * http://www.oecp.cn                                                                 
 */

package com.ecnice.privilege.web.session;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ecnice.privilege.cache.CacheEntity;
import com.ecnice.privilege.cache.CacheListHandler;
import com.ecnice.privilege.common.SessionMap;
import com.ecnice.privilege.constant.PrivilegeConstant;

/**
 * @Comment:集群环境下，session 不共享架构下的Servlet过滤器
 * @author bruce.liu
 * @Create Date 2014年3月27日
 */
public class SnaFilter implements Filter {
	private Log logger = LogFactory.getLog(SnaFilter.class);

	private static boolean cluster = false;
	private String[] EXCLUDE_URLS = {
			"login.do", //登录
			"logout.do", // HR频道
			"init.do", // 登录用户代理
			"verifyCode.do",
			"common/"
		};


	public void destroy() {
		// TODO 记录系统日志
	}

	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		final HttpServletRequest hrequest = (HttpServletRequest) req;
		final HttpServletResponse hresponse = (HttpServletResponse) res;
		String uri = hrequest.getRequestURI();
		
		
		//对特殊的一些url不做拦截处理
		if(null!=EXCLUDE_URLS && EXCLUDE_URLS.length>0) {
			boolean exclude = false;
			for(int i=0;i<EXCLUDE_URLS.length;i++) {
				if(uri.contains(EXCLUDE_URLS[i])) {
					exclude = true;
					break;
				}
			}
			if(exclude){
				chain.doFilter(hrequest, hresponse);
				return;
			}
		}
		HttpSession httpSession = hrequest.getSession();
		String sessionId = hrequest.getParameter("sessionId");
		if(StringUtils.isBlank(sessionId)) {
			sessionId = httpSession.getId();
		}
		long sessionTime = httpSession.getCreationTime();

		SessionMap sessionMap = null;
		if (StringUtils.isBlank(sessionId)) {
			chain.doFilter(hrequest, hresponse);
			return ;
		}
		CacheEntity ce = CacheListHandler.getCache(sessionId);
		if (ce != null) {
			sessionMap = (SessionMap) ce.getCacheContext();
			if (sessionMap.get("creationTime") != null) {
				String createTime = sessionMap.get("creationTime").toString();
				if ((Long.valueOf(createTime)) < sessionTime) {
					initHttpSession(httpSession, sessionMap);
				}
//				if(createTime.indexOf(".")!=-1) {
//					try {
//						DecimalFormat df=new DecimalFormat("#");
//						if(createTime.indexOf("E")>0){
//							BigDecimal db = new BigDecimal(createTime);
//							createTime = db.toPlainString();
//						}
//						String result=df.format(createTime);
//						if((Long.valueOf(result)) < sessionTime) {
//							initHttpSession(httpSession, sessionMap);
//						}
//					} catch (NumberFormatException e) {
//						logger.error("格式化失败");
//					}
//				}else {
//					if ((Long.valueOf(createTime)) != sessionTime) {
//						initHttpSession(httpSession, sessionMap);
//					}
//				}
			}

			Object userInfoObj = sessionMap.get(PrivilegeConstant.LOGIN_USER);
			if (userInfoObj == null) {
				if (uri.indexOf("loginUI.do") == -1) {
					String path = hrequest.getContextPath();
					String basePath = hrequest.getScheme()+"://"+hrequest.getServerName()+":"+hrequest.getServerPort()+path+"/";
					hresponse.sendRedirect(basePath+"/loginUI.do");
					return;
				}
			}
		}
		chain.doFilter(hrequest, hresponse);
	}

	private void initHttpSession(HttpSession session, SessionMap sessionMap) {
		Set<String> keySet = sessionMap.keySet();
		Iterator<String> it = keySet.iterator();
		while (it.hasNext()) {
			String key = it.next();
			session.setAttribute(key, sessionMap.get(key));
		}
//		sessionMap.put("creationTime", session.getCreationTime());
		CacheEntity cce = new CacheEntity(session.getId(), sessionMap, PrivilegeConstant.SESSION_OUT_TIME);
		CacheListHandler.addCache(session.getId(), cce);
		//设置session的过期时间
		session.setMaxInactiveInterval(PrivilegeConstant.SESSION_OUT_TIME);
	}

	public void init(FilterConfig chain) throws ServletException {
		cluster = true;
	}

	public static boolean isCluster() {
		return cluster;
	}

}
