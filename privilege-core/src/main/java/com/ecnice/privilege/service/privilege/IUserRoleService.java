package com.ecnice.privilege.service.privilege;

import java.util.List;

import com.ecnice.privilege.model.privilege.Role;
import com.ecnice.privilege.model.privilege.UserRole;
import com.ecnice.privilege.vo.privilege.UserRoleVo;

/**
 * 
 * @Title:
 * @Description:用户角色
 * @Author:Martin.Wang
 * @Since:2014-4-2
 * @Version:1.1.0
浙江蘑菇加电子商务有限公司 2014 ~ 2015 版权所有
 */
public interface IUserRoleService {
	
	public List<UserRoleVo> getAllUserRoleVo() throws Exception;
	
	/**
	 * 根据条件获取所有用户角色
	 * @param userRole
	 * @return
	 * @throws Exception
	 * @Description:
	 * @author xietongjian 2017 下午1:02:42
	 */
	public List<UserRole> getAll(UserRole userRole) throws Exception;
	
	/**
	 * @param ur
	 * @throws Exception
	 * @Description:添加用户角色关联
	 */
	public void insertUserRole(UserRole ur) throws Exception;

	/**
	 * 
	 * @param roleIds
	 * @param userId
	 * @throws Exception
	 * @Description:批量添加用户角色
	 */
	public void insertUserRoles(String[] roleIds, String userId, String userNo, String updator)
			throws Exception;
	
	public void insertUserRoles(String userId, String userNo, String updator) throws Exception;

	/**
	 * @param id
	 * @throws Exception
	 * @Description:删除用户角色管理通过id
	 */
	public void delUserRole(String id) throws Exception;

	/**
	 * @param userId
	 * @throws Exception
	 * @Description:通过用户id删除用户角色
	 */
	public void delUserRoleByUserId(String userId) throws Exception;

	/**
	 * @param roleId
	 * @throws Exception
	 * @Description:通过角色id删除用户角色
	 */
	public void delUserRoleByRoleId(String roleId) throws Exception;

	/**
	 * @param userId
	 * @return
	 * @throws Exception
	 * @Description:通过用户id查询角色列表
	 */
	public List<Role> getRolesByUserId(String userId) throws Exception;
	
	/**
	 * 通过用户工号和角色 ID删除用户角色
	 * @param userId
	 * @param roleId
	 * @return
	 * @throws Exception
	 * @Description:通过用户id和角色 ID删除用户角色
	 * @author xietongjian 2017 上午1:45:31
	 */
	public int deleteUserRoleByUserRoleId(String userNo, String roleId) throws Exception;
	
}
