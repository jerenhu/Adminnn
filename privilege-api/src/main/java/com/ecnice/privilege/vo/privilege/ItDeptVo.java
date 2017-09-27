package com.ecnice.privilege.vo.privilege;

import java.io.Serializable;

public class ItDeptVo implements Serializable {
	/**
	 * id
	 */
	private String id;
	/**
	 * 父id
	 */
	private String pid;
	/**
	 * 名称
	 */
	private String name;
	// 临时变量
	private String type;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
