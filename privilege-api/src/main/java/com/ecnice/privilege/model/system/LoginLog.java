package com.ecnice.privilege.model.system;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Title:
 * @Description:日志
 * @Author:Bruce.Liu
 * @Since:2014年4月8日
 * @Version:1.1.0浙江蘑菇加电子商务有限公司 2014 ~ 2015 版权所有
 */
public class LoginLog implements Serializable {
		
	private static final long serialVersionUID = 1L;
	//id 自增长
	private int id;
	//访问ip
	private String ip;
	//操作人id
	private String operationId;
	//操作人的姓名
	private String operationUsername;
	//操作人姓名
	private String operationPerson;
	//操作内容
	private String operationContent;
	//操作时间
	private Date operationTime = new Date();
	//操作时间 字符串
	private String operationTimeStr;
	public LoginLog(){}
	public LoginLog(String ip, String operationId,
			String operationUsername, String operationPerson,
			String operationContent) {
		this.ip = ip;
		this.operationId = operationId;
		this.operationUsername = operationUsername;
		this.operationPerson = operationPerson;
		this.operationContent = operationContent;
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
	public Date getOperationTime() {
		return operationTime;
	}
	public void setOperationTime(Date operationTime) {
		if (null != operationTime) {
			this.operationTimeStr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(operationTime);
		}
		this.operationTime = operationTime;
	}
	public String getOperationTimeStr() {
		return operationTimeStr;
	}
	public void setOperationTimeStr(String operationTimeStr) {
		this.operationTimeStr = operationTimeStr;
	}
}
