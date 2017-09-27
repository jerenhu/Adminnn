package com.ecnice.privilege.service.privilege.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.ecnice.privilege.dao.privilege.IUserRoleDao;
import com.ecnice.privilege.model.privilege.Role;
import com.ecnice.privilege.model.privilege.UserRole;
import com.ecnice.privilege.service.privilege.IUserRoleService;
import com.ecnice.privilege.vo.privilege.UserRoleVo;
import com.ecnice.tools.common.UUIDGenerator;

/**
 * 
 * @Description:用户角色
 * @Author:Martin.Wang
 * @Since:2014-4-2
 * @Version:1.1.0
浙江蘑菇加电子商务有限公司 2014 ~ 2015 版权所有
 */
@Service
public class UserRoleServiceImpl implements IUserRoleService {
	@Resource
	private IUserRoleDao userRoleDao;
	
	@Override
	public List<UserRoleVo> getAllUserRoleVo() throws Exception {
		return userRoleDao.getAllUserRoleVo();
	}
	@Override
	public void insertUserRole(UserRole ur) throws Exception {
		ur.setCreateTime(new Date());
		ur.setUpdateTime(new Date());
		this.userRoleDao.insertUserRole(ur);
	}
	/*
	 * (non-Javadoc)
	 * @see com.ecnice.privilege.service.privilege.IUserRoleService#insertUserRoles(java.lang.String[], java.lang.String)
	 */
	@Override
	public void insertUserRoles(String[] roleIds, String userId, String userNo, String updator)
			throws Exception {
		this.userRoleDao.delUserRoleByUserId(userId);
		for(String roleId:roleIds){
			UserRole userRole=new UserRole();
			userRole.setId(UUIDGenerator.generate());
			userRole.setUserId(userId);
			userRole.setUserNo(userNo);
			userRole.setRoleId(roleId);
			userRole.setCreator(updator);
			userRole.setUpdator(updator);
			this.insertUserRole(userRole);
		}
	}
	
	@Override
	public void insertUserRoles(String userId, String userNo, String updator) throws Exception {
		this.userRoleDao.delUserRoleByUserId(userId);
	}
	
	@Override
	public void delUserRole(String id) throws Exception {
		this.userRoleDao.delUserRole(id);
	}

	@Override
	public void delUserRoleByUserId(String userId) throws Exception {
		this.userRoleDao.delUserRoleByUserId(userId);
	}

	@Override
	public void delUserRoleByRoleId(String roleId) throws Exception {
		this.userRoleDao.delUserRoleByRoleId(roleId);
	}

	@Override
	public List<Role> getRolesByUserId(String userId) throws Exception {
		return this.userRoleDao.getRolesByUserId(userId);

	}
	
	@Override
	public int deleteUserRoleByUserRoleId(String userNo, String roleId) throws Exception {
		if(StringUtils.isNotBlank(userNo) && StringUtils.isNotBlank(roleId)){
			return this.userRoleDao.deleteUserRoleByUserRoleId(userNo, roleId);
		}
		return 0;
	}
	@Override
	public List<UserRole> getAll(UserRole userRole) throws Exception {
		return null != userRole ? this.userRoleDao.getAll(userRole) : null;
	}
}
