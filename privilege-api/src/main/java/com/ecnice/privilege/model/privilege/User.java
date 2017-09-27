package com.ecnice.privilege.model.privilege;

import java.io.Serializable;
import java.util.Date;

import com.ecnice.privilege.model.BaseModel;

/**
 * @Comment: 用户
 * @author bruce.liu
 * @Create Date 2014年3月11日
 */
@SuppressWarnings("serial")
public class User extends BaseModel implements Serializable {
	//id 32
	private String id;
	//真实姓名 20
	private String realName;
	//用户名 30
	private String username;
	//密码 40
	private String password;
	//电话 
	private String tel;
	//手机
	private String mobile;
	//座机 15
	private String phone;
	//邮箱 30
	private String email;
	//图像 30
	private String image;
	//公司id
	private String companyId;
	//部门id 32
	private String departmentId;
	//性别 0标示男 1标示女
	private Integer sex;
	//地址 100
	private String address;
	//传真
	private String fax;
	//失效月数
	private Integer failMonth;
	//失效时间
	private Date failureTime;
	//密码失效日期
	private Date pwdFtime;
	//初始密码是否已修改 1是0否
	private Integer pwdInit;
	//临时变量：用户所属系统id
	private String systemIds;
	//临时变量:部门名称
	private String deptName;
	//临时变量:角色名称集
	private String roles;
	//临时变量:账户失效时间str
	private String failureTimeStr="-";
	//临时变量:密码过期时间str
	private String pwdFtimeStr="-";
	/**临时变量:是否失效标识 1失效，0未失效 ,2未设置失效时间*/
	private Integer failFlag;
	
	/**
	 * 临时变量
	 */
	private String keyword;
	
	private String companyName;
	
	public Integer getFailFlag() {
		return failFlag;
	}
	/**临时变量:是否失效标识 1失效，0未失效 ,2未设置失效时间*/
	public void setFailFlag(Integer failFlag) {
		this.failFlag = failFlag;
	}
	public String getFailureTimeStr() {
		return failureTimeStr;
	}
	public void setFailureTimeStr(String failureTimeStr) {
		this.failureTimeStr = failureTimeStr;
	}
	public Integer getFailMonth() {
		return failMonth;
	}
	public void setFailMonth(Integer failMonth) {
		this.failMonth = failMonth;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	public String getPwdFtimeStr() {
		return pwdFtimeStr;
	}
	public void setPwdFtimeStr(String pwdFtimeStr) {
		this.pwdFtimeStr = pwdFtimeStr;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public String getRoles() {
		return roles;
	}
	public void setRoles(String roles) {
		this.roles = roles;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public String getSystemIds() {
		return systemIds;
	}
	public void setSystemIds(String systemIds) {
		this.systemIds = systemIds;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}
	public Integer getSex() {
		return sex;
	}
	public void setSex(Integer sex) {
		this.sex = sex;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	public Date getFailureTime() {
		return failureTime;
	}
	public void setFailureTime(Date failureTime) {
		this.failureTime = failureTime;
	}
	public Date getPwdFtime() {
		return pwdFtime;
	}
	public void setPwdFtime(Date pwdFtime) {
		this.pwdFtime = pwdFtime;
	}
	public Integer getPwdInit() {
		return pwdInit;
	}
	public void setPwdInit(Integer pwdInit) {
		this.pwdInit = pwdInit;
	}
	public String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", realName=" + realName + ", username=" + username + ", password=" + password + ", tel=" + tel + ", mobile=" + mobile + ", phone=" + phone
				+ ", email=" + email + ", image=" + image + ", companyId=" + companyId + ", departmentId=" + departmentId + ", sex=" + sex + ", address=" + address + ", fax=" + fax
				+ ", failMonth=" + failMonth + ", failureTime=" + failureTime + ", pwdFtime=" + pwdFtime + ", pwdInit=" + pwdInit + ", systemIds=" + systemIds + ", deptName="
				+ deptName + ", roles=" + roles + ", failureTimeStr=" + failureTimeStr + ", pwdFtimeStr=" + pwdFtimeStr + ", failFlag=" + failFlag + ", companyName=" + companyName
				+ "]";
	}
	
	
	
}
