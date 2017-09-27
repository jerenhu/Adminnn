package com.ecnice.privilege.service.privilege;

import java.util.List;

import com.ecnice.privilege.model.privilege.RoleSystem;
import com.ecnice.tools.pager.PagerModel;
import com.ecnice.tools.pager.Query;




/**
 * 延时短信Service接口
 * @author bruce.liu
 * @date 2016-12-23 16:53:22
 */
public interface IRoleSystemService {
	
	/**
	 * 更新角色系统
	 * @param roleId
	 * @param systemIds
	 * @return
	 * @throws Exception
	 */
	public boolean updateRoleSystemByRoleId(String roleId,List<String> systemIds) throws Exception;
	
	/**
	 * 通过角色的id获取角色的系统
	 * @param roleId
	 * @return
	 * @throws Exception
	 */
	public List<RoleSystem> getRoleSystemsByRoleId(String roleId) throws Exception;

	/**
	 * 通过id得到延时短信RoleSystem
	 * @param id
	 * @return 
	 * @throws Exception
	 * @Description:
	 */
	public RoleSystem getRoleSystemById(String id) throws Exception;

	/**
	 * 通过ids批量得到延时短信RoleSystem
	 * @param ids 如："'1','2','3','4'..."
	 * @return 
	 * @throws Exception
	 * @Description:
	 */
	public List<RoleSystem> getRoleSystemByIds(String ids) throws Exception;
	
	/**
	 * 通过ids批量得到延时短信RoleSystem
	 * @param ids 
	 * @return 
	 * @throws Exception
	 * @Description:
	 */
	public List<RoleSystem> getRoleSystemByIdsList(List<String> ids) throws Exception;

	/**
	 * 得到所有延时短信RoleSystem
	 * @param RoleSystem
	 * @return 
	 * @throws Exception
	 * @Description:
	 */
	public List<RoleSystem> getAll(RoleSystem RoleSystem) throws Exception;

	/**
	 * 分页查询延时短信RoleSystem
	 * @param RoleSystem
	 * @param query
	 * @return
	 * @throws Exception
	 * @Description:
	 */
	public PagerModel<RoleSystem> getPagerModelByQuery(RoleSystem RoleSystem, Query query) throws Exception;

	/**
	 * 查询记录数
	 * @param RoleSystem
	 * @return
	 * @throws Exception
	 * @Description:
	 */
	public int getByPageCount(RoleSystem RoleSystem)throws Exception ;
	
	/**
	 * 添加延时短信RoleSystem
	 * @param RoleSystem
	 * @throws Exception
	 * @Description:
	 */
	public void insertRoleSystem(RoleSystem RoleSystem) throws Exception;
	
	/**
	 * 批量添加延时短信RoleSystem
	 * @param RoleSystems
	 * @throws Exception
	 * @Description:
	 */
	public void insertRoleSystemBatch(List<RoleSystem> RoleSystems) throws Exception;

	/**
	 * 通过id删除延时短信RoleSystem
	 * @param id
	 * @throws Exception
	 * @Description:
	 */
	public void delRoleSystemById(String id) throws Exception;

	/**
	 * 通过id批量删除延时短信RoleSystem
	 * @param ids 如："'1','2','3','4'..."
	 * @throws Exception
	 * @Description:
	 */
	public void delRoleSystemByIds(String ids) throws Exception;
	
	/**
	 * 通过id批量删除延时短信RoleSystem
	 * @param ids 
	 * @throws Exception
	 * @Description:
	 */
	public void delRoleSystemByIdsList(List<String> ids) throws Exception;

	/**
	 * 通过id修改延时短信RoleSystem
	 * @param RoleSystem
	 * @throws Exception
	 * @Description:
	 */
	public int updateRoleSystem(RoleSystem RoleSystem) throws Exception;
	
	/**
	 * 通过ids批量修改延时短信RoleSystem
	 * @param ids 
	 * @param RoleSystem
	 * @throws Exception
	 * @Description:
	 */
	public int updateRoleSystemByIdsList(List<String> ids,RoleSystem RoleSystem) throws Exception;
	
	/**
	 * 通过id批量修改延时短信RoleSystem
	 * @param RoleSystems
	 * @throws Exception
	 * @Description:
	 */
	public int updateRoleSystemList(List<RoleSystem> RoleSystems) throws Exception;
}
