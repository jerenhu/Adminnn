package com.ecnice.privilege.model.system;

import java.io.Serializable;

import com.ecnice.privilege.model.BaseModel;

/**
 * @Comment:系统配置表
 * @author bruce.liu
 * @Create Date 2014年3月26日
 */
@SuppressWarnings("serial")
public class SystemConfig extends BaseModel implements Serializable {
	// id
	private String id;
	// 配置名称
	private String configName;
	// 配置key
	private String configKey;
	// 配置key的value值
	private String configValue;
	//配置标示
	private String configSn;
	//排序
	private int configOrder;
	// 备注
	private String remark;
	//图片
	private byte[] image;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getConfigName() {
		return configName;
	}
	public void setConfigName(String configName) {
		this.configName = configName;
	}
	public String getConfigKey() {
		return configKey;
	}
	public void setConfigKey(String configKey) {
		this.configKey = configKey;
	}
	public String getConfigValue() {
		return configValue;
	}
	public void setConfigValue(String configValue) {
		this.configValue = configValue;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getConfigSn() {
		return configSn;
	}
	public void setConfigSn(String configSn) {
		this.configSn = configSn;
	}
	public int getConfigOrder() {
		return configOrder;
	}
	public void setConfigOrder(int configOrder) {
		this.configOrder = configOrder;
	}
	public byte[] getImage() {
		return image;
	}
	public void setImage(byte[] image) {
		this.image = image;
	}
	
}
