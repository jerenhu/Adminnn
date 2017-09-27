package com.ecnice.tools.push;

/**
 * 系统类型枚举
 * 
 * @Title:
 * @Description:
 * @Author:wentaoxiang
 * @Since:2016年10月17日 上午11:10:44
 * @Version:1.1.0
 * @Copyright:Copyright (c) 浙江蘑菇加电子商务有限公司 2015 ~ 2016 版权所有
 */
public enum SysTypeEnum {
	IOS(10, "IOS"), ANDROID(20, "Android"), WINPHONE(30, "WinPhone");

	private Integer code;
	private String name;

	private SysTypeEnum(Integer code, String name) {
		this.code = code;
		this.name = name;
	}

	public Integer getCode() {
		return code;
	}

	public String getName() {
		return name;
	}
	
	/**
	 * 通过code得到枚举
	 * @param code
	 * @return
	 * @Description:
	 * @author wentaoxiang 2016年10月21日 上午11:39:38
	 */
	public static SysTypeEnum getEnumByCode(Integer code) {
		for (SysTypeEnum c : SysTypeEnum.values()) {
			if (c.getCode().equals(code)) {
				return c;
			}
		}
		return null;
	}

}
