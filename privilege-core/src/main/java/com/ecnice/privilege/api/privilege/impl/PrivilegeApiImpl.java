package com.ecnice.privilege.api.privilege.impl;

import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.ecnice.privilege.api.privilege.IPrivilegeApi;
import com.ecnice.privilege.cache.CacheEntity;
import com.ecnice.privilege.cache.CacheListHandler;
import com.ecnice.privilege.common.PrivilegeException;
import com.ecnice.privilege.common.SessionMap;
import com.ecnice.privilege.constant.PrivilegeConstant;
import com.ecnice.privilege.model.privilege.ACL;
import com.ecnice.privilege.model.privilege.Role;
import com.ecnice.privilege.model.privilege.User;
import com.ecnice.privilege.model.system.SystemConfig;
import com.ecnice.privilege.service.privilege.IAclService;
import com.ecnice.privilege.service.privilege.IDepartmentService;
import com.ecnice.privilege.service.privilege.IUserRoleService;
import com.ecnice.privilege.service.privilege.IUserService;
import com.ecnice.privilege.service.system.IDictionaryService;
import com.ecnice.privilege.service.system.ISystemConfigService;
import com.ecnice.privilege.utils.JsonUtils;
import com.ecnice.privilege.vo.ReturnVo;
import com.ecnice.privilege.vo.privilege.HasPermissionVo;
import com.ecnice.privilege.vo.privilege.LoginVo;
import com.ecnice.privilege.vo.privilege.UserAcls;
import com.ecnice.tools.common.ReadProperty;
import com.google.gson.reflect.TypeToken;

@Component
public class PrivilegeApiImpl implements IPrivilegeApi {
	
	private static final Logger logger = Logger.getLogger(PrivilegeApiImpl.class);

	@Resource
	private ReadProperty readProperty;
	@Resource
	private IAclService aclService;
	@Resource
	private IUserService userService;
	@Resource
	private IUserRoleService userRoleService;
	@Resource
	private IDepartmentService departmentService;
	@Resource
	private ISystemConfigService systemConfigService;
	@Resource
	private IDictionaryService dictionaryService;

	@Override
	public SystemConfig findSystemConfigByKey(String key)
			throws PrivilegeException {
		try {
			return systemConfigService.findSystemConfigByKey(key);
		} catch (Exception e) {
			e.printStackTrace();
			throw new PrivilegeException("得到数据出错", e);
		}

	}

	@Override
	public LoginVo login(String username, String password, String companyId,
			String ip, String systemSn) throws PrivilegeException {
		try {
			return userService.login(username, password, companyId, ip,
					systemSn);
		} catch (Exception e) {
			e.printStackTrace();
			throw new PrivilegeException("登录出错", e);
		}
	}

	@Override
	public List<Role> getRolesByUserId(String userId) throws PrivilegeException {
		try {
			return this.userRoleService.getRolesByUserId(userId);
		} catch (Exception e) {
			e.printStackTrace();
			throw new PrivilegeException("通过用户id得到用户角色列表失败", e);
		}
	}
	
	@Override
	public List<User> getUserByUserNames(String... userName) {
		try {
			return this.userService.getUserByUserNames(userName);
		} catch (Exception e) {
			e.printStackTrace();
			throw new PrivilegeException("通过用户名得到用户信息失败", e);
		}
	}

	@Override
	public User getUserById(String id) {
		try {
			if (StringUtils.isBlank(id)) {
				return null;
			}
			User user = this.userService.getUserById(id.trim());
			if (null != user) {
				user.setPassword(null);
			}
			return user;
		} catch (Exception e) {
			e.printStackTrace();
			throw new PrivilegeException("通过用户id查询用户信息失败", e);
		}
	}

