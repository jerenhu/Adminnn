package com.ecnice.privilege.vo;

import java.io.Serializable;
import java.util.List;

public class ReturnVo<T> implements Serializable{
	private static final long serialVersionUID = 4111085789958984329L;
	//状态
	private String status;
	//信息
	private String message;
	//单个对象
	private T data;
	//多个对象
	private List<T> datas;
	
	public ReturnVo() {
		super();
	}
	
	public ReturnVo(String status, String message) {
		super();
		this.status = status;
		this.message = message;
	}
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
	public List<T> getDatas() {
		return datas;
	}
	public void setDatas(List<T> datas) {
		this.datas = datas;
	}
	
}
