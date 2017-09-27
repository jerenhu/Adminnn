package com.ecnice.privilege.service.privilege.impl;
//package com.mhome.privilege.service.privilege.impl;
//
//import javax.annotation.Resource;
//
//import org.apache.log4j.Logger;
//import org.springframework.stereotype.Service;
//
//import com.mhome.privilege.cache.CacheListHandler;
//import com.mhome.privilege.common.SessionMap;
//import com.mhome.privilege.component.redis.RedisClientTemplate;
//import com.mhome.privilege.constant.PrivilegeConstant;
//import com.mhome.privilege.dao.privilege.ISessionDataDao;
//import com.mhome.privilege.model.privilege.SessionData;
//import com.mhome.privilege.service.privilege.ISessionDataService;
//import com.mhome.privilege.utils.JsonUtils;
//
///**
// * @Title:
// * @Description:session操作实现类
// * @Author:Bruce.Liu
// * @Since:2014年4月10日
// * @Version:1.1.0 浙江蘑菇加电子商务有限公司 2014 ~ 2015 版权所有
// */
//@Service
//public class SessionDataServiceImpl implements ISessionDataService {
//
//	private static final Logger log = Logger
//			.getLogger(SessionDataServiceImpl.class);
//
//	@Resource
//	private CacheListHandler cacheListHandler;
//	@Resource
//	private ISessionDataDao sessionDataDao;
//
//	@Override
//	public SessionMap getSessionDataBySessionId(String sessionId) {
//		SessionData sessionData = null;
//		try {
//			sessionData = sessionDataDao
//					.getSessionDataBySessionId(sessionId);
//		} catch (Exception e2) {
//			e2.printStackTrace();
//			log.error("查询sessionData数据报错，错误原因："+e2.getMessage());
//		}
//		// 1:先从redis中取
//		SessionMap sessionMap = null;
//		try {
//			String sessionMapJson = redisClientTemplate.get(sessionId);
//			if (sessionMapJson == null) {
//				sessionMap = new SessionMap();
//			} else {
//				sessionMap = (SessionMap) JsonUtils.jsonToObj(sessionMapJson,
//						SessionMap.class);
//				if(sessionData == null) {
//					sessionData = new SessionData();
//					sessionData.setSessionId(sessionId);
//					sessionData.setUserInfo((String) sessionMap
//							.get(PrivilegeConstant.LOGIN_USER));
//					sessionData.setAclsInfo((String) sessionMap
//							.get(PrivilegeConstant.LOGIN_USER_ACLS));
//					sessionData.setCreationTime(Long.parseLong(sessionMap.get("creationTime")+""));
//					sessionDataDao.insertSessionData(sessionData);
//				}
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			log.error("redis connection is out time or redis is die !");
//			// 2：从数据库中取
//			try {
//				sessionMap.put(PrivilegeConstant.LOGIN_USER,
//						sessionData.getUserInfo());
//				sessionMap.put(PrivilegeConstant.LOGIN_USER_ACLS,
//						sessionData.getAclsInfo());
//			} catch (Exception e1) {
//				e1.printStackTrace();
//				log.error("get db data is error !");
//			}
//		}
//
//		return sessionMap;
//	}
//
//	@Override
//	public void updateSessionData(SessionMap sessionMap, String sessionId)
//			throws Exception {
//		// 1.插入redis中
//		redisClientTemplate.setex(sessionId,
//				PrivilegeConstant.SESSION_OUT_TIME,
//				JsonUtils.toJson(sessionMap));
//		// 2.插入数据库中
//		SessionData sessionData = new SessionData();
//		sessionData.setSessionId(sessionId);
//		sessionData.setUserInfo((String) sessionMap
//				.get(PrivilegeConstant.LOGIN_USER));
//		sessionData.setAclsInfo((String) sessionMap
//				.get(PrivilegeConstant.LOGIN_USER_ACLS));
//		sessionDataDao.updateSessionData(sessionData);
//	}
//
//	@Override
//	public void insertSessionData(SessionMap sessionMap, String sessionId,
//			long creationTime) throws Exception {
//		// 1.插入redis中
//		redisClientTemplate.setex(sessionId,
//				PrivilegeConstant.SESSION_OUT_TIME,
//				JsonUtils.toJson(sessionMap));
//		// 2.插入数据库中
//		SessionData sessionData = new SessionData();
//		sessionData.setSessionId(sessionId);
//		sessionData.setUserInfo((String) sessionMap
//				.get(PrivilegeConstant.LOGIN_USER));
//		sessionData.setAclsInfo((String) sessionMap
//				.get(PrivilegeConstant.LOGIN_USER_ACLS));
//		sessionData.setCreationTime(creationTime);
//		sessionDataDao.insertSessionData(sessionData);
//	}
//
//	@Override
//	public void deleteSessionData(String sessionId) {
//		try {
//			redisClientTemplate.del(sessionId);
//			sessionDataDao.deleteSessionDataBySessionId(sessionId);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//}