	@Override
	public ReturnVo<HasPermissionVo> hasPermission(String sessionId, String systemSn, String moduleSn, Integer permission) {
		ReturnVo<HasPermissionVo> returnVo = new ReturnVo<HasPermissionVo>();
		HasPermissionVo vo = new HasPermissionVo();
		String sysUrl=readProperty.getValue("privilege.system.url");
		if (StringUtils.isNotBlank(sysUrl)) {
			vo.setUrl(sysUrl.trim() + "/exception.jsp");
		}
		returnVo.setData(vo);
		returnVo.setStatus(PrivilegeConstant.ERROR_CODE);
		returnVo.setMessage("没有权限");
		
		boolean falg=false;
		try {
			falg = aclService.hasPermission(sessionId, systemSn, moduleSn, permission);
		} catch (Exception e) {
			logger.error("PrivilegeApiImpl-hasPermission方法报错",e);
			e.printStackTrace();
			
			returnVo.setStatus(PrivilegeConstant.EXCEPTION_CODE);
			returnVo.setMessage("权限判断异常，请联系接口提供方");
			return returnVo;
		}
		if (falg) {
			returnVo.setStatus(PrivilegeConstant.SUCCESS_CODE);
			returnVo.setMessage("有权限");
		}
		return returnVo;
	}
	
	public ReturnVo<List<String>> getCompanyIdsByRolesAndUser(String sessionId,List<String> roleList){
		ReturnVo<List<String>> ret = new ReturnVo<List<String>>();
		ret.setStatus(PrivilegeConstant.ERROR_CODE);
		ret.setMessage("查询失败");
		try {
			if(StringUtils.isBlank(sessionId)){
				ret.setMessage("sessionId不能为空");
				return ret;
			}
			if(CollectionUtils.isEmpty(roleList)){
				ret.setMessage("数据角色不能为空不能为空");
				return ret;
			}
			List<String> companyIds = this.aclService.getCompanyIdsByRolesAndUser(sessionId, roleList);
			ret.setData(companyIds);
			ret.setStatus(PrivilegeConstant.SUCCESS_CODE);
			ret.setMessage("查询成功");
		}catch (Exception e) {
			logger.error("PrivilegeApiImpl-getCompanyIdsByRolesAndUser方法报错",e);
			e.printStackTrace();
		}
		return ret;
	}

	@Override
	public UserAcls getUserAclsBySessionId(String sessionId) {
		CacheEntity ce = CacheListHandler.getCache(sessionId);
		if (ce == null)
			return null;
		SessionMap sessionMap = (SessionMap) ce.getCacheContext();
		UserAcls userAcls = new UserAcls();

		String creationTime = sessionMap.get("creationTime") == null ? null : String.valueOf(sessionMap.get("creationTime"));
		String userJson = (String) sessionMap.get(PrivilegeConstant.LOGIN_USER);
		String aclJson = (String) sessionMap.get(PrivilegeConstant.LOGIN_USER_ACLS);
		Type type = new TypeToken<HashSet<ACL>>() {
		}.getType();
		Set<ACL> acls = JsonUtils.getGson().fromJson(aclJson, type);
		userAcls.setAcls(acls);
		userAcls.setCreationTime(creationTime);
		User user = (User) JsonUtils.jsonToObj(userJson, User.class);
		userAcls.setLoginUser(user);
		userAcls.setSessionId(sessionId);
		return userAcls;
	}


	@Override
	public List<User> getUserByRoleSns(String... roleSns)
			throws PrivilegeException {
		try {
			return userService.getUserByRoleSns(roleSns);
		} catch (Exception e) {
			e.printStackTrace();
			throw new PrivilegeException("通过角色标示得到用户列表失败", e);
		}
	}

	@Override
	public void checkFailUserJob() {
		try {
			this.userService.checkFailUser();
		} catch (Exception e) {
			logger.error("PrivilegeApiImpl-checkFailUser:检查用户账户和密码是否过期失败", e);
			e.printStackTrace();
			throw new PrivilegeException("检查用户账户和密码是否过期失败", e);
		}
	}
}
