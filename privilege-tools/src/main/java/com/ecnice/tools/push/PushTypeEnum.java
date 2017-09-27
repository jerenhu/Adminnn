package com.ecnice.tools.push;

/**
 * 推送类型枚举
 * 
 * @Title:
 * @Description:
 * @Author:wentaoxiang
 * @Since:2016年10月14日 下午3:39:08
 * @Version:1.1.0
 * @Copyright:Copyright (c) 浙江蘑菇加电子商务有限公司 2015 ~ 2016 版权所有
 */
public enum PushTypeEnum {
	JPUSH(10, "极光推送");

	private PushTypeEnum(Integer code, String name) {
		this.code = code;
		this.name = name;
	}

	private Integer code;
	private String name;

	public Integer getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

}
