/*
 * Copyright (c) 2011 OECP All Rights Reserved.                	
 * http://www.oecp.cn                                                                 
 */

package com.ecnice.privilege.web.session;

import java.io.Serializable;
import java.util.Set;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;

import com.ecnice.privilege.cache.CacheEntity;
import com.ecnice.privilege.cache.CacheListHandler;
import com.ecnice.privilege.common.SessionMap;
import com.ecnice.privilege.constant.PrivilegeConstant;
import com.ecnice.privilege.model.privilege.ACL;
import com.ecnice.privilege.model.privilege.User;
import com.ecnice.privilege.utils.JsonUtils;

/**
 * @Comment:用于集群环境下对session处理的HttpSessionAttributeListener
 * @author bruce.liu
 * @Create Date 2014年3月27日
 */
public class ClusterHttpSessionAttributeListener implements
		HttpSessionAttributeListener {

	/**
	 * 添加Session到缓存中
	 */
	@SuppressWarnings("unchecked")
	public void attributeAdded(HttpSessionBindingEvent event) {
		HttpSession httpSession = event.getSession();
		String attrName = event.getName();

		Object attrValue = event.getValue();
		String sessionId = httpSession.getId();

		// 把session放在缓存中
		CacheEntity ce = CacheListHandler.getCache(sessionId);
		SessionMap sessionMap = null;
		if (ce == null) {
			sessionMap = new SessionMap();
		} else {
			sessionMap = (SessionMap) ce.getCacheContext();
		}
		if (attrValue instanceof Serializable) {
			if(attrValue!=null) {
				sessionMap.put(attrName, JsonUtils.toJson(attrValue));
			}
//			if (attrValue instanceof User && attrValue != null) {
//				sessionMap.put(attrName, JsonUtils.toJson((User) attrValue));
//			} else if (attrValue instanceof Set) {
//				sessionMap
//						.put(attrName, JsonUtils.toJson((Set<ACL>) attrValue));
//			}
		}
		CacheEntity cce = new CacheEntity(sessionId, sessionMap, PrivilegeConstant.SESSION_OUT_TIME);
		CacheListHandler.addCache(sessionId, cce);
	}

	/**
	 * 移除Session缓存中的session
	 */
	public void attributeRemoved(HttpSessionBindingEvent event) {
		HttpSession httpSession = event.getSession();
		String sessionId = httpSession.getId();
		// 如果移除session的时候，我把里面的信息全部移除
		httpSession.removeAttribute(PrivilegeConstant.LOGIN_USER);
		httpSession.removeAttribute(PrivilegeConstant.LOGIN_USER_ACLS);
		CacheListHandler.removeCache(sessionId);
	}

	public void attributeReplaced(HttpSessionBindingEvent event) {
		attributeAdded(event);
	}

}
