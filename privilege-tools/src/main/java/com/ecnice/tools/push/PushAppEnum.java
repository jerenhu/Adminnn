package com.ecnice.tools.push;

/**
 * 推送的app应用
 * 
 * @Title:
 * @Description:
 * @Author:wentaoxiang
 * @Since:2016年11月8日 下午3:24:54
 * @Version:1.1.0
 * @Copyright:Copyright (c) 浙江蘑菇加电子商务有限公司 2015 ~ 2016 版权所有
 */
public enum PushAppEnum {
	B(1, "B端App"), C(2, "C端App");

	private PushAppEnum(Integer code, String name) {
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

	public static PushAppEnum getEnumByCode(Integer code) {
		for (PushAppEnum enm : PushAppEnum.values()) {
			if (enm.getCode().intValue() == code.intValue()) {
				return enm;
			}
		}
		return null;
	}
	
}
