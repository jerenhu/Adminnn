package com.ecnice.privilege.model;

import java.util.Date;

/**
 * @Comment:基本实体类
 * @author liu.wj
 * @Create Date 2013-1-31
 */
public class BaseModel {
	/** 创建时间 **/
	private Date createTime ;
	/** 创建人 **/
	private String creator;
	/** 更新时间  **/
	private Date updateTime;
	/** 更新人 **/
	private String updator;
	/** 删除标识  0标识已删除   1标识未删除 **/
	private Integer delFlag = 1;
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public String getUpdator() {
		return updator;
	}
	public void setUpdator(String updator) {
		this.updator = updator;
	}
	public Integer getDelFlag() {
		return delFlag;
	}
	public void setDelFlag(Integer delFlag) {
		this.delFlag = delFlag;
	}
	
}
