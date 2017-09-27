package com.ecnice.privilege.dao.privilege;

import java.util.List;

import com.ecnice.privilege.common.PagerModel;
import com.ecnice.privilege.common.Query;
import com.ecnice.privilege.model.privilege.RoleCompany;


/**
 * @Title:角色分配的公司Dao接口
 * @Description:
 * @Author:XTJ
 * @Since:2017-05-09 21:25:08
 * @Version:1.1.0
 * @Copyright:Copyright (c) 浙江蘑菇加电子商务有限公司 2015 ~ 2016 版权所有  
 */
public interface IRoleCompanyDao {

	/**
	 * 通过id得到角色分配的公司RoleCompany
	 * @param id
	 * @return 
	 * @throws Exception
	 * @Description:
	 */
	public RoleCompany getRoleCompanyById(String id) throws Exception;

	/**
	 * 得到所有角色分配的公司RoleCompany
	 * @param roleCompany
	 * @return 
	 * @throws Exception
	 * @Description:
	 */
	public List<RoleCompany> getAll(RoleCompany roleCompany) throws Exception;

	/**
	 * 分页查询角色分配的公司RoleCompany
	 * @param roleCompany
	 * @param query
	 * @return
	 * @throws Exception
	 * @Description:
	 */
	public PagerModel<RoleCompany> getPagerModelByQuery(RoleCompany roleCompany, Query query) throws Exception;

	/**
	 * 添加角色分配的公司RoleCompany
	 * @param roleCompany
	 * @throws Exception
	 * @Description:
	 */
	public void insertRoleCompany(RoleCompany roleCompany) throws Exception;
	
	/**
	 * 通过id删除角色分配的公司RoleCompany
	 * @param id
	 * @throws Exception
	 * @Description:
	 */
	public void delRoleCompanyById(String id) throws Exception;
	
	
	/**
	 * 通过角色 ID和公司ID删除角色管理的公司
	 * @param userId
	 * @param roleId
	 * @return
	 * @throws Exception
	 * @Description:通过用户id和角色 ID删除用户角色
	 * @author xietongjian 2017 上午1:45:31
	 */
	public int deleteRoleCompanyByRoleCompanyId(String cId, String roleId) throws Exception;
	
	/**
	 * 通过id批量删除角色分配的公司RoleCompany
	 * @param ids 如："'1','2','3','4'..."
	 * @throws Exception
	 * @Description:
	 */
	public void delRoleCompanyByIds(String ids) throws Exception;
	
	/**
	 * 通过id修改角色分配的公司RoleCompany
	 * @param roleCompany
	 * @throws Exception
	 * @Description:
	 */
	public void updateRoleCompany(RoleCompany roleCompany) throws Exception;

	/**
	 * 通过ids批量修改角色分配的公司RoleCompany
	 * @param ids 如："'1','2','3','4'..."
	 * @param roleCompany
	 * @throws Exception
	 * @Description:
	 */
	public void updateRoleCompanyByIds(String ids,RoleCompany roleCompany) throws Exception;
	
	
}
