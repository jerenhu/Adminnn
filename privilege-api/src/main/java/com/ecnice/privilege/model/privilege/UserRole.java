package com.ecnice.privilege.model.privilege;

import java.io.Serializable;

import com.ecnice.privilege.model.BaseModel;

/**
 * @Comment: 用户角色
 * @author bruce.liu
 * @Create Date 2014年3月11日
 */
@SuppressWarnings("serial")
public class UserRole extends BaseModel implements Serializable {
	//id 32
	private String id;
	//用户id 32
	private String userId;
	//用户工号
	private String userNo;
	//角色id 32
	private String roleId;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public String getUserNo() {
		return userNo;
	}
	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}
	@Override
	public String toString() {
		return "UserRole [id=" + id + ", userId=" + userId + ", userNo=" + userNo + ", roleId=" + roleId + "]";
	}
}
