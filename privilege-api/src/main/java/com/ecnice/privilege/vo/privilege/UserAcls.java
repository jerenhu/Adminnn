package com.ecnice.privilege.vo.privilege;

import java.io.Serializable;
import java.util.Set;

import com.ecnice.privilege.model.privilege.ACL;
import com.ecnice.privilege.model.privilege.User;
/**
 * 用户的acl
*@Author:Bruce.Liu
*@Since:2014年5月23日
*@Version:1.1.0浙江蘑菇加电子商务有限公司 2014 ~ 2015 版权所有
 */
public class UserAcls implements Serializable{
	private static final long serialVersionUID = 4257470135287579384L;
	/**
	 * sessionID
	 */
	private String sessionId ;
	/**
	 * session的创建时间
	 */
	private String creationTime;
	 /**
	  * 用户的访问控制列表
	  */
	private Set<ACL> acls;
	/**
	 * 登录用户
	 */
	private User loginUser;
	
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	public String getCreationTime() {
		return creationTime;
	}
	public void setCreationTime(String creationTime) {
		this.creationTime = creationTime;
	}
	public Set<ACL> getAcls() {
		return acls;
	}
	public void setAcls(Set<ACL> acls) {
		this.acls = acls;
	}
	public User getLoginUser() {
		return loginUser;
	}
	public void setLoginUser(User loginUser) {
		this.loginUser = loginUser;
	}
	
}

	