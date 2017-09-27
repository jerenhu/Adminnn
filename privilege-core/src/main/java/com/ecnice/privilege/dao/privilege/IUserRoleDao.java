package com.ecnice.privilege.dao.privilege;

import java.util.List;

import com.ecnice.privilege.model.privilege.Role;
import com.ecnice.privilege.model.privilege.User;
import com.ecnice.privilege.model.privilege.UserRole;
import com.ecnice.privilege.vo.privilege.UserRoleVo;

/**
 * @Title:
 * @Description:用户角色Dao接口
 * @Author:Bruce.Liu
 * @Since:2014年4月1日
 * @Version:1.1.0
浙江蘑菇加电子商务有限公司 2014 ~ 2015 版权所有
 */
public interface IUserRoleDao {

	/**
	 * 得到所有人员公司关系UserRole
	 * @param userRole
	 * @return 
	 * @throws Exception
	 * @Description:
	 */
	public List<UserRole> getAll(UserRole userRole) throws Exception;
	
	/**
	 * @param ur
	 * @throws Exception
	 * @Description:添加用户角色关联
	 */
	public void insertUserRole(UserRole ur) throws Exception;

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
	
	public UserRole getUserRoleById(String id)  throws Exception;

	/**
	 * @param userId
	 * @return
	 * @throws Exception
	 * @Description:通过用户id查询角色列表
	 */
	public List<Role> getRolesByUserId(String userId) throws Exception;
	
	/**
	 * 得到所有的用户和角色关联
	 * @return
	 * @throws Exception
	 */
	public List<UserRoleVo> getAllUserRoleVo() throws Exception;
	
	/**
	 * 通过角色id得到用户列表
	 * @param roleId 角色id
	 * @return
	 * @throws Exception
	 */
	public List<User> getUsersByRoleId(String roleId) throws Exception;

	/**
	 * 同步用户角色
	 * @param userRoles
	 * @return
	 * @throws Exception
	 * @Description:
	 * @author xietongjian 2017 下午8:12:42
	 */
	public int updateSyncUserRoles(List<UserRole> userRoles)throws Exception;

	/**通过用户工号和角色 ID删除用户角色
	 * @param userNo
	 * @param roleId
	 * @return
	 * @throws Exception
	 * @Description:
	 * @author xietongjian 2017 上午1:49:04
	 */
	public int deleteUserRoleByUserRoleId(String userNo, String roleId) throws Exception;
}
