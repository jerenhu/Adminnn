package com.ecnice.privilege.dao.privilege.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.ecnice.privilege.common.PagerModel;
import com.ecnice.privilege.common.PrivilegeException;
import com.ecnice.privilege.common.Query;
import com.ecnice.privilege.constant.PrivilegeConstant;
import com.ecnice.privilege.dao.MybatisTemplate;
import com.ecnice.privilege.dao.privilege.IUserDao;
import com.ecnice.privilege.model.privilege.User;
import com.ecnice.privilege.vo.privilege.PrivilegeVo;
import com.ecnice.tools.common.MD5Util;
import com.ecnice.tools.common.UUIDGenerator;

@Repository
public class UserDaoImpl extends MybatisTemplate implements IUserDao {
	private static final Logger logger = Logger.getLogger(UserDaoImpl.class);

	@Override
	public List<User> getSystemUsersBySystemSn(String systemSn) {
		return (List<User>) selectList("UserXML.getSystemUsersBySystemSn", systemSn);
	}

	public static void main(String[] args) {
		System.out.println(MD5Util.getMD5String("iceasy2014888888"));
	}

	@Override
	public User login(String username, String password) throws Exception {
		password = MD5Util.getMD5String(PrivilegeConstant.USER_PASSWORD_FRONT + password);
		Map<String, String> params = new HashMap<String, String>();
		params.put("username", username);
		params.put("password", password);
		return (User) selectOne("UserXML.login", params);
	}

	@Override
	public void insertUser(User user) throws Exception {
		String password = MD5Util.getMD5String(PrivilegeConstant.USER_PASSWORD_FRONT + user.getPassword());
		user.setPassword(password);
		user.setCreateTime(new Date());
		user.setUpdateTime(new Date());
		this.insert("UserXML.insertUser", user);
	}

	@Override
	public void insertUserBatch(List<User> users) throws Exception {
		if(CollectionUtils.isNotEmpty(users)){
			for (User user : users) {
				if (null != user) {
					user.setId(UUIDGenerator.generate());
					String password = MD5Util.getMD5String(PrivilegeConstant.USER_PASSWORD_FRONT + user.getPassword());
					user.setPassword(password);
					user.setCreateTime(new Date());
					user.setUpdateTime(new Date());
					user.setFailMonth(PrivilegeConstant.DEFAULT_FAIL_MONTH);
					user.setPwdInit(0);
				}
			}
			this.insert("UserXML.insertUserBatch", users);
		}
	}
	
	@Override
	public void updateUser(User user) throws Exception {
		user.setCreateTime(null);
		user.setCreator(null);
		user.setUpdateTime(new Date());
		this.update("UserXML.updateUser", user);
	}
	
	@Override
	public int updateUserBatch(List<User> users) throws Exception {
		if(CollectionUtils.isNotEmpty(users)){
			for (User user : users) {
				if (null != user) {
					user.setCreateTime(null);
					user.setCreator(null);
					user.setUpdateTime(new Date());
				}
			}
			return this.update("UserXML.updateUserList", users);
		} else {
			return 0;
		}
	}
	
	@Override
	public int updateSyncUserList(List<User> users) throws Exception {
		if(CollectionUtils.isNotEmpty(users)){
			for (User user : users) {
				if (null != user) {
					user.setUpdateTime(new Date());
				}
			}
			return this.update("UserXML.synUserList", users);
		} else {
			return 0;
		}
	}

	@Override
	public void delUser(String id) throws Exception {
		User user = this.getUserById(id);
		this.delete("UserXML.delUser", id);
	}

	@Override
	public User getUserById(String id) throws Exception {
		return (User) this.selectOne("UserXML.getUserById", id);
	}

	@Override
	public User getUserByUserName(String userName) throws Exception {
		return (User) this.selectOne("UserXML.getUserByUserName", userName);
	}

	@Override
	public PagerModel<User> getPagerModel(User user, Query query) throws Exception {
		return this.getPagerModelByQuery(user, query, "UserXML.PagerModel");
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> getAll(User user) throws Exception {
		return (List<User>) this.selectList("UserXML.getAll", user);
	}

	@Override
	public List<User> getUserByRoleSn(String roleSn) throws Exception {
		return (List<User>) this.selectList("UserXML.getUserByRoleSn", roleSn);
	}

	@Override
	public List<User> getUsersByPrivilegeVo(PrivilegeVo vo) throws Exception {
		return (List<User>) this.selectList("UserXML.getUsersByPrivilegeVo", vo);
	}

	@Override
	public List<User> getUserByDeptIdAndRoleSn(String deptId, String roleSn) throws PrivilegeException {
		Map<String, String> params = new HashMap<String, String>();
		params.put("deptId", deptId);
		params.put("roleSn", roleSn);
		return (List<User>) this.selectList("UserXML.getUserByDeptIdAndRoleSn", params);
	}

	@Override
	public List<User> getUserByRoleSns(String... roleSns) throws PrivilegeException {
		Map<String, Object> map = new HashMap<String, Object>();
		ArrayList<String> list = new ArrayList<String>();
		for (String roleSn : roleSns) {
			list.add(roleSn);
		}
		map.put("list", list);
		return (List<User>) this.selectList("UserXML.getUserByRoleSns", map);
	}

	@Override
	public List<User> getUserByRoleSns(User user, String... roleSns) throws PrivilegeException {
		Map<String, Object> map = new HashMap<String, Object>();
		ArrayList<String> list = new ArrayList<String>();
		for (String roleSn : roleSns) {
			list.add(roleSn);
		}
		map.put("list", list);
		map.put("user", user);
		return (List<User>) this.selectList("UserXML.getUserByRoleSnsByUser", map);
	}

	@Override
	public List<User> getUsersByDeptId(String deptId) throws Exception {
		return (List<User>) this.selectList("UserXML.getUsersByDeptId", deptId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> getUserByUserNames(String... userName) {
		if(ArrayUtils.isNotEmpty(userName)){
			Map<String,Object> params = new HashMap<String, Object>();
			params.put("userNames", userName);
			return (List<User>) this.selectList("UserXML.getUserByUserNames", params);
		}else {
			return null;
		}
	}

	@Override
	public PagerModel<User> getUserFailTime(User user,Query query) {
		return this.getPagerModelByQuery(user, query, "UserXML.getUserFailTime");
	}
	
	@Override
	public List<User> getUserByIdsList(List<String> ids) throws Exception {
		// TODO Auto-generated method stub
		return (List<User>) this.selectList("UserXML.getUserByIdsList", ids);
	}
}
