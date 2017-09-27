package com.ecnice.privilege.dao.privilege;

import java.util.List;

import com.ecnice.privilege.common.PagerModel;
import com.ecnice.privilege.common.Query;
import com.ecnice.privilege.model.privilege.Role;

/**
 * @Title:
 * @Description:角色Dao
 * @Author:Bruce.Liu
 * @Since:2014年3月31日
 * @Version:1.1.0
浙江蘑菇加电子商务有限公司 2014 ~ 2015 版权所有
 */
public interface IRoleDao {

	/**
	 * @param userId
	 * @return
	 * @throws Exception
	 * @Description:通过用户id得到这个用户的角色列表
	 */
	public List<Role> getRolesByUserId(String userId) throws Exception;

	/**
	 * @param Role
	 * @throws Exception
	 * @Description:添加角色
	 */
	public void insertRole(Role role) throws Exception;

	/**
	 * @param Role
	 * @throws Exception
	 * @Description:更新角色
	 */
	public void updateRole(Role role) throws Exception;

	/**
	 * @param id
	 * @throws Exception
	 * @Description:删除角色
	 */
	public void delRole(String id) throws Exception;

	/**
	 * @param id
	 * @return
	 * @throws Exception
	 * @Description:根据id查询角色对象
	 */
	public Role getRoleById(String id) throws Exception;

	/**
	 * @param Role
	 * @param query
	 * @return
	 * @throws Exception
	 * @Description: 分页查询角色列表
	 */
	public PagerModel<Role> getPagerModel(Role role, Query query)
			throws Exception;
	
	/**
	 * @param Role
	 * @param query
	 * @return
	 * @throws Exception
	 * @Description: 分页查询角色列表
	 */
	public PagerModel<Role> getPagerModelByQuery(Role role, Query query)
			throws Exception;
	
	/**
	 * @param Role
	 * @param query
	 * @return
	 * @throws Exception
	 * @Description: 分页查询角色列表
	 */
	public PagerModel<Role> getPagerModelByQuery4Api(Role role, Query query)
			throws Exception;

	/**
	 * 
	 * @param role
	 * @return
	 * @throws Exception
	 * @Description:获取所有角色列表
	 */
	public List<Role> getAll(Role role) throws Exception;

	/**
	 * 批量插入数据，批量同步数据到数据表中。
	 * @param roles
	 * @throws Exception
	 * @Description:
	 * @author xietongjian 2017 下午4:59:01
	 */
	public int updateSyncRoles(List<Role> roles) throws Exception;
}
