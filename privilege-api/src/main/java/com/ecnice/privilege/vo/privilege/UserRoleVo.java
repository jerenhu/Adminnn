package com.ecnice.privilege.vo.privilege;

import java.io.Serializable;

/**
 * 用户角色关联
 * @author liuwenjun1
 */
public class UserRoleVo implements Serializable {
	private static final long serialVersionUID = -3313350624746186586L;
	//用户名
	private String username;
	//角色标识
	private String sn;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getSn() {
		return sn;
	}
	public void setSn(String sn) {
		this.sn = sn;
	}
	@Override
	public String toString() {
		return "UserRoleVo [username=" + username + ", sn=" + sn + "]";
	}
	
}
