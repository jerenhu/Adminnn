package com.ecnice.privilege.model.privilege;

import java.io.Serializable;
import java.util.List;

import com.ecnice.privilege.model.BaseModel;

/**
 * @Comment:角色
 * @author bruce.liu
 * @Create Date 2014年3月11日
 */
public class Role extends BaseModel implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 1275239552021904861L;
	//id 32
	private String id;
	//公司id 》》》
	private String companyId;
	// 名称 20
	private String name;
	
	/**
	 * 【临时变量】
	 * 角色全名【公司编码-公司名称-系统名称-角色名称】
	 */
	private String longName;
	// 标示 30
	private String sn;
	// 角色等级
	private String roleLevel;
	// 角色所属系统
	private String systemId;
	// 备注 80
	private String note;
	//是否选择
	private int status = 0;
	/**
	 * 有效状态（1：有效；0：失效）
	 */
	private Integer validState;
	//用于显示子部门的所有角色
	private List<String> companyIdList;
	// 角色对应的用户ID
	private String personids; 
	// 角色对应的公司ID
	private String companyIds; 
	// 角色对应的公司名称
	private String companyNames; 
	private String usernames; //临时存储用户名
	//用于页面角色查询内容的载体
	private String querycontent;
	//easyUIcheckbox选中标识
	private boolean checked=false;
	//临时变量 用户姓名
	private String users;
	
	public boolean isChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLongName() {
		return longName;
	}
	public void setLongName(String longName) {
		this.longName = longName;
	}
	public String getSn() {
		return sn;
	}
	public void setSn(String sn) {
		this.sn = sn;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getUsers() {
		return users;
	}
	public void setUsers(String users) {
		this.users = users;
	}
	public String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public List<String> getCompanyIdList() {
		return companyIdList;
	}
	public void setCompanyIdList(List<String> companyIdList) {
		this.companyIdList = companyIdList;
	}
	public String getPersonids() {
		return personids;
	}
	public void setPersonids(String personids) {
		this.personids = personids;
	}
	
	public String getCompanyIds() {
		return companyIds;
	}
	public void setCompanyIds(String companyIds) {
		this.companyIds = companyIds;
	}
	public String getQuerycontent() {
		return querycontent;
	}
	public void setQuerycontent(String querycontent) {
		this.querycontent = querycontent;
	}
	public String getUsernames() {
		return usernames;
	}
	public void setUsernames(String usernames) {
		this.usernames = usernames;
	}
	public String getSystemId() {
		return systemId;
	}
	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}
	public String getCompanyNames() {
		return companyNames;
	}
	public void setCompanyNames(String companyNames) {
		this.companyNames = companyNames;
	}
	public String getRoleLevel() {
		return roleLevel;
	}
	public void setRoleLevel(String roleLevel) {
		this.roleLevel = roleLevel;
	}
	
	public Integer getValidState() {
		return validState;
	}
	public void setValidState(Integer validState) {
		this.validState = validState;
	}
	@Override
	public String toString() {
		return "Role [id=" + id + ", companyId=" + companyId + ", name=" + name + ", longName=" + longName + ", sn="
				+ sn + ", roleLevel=" + roleLevel + ", systemId=" + systemId + ", note=" + note + ", status=" + status
				+ ", validState=" + validState + ", companyIdList=" + companyIdList + ", personids=" + personids
				+ ", companyIds=" + companyIds + ", companyNames=" + companyNames + ", usernames=" + usernames
				+ ", querycontent=" + querycontent + ", checked=" + checked + ", users=" + users + "]";
	}
}
