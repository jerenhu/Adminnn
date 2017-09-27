package com.ecnice.privilege.model.privilege;

import java.io.Serializable;

/**
 * @Title:
 * @Description:session的保存
 * @Author:Bruce.Liu
 * @Since:2014年4月10日
 * @Version:1.1.0
浙江蘑菇加电子商务有限公司 2014 ~ 2015 版权所有
 */
@SuppressWarnings("serial")
public class SessionData implements Serializable {
	//sessionId
	private String sessionId;
	//用户信息
	private String userInfo;
	//权限列表
	private String aclsInfo;
	//其他的信息
	private String otherInfo;
	//失效时间戳
	private long creationTime;
	
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	public String getUserInfo() {
		return userInfo;
	}
	public void setUserInfo(String userInfo) {
		this.userInfo = userInfo;
	}
	public String getAclsInfo() {
		return aclsInfo;
	}
	public void setAclsInfo(String aclsInfo) {
		this.aclsInfo = aclsInfo;
	}
	public long getCreationTime() {
		return creationTime;
	}
	public void setCreationTime(long creationTime) {
		this.creationTime = creationTime;
	}
	public String getOtherInfo() {
		return otherInfo;
	}
	public void setOtherInfo(String otherInfo) {
		this.otherInfo = otherInfo;
	}
}
