package com.ecnice.privilege.model.system;

import java.io.Serializable;

/**
 * @Title:
 * @Description:数据字典
 * @Author:Bruce.Liu
 * @Since:2014年4月8日
 * @Version:1.1.0浙江蘑菇加电子商务有限公司 2014 ~ 2015 版权所有
 */
@SuppressWarnings("serial")
public class Dictionary implements Serializable {
	//id 自增长
	private Integer id;
	//编码
	private String code;
	//名称
	private String name;
	//父编码
	private String pcode;
	//系统标识
	private String systemSn;
	//标识
	private String sn;
	//排序号
	private Integer orderNo;
	//easyUI树结构父节点
	private String _parentId;
	
	public String get_parentId() {
		return _parentId;
	}
	public void set_parentId(String _parentId) {
		this._parentId = _parentId;
	}
	public Integer getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}
	public String getSn() {
		return sn;
	}
	public void setSn(String sn) {
		this.sn = sn;
	}
	public String getSystemSn() {
		return systemSn;
	}
	public void setSystemSn(String systemSn) {
		this.systemSn = systemSn;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPcode() {
		return pcode;
	}
	public void setPcode(String pcode) {
		this.pcode = pcode;
		this._parentId = pcode;
	}
	
}
