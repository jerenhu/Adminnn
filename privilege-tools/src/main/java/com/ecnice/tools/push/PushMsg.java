package com.ecnice.tools.push;

import java.io.Serializable;

/**
 * 推送消息
 * @Title:
 * @Description:
 * @Author:wentaoxiang
 * @Since:2016年10月14日 上午11:39:53
 * @Version:1.1.0
 * @Copyright:Copyright (c) 浙江蘑菇加电子商务有限公司 2015 ~ 2016 版权所有
 */
public class PushMsg implements Serializable{
	
	/**
	 *
	 */
		
	private static final long serialVersionUID = 1L;

	/**
	 * 消息id
	 */
	private String id;
	
	/**
	 * 消息标题
	 */
	private String title; 
	
	/**
	 * 弹窗消息
	 */
	private String alert;
	
	/**
	 * 消息内容
	 */
	private String msgContent;
	
	/**
	 * 消息类型 @see AnnouncementTypeEnum 
	 */
	private Integer type;
	
	/**
	 * 全流程id
	 */
	private String wpId;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAlert() {
		return alert;
	}

	public void setAlert(String alert) {
		this.alert = alert;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getMsgContent() {
		return msgContent;
	}

	public void setMsgContent(String msgContent) {
		this.msgContent = msgContent;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getWpId() {
		return wpId;
	}

	public void setWpId(String wpId) {
		this.wpId = wpId;
	}

	@Override
	public String toString() {
		return "PushMsg [id=" + id + ", title=" + title + ", alert=" + alert + ", msgContent=" + msgContent + ", type=" + type + ", wpId=" + wpId + "]";
	}
}
