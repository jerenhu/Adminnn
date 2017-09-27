package com.ecnice.privilege.vo;

import java.io.Serializable;

/**
 * @Title:
 * @Description:返回vo
 * @Author:Bruce.Liu
 * @Since:2014年4月1日
 * @Version:1.1.0浙江蘑菇加电子商务有限公司 2014 ~ 2015 版权所有
 */
public class SimpleReturnVo implements Serializable{
	private static final long serialVersionUID = -5799849092170512473L;
	// 返回编码
	private Integer responseCode;
	// 返回的信息
	private String responseMsg;

	public SimpleReturnVo() {

	}

	public SimpleReturnVo(Integer responseCode, String responseMsg) {
		this.responseCode = responseCode;
		this.responseMsg = responseMsg;
	}

	public Integer getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(Integer responseCode) {
		this.responseCode = responseCode;
	}

	public String getResponseMsg() {
		return responseMsg;
	}

	public void setResponseMsg(String responseMsg) {
		this.responseMsg = responseMsg;
	}

}
