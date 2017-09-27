/*
 * Copyright (c) 2011 OECP All Rights Reserved.                	
 * http://www.oecp.cn                                                                 
 */

package com.ecnice.privilege.web.session;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import com.ecnice.privilege.cache.CacheEntity;
import com.ecnice.privilege.cache.CacheListHandler;
import com.ecnice.privilege.common.SessionMap;
import com.ecnice.privilege.constant.PrivilegeConstant;

/**
 * @Comment:集群环境下的session监听并处理
 * @author bruce.liu
 * @Create Date 2014年3月27日
 */
public class ClusterHttpSessionListener implements HttpSessionListener {
	/**
	 * 创建session同时创建缓存Session
	 */
	public void sessionCreated(HttpSessionEvent event) {
		HttpSession httpSession = event.getSession();
		String sessionId = httpSession.getId();
		SessionMap sessionMap = new SessionMap();
		// 复制session的创建时间到缓存中，以用来在session转移时使用 
//		sessionMap.put("creationTime", httpSession.getCreationTime()+"");
		// 把session和时间放到缓存中
		CacheEntity cce = new CacheEntity(sessionId, sessionMap, PrivilegeConstant.SESSION_OUT_TIME);
		CacheListHandler.addCache(sessionId, cce);
	}

	/**
	 * 摧毁Session
	 */
	public void sessionDestroyed(HttpSessionEvent event) {
		HttpSession httpSession = event.getSession();
		String sessionId = httpSession.getId();
		// 直接清除掉
		CacheListHandler.removeCache(sessionId);
		//将session清空
		httpSession = null;
	}

}
