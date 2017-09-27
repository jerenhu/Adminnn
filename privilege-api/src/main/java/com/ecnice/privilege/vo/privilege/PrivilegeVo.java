package com.ecnice.privilege.vo.privilege;

import java.io.Serializable;

/**
* 权限Vo
*@Author:Bruce.Liu
*@Since:2014年6月4日
*@Version:1.1.0浙江蘑菇加电子商务有限公司 2014 ~ 2015 版权所有
 */
public class PrivilegeVo implements Serializable{

	/**
	 * 用户名
	 */
	private String username;
	/**
	 * 真实姓名
	 */
	private String realname;
	/**
	 * 角色标示
	 */
	private String roleSn;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getRealname() {
		return realname;
	}
	public void setRealname(String realname) {
		this.realname = realname;
	}
	public String getRoleSn() {
		return roleSn;
	}
	public void setRoleSn(String roleSn) {
		this.roleSn = roleSn;
	}
	
	
}
