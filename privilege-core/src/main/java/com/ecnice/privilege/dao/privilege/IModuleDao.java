package com.ecnice.privilege.dao.privilege;

import java.util.List;

import com.ecnice.privilege.common.PagerModel;
import com.ecnice.privilege.common.Query;
import com.ecnice.privilege.model.privilege.Module;

/**
 * 模块管理Dao
 * 
 * @author Martin.Wang
 * 
 */
public interface IModuleDao {
	
	/**
	 * 通过系统标识来得到模块列表
	 * @param systemSn
	 * @return
	 * @throws Exception
	 */
	public List<Module> getModulesBySystemSn(String systemSn) throws Exception;

	/**
	 * @param moduleIds
	 * @return
	 * @throws Exception
	 * @Description:通过模块id列表查询模块列表
	 */
	public List<Module> getModulesByIds(String moduleIds,String systemId) throws Exception;

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
	 * 获取所有的模块列表
	 * 
	 * @param module
	 *            对象参数，作为查询的条件
	 * @return
	 * @throws Exception
	 */
	public List<Module> getAllModule(Module module) throws Exception;

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

	/**
	 * 
	 * @param pid
	 * @return
	 * @Description:判断是否有孩子
	 */
	public boolean checkChildren(String pid);
}
