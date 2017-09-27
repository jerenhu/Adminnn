package com.ecnice.privilege.dao.privilege;

import java.util.List;

import com.ecnice.privilege.common.PagerModel;
import com.ecnice.privilege.common.Query;
import com.ecnice.privilege.model.privilege.SystemPrivilegeValue;

/**
 * @author martin.wang
 * @date 2014-07-28 11:34:08
 */
public interface ISystemPrivilegeValueDao {
	
	/**
	 * 得到系统的权限值
	 * @param systemSn
	 * @return
	 * @throws Exception
	 */
	public List<SystemPrivilegeValue> getSystemPrivilegeValuesBySystemSn(String systemSn) throws Exception;
	/**
	 */
	public SystemPrivilegeValue getSystemPrivilegeValueById(String id) throws Exception;
	/**
	 */
	public List<SystemPrivilegeValue> getAll(SystemPrivilegeValue systemPrivilegeValue) throws Exception;
	
	public List<SystemPrivilegeValue> getPval(String systemId) throws Exception;
	/**
	 */
	public PagerModel<SystemPrivilegeValue> getPagerModelByQuery(SystemPrivilegeValue systemPrivilegeValue, Query query) throws Exception;

	/**
	 */
	public void insertSystemPrivilegeValue(SystemPrivilegeValue systemPrivilegeValue) throws Exception;

	/**
	 */
	public void delSystemPrivilegeValueById(String id) throws Exception;

	/**
	 */
	public void updateSystemPrivilegeValue(SystemPrivilegeValue systemPrivilegeValue) throws Exception;
	
	public void initPval(String systemId)throws Exception;
}
