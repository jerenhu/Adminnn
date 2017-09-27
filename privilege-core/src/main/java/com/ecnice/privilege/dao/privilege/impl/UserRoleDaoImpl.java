package com.ecnice.privilege.dao.privilege.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.ecnice.privilege.dao.MybatisTemplate;
import com.ecnice.privilege.dao.privilege.IRoleDao;
import com.ecnice.privilege.dao.privilege.IUserDao;
import com.ecnice.privilege.dao.privilege.IUserRoleDao;
import com.ecnice.privilege.model.privilege.Role;
import com.ecnice.privilege.model.privilege.User;
import com.ecnice.privilege.model.privilege.UserRole;
import com.ecnice.privilege.vo.privilege.UserRoleVo;

/**
 * @Title:
 * @Description:用户角色dao实现类
 * @Author:Bruce.Liu
 * @Since:2014年4月1日
 * @Version:1.1.0 浙江蘑菇加电子商务有限公司 2014 ~ 2015 版权所有
 */
@Repository
public class UserRoleDaoImpl extends MybatisTemplate implements IUserRoleDao {
	private static final Logger logger = Logger.getLogger(UserRoleDaoImpl.class);

	@Resource
	private IUserDao userDao;
	@Resource
	private IRoleDao roleDao;

	@SuppressWarnings("unchecked")
	@Override
	public List<UserRole> getAll(UserRole userRole) throws Exception {
		return (List<UserRole>) this.selectList("UserRoleXML.getAll", userRole);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<User> getUsersByRoleId(String roleId) throws Exception {
		return (List<User>) selectList("UserRoleXML.getUsersByRoleId", roleId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Role> getRolesByUserId(String userId) throws Exception {
		return (List<Role>) this.selectList("UserRoleXML.getRolesByUserId", userId);
	}

	@Override
	public void insertUserRole(UserRole ur) throws Exception {
		//User user = userDao.getUserById(ur.getUserId());
//		Role role = roleDao.getRoleById(ur.getRoleId());
		this.insert("UserRoleXML.insertUserRole", ur);

//		List<Role> roles = new ArrayList<Role>();
//		roles.add(role);
	}

	@Override
	public void delUserRole(String id) throws Exception {
		this.delete("UserRoleXML.delUserRole", id);
	}

	@Override
	public void delUserRoleByUserId(String userId) throws Exception {
		this.delete("UserRoleXML.delUserRoleByUserId", userId);
		//User user = userDao.getUserById(userId);
	}

	@Override
	public UserRole getUserRoleById(String id) throws Exception {
		return (UserRole) this.selectOne("UserRoleXML.getUserRoleById", id);
	}

	@Override
	public void delUserRoleByRoleId(String roleId) throws Exception {
		this.delete("UserRoleXML.delUserRoleByRoleId", roleId);

		//Role role = roleDao.getRoleById(roleId);
	}

	@Override
	public List<UserRoleVo> getAllUserRoleVo() throws Exception {
		return (List<UserRoleVo>) this.selectList("UserRoleXML.getAllUserRoleVo");
	}

	@Override
	public int updateSyncUserRoles(List<UserRole> userRoles) throws Exception {
		if(CollectionUtils.isNotEmpty(userRoles)){
			return this.update("UserRoleXML.synUserRoleList", userRoles);
		}else{
			return 0;
		}
	}

	@Override
	public int deleteUserRoleByUserRoleId(String userNo, String roleId) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userNo", userNo);
		map.put("roleId", roleId);
		return this.delete("UserRoleXML.deleteUserRoleByUserRoleId", map);
	}

}
