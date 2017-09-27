package com.ecnice.privilege.dao.privilege.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ecnice.privilege.common.PagerModel;
import com.ecnice.privilege.common.Query;
import com.ecnice.privilege.dao.MybatisTemplate;
import com.ecnice.privilege.dao.privilege.ISystemPrivilegeValueDao;
import com.ecnice.privilege.model.privilege.SystemPrivilegeValue;

/**
 * @author martin.wang
 * @date 2014-07-28 11:34:08
 */
@Repository
public class SystemPrivilegeValueDaoImpl extends MybatisTemplate implements ISystemPrivilegeValueDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<SystemPrivilegeValue> getSystemPrivilegeValuesBySystemSn(String systemSn) throws Exception {
		return (List<SystemPrivilegeValue>) this.selectList("SystemPrivilegeValueXML.getSystemPrivilegeValuesBySystemSn", systemSn);
	}

	@Override
	public SystemPrivilegeValue getSystemPrivilegeValueById(String id) throws Exception {
		return (SystemPrivilegeValue)this.selectOne("SystemPrivilegeValueXML.getSystemPrivilegeValueById", id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SystemPrivilegeValue> getAll(SystemPrivilegeValue systemPrivilegeValue) throws Exception {
		return (List<SystemPrivilegeValue>)this.selectList("SystemPrivilegeValueXML.getAll", systemPrivilegeValue);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public java.util.List<SystemPrivilegeValue> getPval(String systemId) throws Exception {
		SystemPrivilegeValue systemPrivilegeValue=new SystemPrivilegeValue();
		systemPrivilegeValue.setSystemId(systemId);
		return (List<SystemPrivilegeValue>)this.selectList("SystemPrivilegeValueXML.getAll", systemPrivilegeValue);
	}

	@Override
	public PagerModel<SystemPrivilegeValue> getPagerModelByQuery(SystemPrivilegeValue systemPrivilegeValue, Query query)
			throws Exception {
		return this.getPagerModelByQuery(systemPrivilegeValue, query, "SystemPrivilegeValueXML.getPagerModelByQuery");
	}

	@Override
	public void insertSystemPrivilegeValue(SystemPrivilegeValue systemPrivilegeValue) throws Exception {
		this.insert("SystemPrivilegeValueXML.insertSystemPrivilegeValue", systemPrivilegeValue);
	}
	
	@Override
	public void delSystemPrivilegeValueById(String id) throws Exception {
		this.delete("SystemPrivilegeValueXML.delSystemPrivilegeValueById", id);
	}

	@Override
	public void updateSystemPrivilegeValue(SystemPrivilegeValue systemPrivilegeValue) throws Exception {
		this.update("SystemPrivilegeValueXML.updateSystemPrivilegeValue", systemPrivilegeValue);
	}
	
	@Override
	public void initPval(String systemId) throws Exception {
		this.insert("SystemPrivilegeValueXML.initPval",systemId);
	}
}

