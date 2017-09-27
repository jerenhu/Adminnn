package com.ecnice.privilege.vo.privilege;

import java.io.Serializable;

/**
 * 
 * @Title:
 * @Description: LoginLog的vo类
 * @Author:TaoXiang.Wen
 * @Since:2014年4月9日
 * @Version:1.1.0浙江蘑菇加电子商务有限公司 2014 ~ 2015 版权所有
 */
public class LoginLogVo implements Serializable {
	// id
	private int id;
	// 访问ip
	private String ip;
	// 操作人id
	private String operationId;
	// 操作人的姓名
	private String operationUsername;
	// 操作人姓名
	private String operationPerson;
	// 操作内容
	private String operationContent;
	// 操作开始时间
	private String operationTimeStart;
	// 操作结束时间
	private String operationTimeEnd;

	public LoginLogVo() {
		super();
	}

	public LoginLogVo(int id, String ip, String operationId, String operationUsername, String operationPerson,
			String operationContent, String operationTimeStart, String operationTimeEnd) {
		super();
		this.id = id;
		this.ip = ip;
		this.operationId = operationId;
		this.operationUsername = operationUsername;
		this.operationPerson = operationPerson;
		this.operationContent = operationContent;
		this.operationTimeStart = operationTimeStart;
		this.operationTimeEnd = operationTimeEnd;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getOperationId() {
		return operationId;
	}

	public void setOperationId(String operationId) {
		this.operationId = operationId;
	}

	public String getOperationUsername() {
		return operationUsername;
	}

	public void setOperationUsername(String operationUsername) {
		this.operationUsername = operationUsername;
	}

	public String getOperationPerson() {
		return operationPerson;
	}

	public void setOperationPerson(String operationPerson) {
		this.operationPerson = operationPerson;
	}

	public String getOperationContent() {
		return operationContent;
	}

	public void setOperationContent(String operationContent) {
		this.operationContent = operationContent;
	}

	public String getOperationTimeStart() {
		return operationTimeStart;
	}

	public void setOperationTimeStart(String operationTimeStart) {
		this.operationTimeStart = operationTimeStart;
	}

	public String getOperationTimeEnd() {
		return operationTimeEnd;
	}

	public void setOperationTimeEnd(String operationTimeEnd) {
		this.operationTimeEnd = operationTimeEnd;
	}
}
