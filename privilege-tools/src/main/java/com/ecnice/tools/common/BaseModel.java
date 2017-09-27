package com.ecnice.tools.common;

import java.util.Date;

/**
 * 基本实体类
 * 
 * @Title:
 * @Description:
 * @Author:wentaoxiang
 * @Since:2016年1月17日 下午2:38:28
 * @Version:1.1.0
 * @Copyright:Copyright (c) 浙江蘑菇加电子商务有限公司 2015 ~ 2016 版权所有
 */
public class BaseModel {
	/** 创建时间 **/
	private Date createTime;
	/** 创建人 **/
	private String creator;
	/** 更新时间 **/
	private Date updateTime;
	/** 更新人 **/
	private String updator;
	/** 删除标识 0标识已删除 1标识未删除 **/
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
