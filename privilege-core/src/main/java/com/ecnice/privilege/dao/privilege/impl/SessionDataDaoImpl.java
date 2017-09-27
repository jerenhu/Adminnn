package com.ecnice.privilege.dao.privilege.impl;

import org.springframework.stereotype.Repository;

import com.ecnice.privilege.dao.MybatisTemplate;
import com.ecnice.privilege.dao.privilege.ISessionDataDao;
import com.ecnice.privilege.model.privilege.SessionData;

/**
 * @Title:
 * @Description:session数据实现类
 * @Author:Bruce.Liu
 * @Since:2014年4月10日
 * @Version:1.1.0
浙江蘑菇加电子商务有限公司 2014 ~ 2015 版权所有
 */
@Repository
public class SessionDataDaoImpl extends MybatisTemplate implements
		ISessionDataDao {

	@Override
	public void insertSessionData(SessionData sessionData) throws Exception {
		this.insert("SessionDataXML.insertSessionData", sessionData);
	}

	@Override
	public void updateSessionData(SessionData sessionData) throws Exception {
		this.update("SessionDataXML.updateSessionData", sessionData);
	}

	@Override
	public SessionData getSessionDataBySessionId(String sessionId)
			throws Exception {
		return (SessionData) this.selectOne("SessionDataXML.getSessionDataBySessionId", sessionId);
	}

	@Override
	public void deleteSessionDataBySessionId(String sessionId) throws Exception {
		this.delete("SessionDataXML.deleteSessionDataBySessionId", sessionId);
	}

	@Override
	public void deleteSessionDataByCreationTime(long creationTime)
			throws Exception {
		this.delete("SessionDataXML.deleteSessionDataByCreationTime", creationTime);
	}

}
