package com.ecnice.tools.vo;

import java.io.Serializable;
import java.util.List;

/**
 * 返回vo
 * @Title:
 * @Description:
 * @Author:wentaoxiang
 * @Since:2016年1月19日 下午5:46:41
 * @Version:1.1.0
 * @Copyright:Copyright (c) 浙江蘑菇加电子商务有限公司 2015 ~ 2016 版权所有
 */
public class ReturnVo<T> implements Serializable {

	private static final long serialVersionUID = -5580228202640516960L;
	// 响应编码
	private Integer code;
	// 响应消息
	private String msg;
	// 返回的vo
	private T data;
	// 返回的list
	private List<T> datas;
	
	public ReturnVo() {
		super();
	}
	
	public ReturnVo(Integer code, String msg) {
		super();
		this.code = code;
		this.msg = msg;
	}

	public ReturnVo(Integer code, String msg, T data, List<T> datas) {
		super();
		this.code = code;
		this.msg = msg;
		this.data = data;
		this.datas = datas;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
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