package com.ecnice.privilege.dao.privilege;

import com.ecnice.privilege.model.privilege.SessionData;

/**
 * @Title:
 * @Description:sessionDataDao
 * @Author:Bruce.Liu
 * @Since:2014年4月10日
 * @Version:1.1.0
浙江蘑菇加电子商务有限公司 2014 ~ 2015 版权所有
 */
public interface ISessionDataDao {

	/**
	 * @param sessionData
	 * @throws Exception
	 * @Description:添加session数据
	 */
	public void insertSessionData(SessionData sessionData) throws Exception;

	/**
	 * @param sessionData
	 * @throws Exception
	 * @Description:更新session数据
	 */
	public void updateSessionData(SessionData sessionData) throws Exception;

	/**
	 * @param sessionId
	 * @return
	 * @throws Exception
	 * @Description:得到session数据
	 */
	public SessionData getSessionDataBySessionId(String sessionId)
			throws Exception;

	/**
	 * @param sessionId
	 * @throws Exception
	 * @Description:删除session数据
	 */
	public void deleteSessionDataBySessionId(String sessionId) throws Exception;

	/**
	 * @param creationTime
	 * @throws Exception
	 * @Description:通过创建时间删除来删除session数据
	 */
	public void deleteSessionDataByCreationTime(long creationTime)
			throws Exception;
}
