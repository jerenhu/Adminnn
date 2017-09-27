package com.ecnice.privilege.dao.privilege;

import com.ecnice.privilege.common.PagerModel;
import com.ecnice.privilege.common.Query;
import com.ecnice.privilege.model.privilege.ICSystem;

import java.util.List;
import java.util.Set;

/**
 * 系统管理Dao
 * 
 * @author Martin.Wang
 * 
 */
public interface IICSystemDao {
	/**
	 * 系统集合
	 * @param systemSns
	 *            系统标识集合
	 * @throws Exception
	 */
	public List<ICSystem> getICSystemsBySns(Set<String> systemSns) throws Exception;
	/**
	 * 添加系统
	 * 
	 * @param icSystem
	 *            系统vo
	 * @throws Exception
	 */
	public void insertIcSystem(ICSystem icSystem) throws Exception;

	/**
	 * 修改系统
	 * 
	 * @param icSystem
	 *            系统vo
	 * @throws Exception
	 */
	public void updateIcSystem(ICSystem icSystem) throws Exception;

	/**
	 * 根据系统id删除系统
	 * 
	 * @param id
	 *            系统主键id
	 * @throws Exception
	 */
	public void deleteIcSystem(String id) throws Exception;

	/**
	 * 获取所有的系统列表
	 * 
	 * @param icSystem
	 *            对象参数，作为查询的条件
	 * @return
	 * @throws Exception
	 */
	public List<ICSystem> getAllIcSystem(ICSystem icSystem) throws Exception;

	/**
	 * 分页获取系统列表
	 * 
	 * @param icSystem
	 *            对象参数，作为查询的条件
	 * @param query
	 *            分页所需的参数(页数和每页显示的条数)
	 * @return
	 * @throws Exception
	 */
	public PagerModel<ICSystem> getPagerIcSystem(ICSystem icSystem, Query query)
			throws Exception;

	/**
	 * 根据id获得系统对象
	 * 
	 * @param id
	 *            系统id
	 * @return
	 * @throws Exception
	 */
	public ICSystem getICSystemById(String id) throws Exception;

	/**
	 * 根据用户id得到用户所有的系统列表
	 * 
	 * @param userId
	 * @return
	 * @Description:
	 */
	public List<ICSystem> getICSystemsByUserId(String userId);

	/**
	 * @param sn
	 * @return
	 * @Description:通过sn查询系统
	 */
	public ICSystem getICSystemBySn(String sn);
}
