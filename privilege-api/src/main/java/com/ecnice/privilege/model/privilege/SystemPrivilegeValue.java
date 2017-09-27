package com.ecnice.privilege.model.privilege;

import java.io.Serializable;
import java.math.BigInteger;

import com.ecnice.privilege.model.BaseModel;

/**
 * 系统权限值
 * 
 * @Author:Bruce.Liu
 * @Since:2014年7月28日
 * @Version:1.1.0浙江蘑菇加电子商务有限公司 2014 ~ 2015 版权所有
 */
public class SystemPrivilegeValue extends BaseModel implements Serializable {
	private static final long serialVersionUID = 1L;
	// id
	private Integer id;
	// 系统id
	private String systemId;
	// 位
	private Integer position;
	// 权限值名称
	private String name;
	// 排序号
	private Integer orderNo;
	// 备注
	private String remark;
	// 临时变量
	private BigInteger state;
	//模块id  临时变量
	private String moduleId;
	//标记是否选中
	private boolean flag = false;

	public BigInteger getState() {
		BigInteger temp = new BigInteger("1");
		return temp.shiftLeft(position);
	}

	public void setState(BigInteger state) {
		this.state = state;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSystemId() {
		return systemId;
	}

	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getPosition() {
		return position;
	}

	public void setPosition(Integer position) {
		this.position = position;
	}


	public String getModuleId() {
		return moduleId;
	}

	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}
}
