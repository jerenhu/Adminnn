package com.ecnice.privilege.vo.privilege;

import java.io.Serializable;
import java.util.List;

import com.ecnice.privilege.model.privilege.Module;
import com.ecnice.privilege.model.privilege.User;

public class LoginVo implements Serializable {
	private static final long serialVersionUID = 8999766360745165910L;
	/**
	 * 登录用户
	 */
	private User loginUser;
	/**
	 * 菜单列表
	 */
	public List<Module> modules;
	/**
	 * sid：user的id+时间戳
	 */
	public String sid;
	
	public User getLoginUser() {
		return loginUser;
	}
	public void setLoginUser(User loginUser) {
		this.loginUser = loginUser;
	}
	public List<Module> getModules() {
		return modules;
	}
	public void setModules(List<Module> modules) {
		this.modules = modules;
	}
	public String getSid() {
		return sid;
	}
	public void setSid(String sid) {
		this.sid = sid;
	}
	@Override
	public String toString() {
		return "LoginVo [loginUser=" + loginUser + ", modules=" + modules
				+ ", sid=" + sid + "]";
	}
}
