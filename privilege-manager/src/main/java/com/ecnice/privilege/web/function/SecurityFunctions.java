package com.ecnice.privilege.web.function;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;

import com.ecnice.privilege.service.privilege.IAclService;

@Controller("securityFunctions")
public class SecurityFunctions {
	private static IAclService aclService;

	/**
	 * @param acls 用户所有的权限列表
	 * @param systemSn 系统标示
	 * @param moduleSn 模块标示
	 * @param permission 权限值
	 * @return
	 * @Description: 通过上面的值判断是否有该权限
	 */
	public static boolean hasPermission(String sessionId, String systemSn,
			String moduleSn, Integer permission) {
		boolean flag = false;
		if(StringUtils.isNotBlank(sessionId)) {
			flag = aclService.hasPermission(sessionId, systemSn, moduleSn,
					permission);
		}
		return flag;
	}

	@Resource(name = "aclServiceImpl")
	public void setAclService(IAclService aclService) {
		SecurityFunctions.aclService = aclService;
	}
	
}