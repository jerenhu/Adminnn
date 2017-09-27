package com.ecnice.privilege.service.privilege;

import java.util.List;
import java.util.Set;

import com.ecnice.privilege.common.PagerModel;
import com.ecnice.privilege.common.Query;
import com.ecnice.privilege.model.privilege.ACL;
import com.ecnice.privilege.model.privilege.Module;

public interface IModuleService {
	
	/**
	 * 得到系统所有的模块和模块值，当然的也做了每个权限值是否勾选判断
	 * 
	 * @param systemSn 系统标识
	 * @param type role或者user
	 * @param releaseId roleid或者userid
	 * @return
	 * @throws Exception
	 */
	public List<Module> getAllModuleBySystemSnAndReleaseId(String systemSn, String type, String releaseId)
			throws Exception;
	/**
	 * 给模块分配权限值
	 * @param position 第几位
	 * @param moduleId 模块id
	 * @throws Exception
	 */
	public void addPriVal(List<Integer> positions, String moduleId) throws Exception ;

	/**
	 * @param acls
	 * @param systemId
	 * @return
	 * @throws Exception
	 * @Description:通过缓存中的数据得到左边的那颗树
	 */
	public List<Module> getTreeModuleBySystemIdAndAcls(Set<ACL> acls,
			String systemId) throws Exception;

	/**
	 * @param pid
	 * @return
	 * @throws Exception
	 * @Description:判断是否有孩子
	 */
	public boolean checkChildren(String pid) throws Exception;

	/**
	 * @param systemId
	 *            系统id
	 * @return
	 * @throws Exception
	 * @Description:通过系统id得到这个系统的所有模块列表
	 */
	public List<Module> getModulesBySystemId(String systemId) throws Exception;

	/**
	 * 添加模块
	 * 
	 * @param module
	 *            模块vo
	 * @throws Exception
	 */
	public void insertModule(Module module) throws Exception;

	/**
	 * 修改模块
	 * 
	 * @param module
	 *            模块vo
	 * @throws Exception
	 */
	public void updateModule(Module module) throws Exception;

	/**
	 * 根据模块id删除模块
	 * 
	 * @param id
	 *            模块主键id
	 * @throws Exception
	 */
	public void deleteModule(String id) throws Exception;

	/**
	 * 
	 * @param ids
	 * @throws Exception
	 * @Description:批量删除
	 */
	public void deleteModules(String[] ids) throws Exception;
	/**
	 * 删除权限值
	 * @param systemPrivilegeValueId
	 * @param moduleId
	 * @throws Exception
	 */
	public void  deletePriVal(String systemPrivilegeValueId,String moduleId)throws Exception;

	/**
	 * 获取所有的模块列表
	 * 
	 * @param module
	 *            对象参数，作为查询的条件
	 * @return
	 * @throws Exception
	 */
	public List<Module> getAllModule(Module module) throws Exception;
	
	/**
	 * 查询所有模块的权限
	 * @param module
	 * @return
	 * @throws Exception
	 */
	public List<Module> getAllModulePri(Module module) throws Exception;

	/**
	 * 分页获取模块列表
	 * 
	 * @param module
	 *            对象参数，作为查询的条件
	 * @param query
	 *            分页所需的参数(页数和每页显示的条数)
	 * @return
	 * @throws Exception
	 */
	public PagerModel<Module> getPagerModule(Module module, Query query)
			throws Exception;

	/**
	 * 根据id获得模块对象
	 * 
	 * @param id
	 *            模块id
	 * @return
	 * @throws Exception
	 */
	public Module getModuleById(String id) throws Exception;
}
