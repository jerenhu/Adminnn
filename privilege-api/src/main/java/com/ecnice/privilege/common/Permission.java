package com.ecnice.privilege.common;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Permission {
	//系统标示
	String systemSn();
	//模块标示
	String moduleSn();
	//权限值
	int value();
}
