package com.ecnice.privilege.model.privilege;

import com.ecnice.privilege.model.BaseModel;

/**
 * 角色系统
 * @author liuwenjun1
 */
public class RoleSystem extends BaseModel {
		
	//id
	private String id;
	//角色id
	private String roleId;
	//系统id
	private String systemId;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public String getSystemId() {
		return systemId;
	}
	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}
	@Override
	public String toString() {
		return "RoleSystem [id=" + id + ", roleId=" + roleId + ", systemId=" + systemId + "]";
	}

}
