package com.ecnice.tools.push;

/**
 * 推送参数
 * 
 * @Title:
 * @Description:
 * @Author:wentaoxiang
 * @Since:2016年11月8日 下午3:23:25
 * @Version:1.1.0
 * @Copyright:Copyright (c) 浙江蘑菇加电子商务有限公司 2015 ~ 2016 版权所有
 */
public class PushParam {

	/** 推送类型 */
	private PushTypeEnum pushTypeEnum;
	/** 推送的app */
	private PushAppEnum pushAppEnum;

	public PushParam(PushTypeEnum pushTypeEnum, PushAppEnum pushAppEnum) {
		super();
		this.pushTypeEnum = pushTypeEnum;
		this.pushAppEnum = pushAppEnum;
	}

	public PushTypeEnum getPushTypeEnum() {
		return pushTypeEnum;
	}

	public void setPushTypeEnum(PushTypeEnum pushTypeEnum) {
		this.pushTypeEnum = pushTypeEnum;
	}

	public PushAppEnum getPushAppEnum() {
		return pushAppEnum;
	}

	public void setPushAppEnum(PushAppEnum pushAppEnum) {
		this.pushAppEnum = pushAppEnum;
	}

	@Override
	public String toString() {
		return "PushParam [pushTypeEnum=" + pushTypeEnum + ", pushAppEnum=" + pushAppEnum + "]";
	}

}
