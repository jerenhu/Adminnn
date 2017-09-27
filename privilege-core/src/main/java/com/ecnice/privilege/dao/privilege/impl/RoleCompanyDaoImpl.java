package com.ecnice.privilege.dao.privilege.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ecnice.privilege.common.PagerModel;
import com.ecnice.privilege.common.Query;
import com.ecnice.privilege.dao.MybatisTemplate;
import com.ecnice.privilege.dao.privilege.IRoleCompanyDao;
import com.ecnice.privilege.model.privilege.RoleCompany;

/**
 * @Title:角色分配的公司Dao实现
 * @Description:
 * @Author:XTJ
 * @Since:2017-05-09 21:25:08
 * @Version:1.1.0
 * @Copyright:Copyright (c) 浙江蘑菇加电子商务有限公司 2015 ~ 2016 版权所有  
 */
@Repository
public class RoleCompanyDaoImpl extends MybatisTemplate implements IRoleCompanyDao {

	@Override
	public RoleCompany getRoleCompanyById(String id) throws Exception {
		return (RoleCompany)this.selectOne("RoleCompanyXML.getRoleCompanyById", id);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<RoleCompany> getAll(RoleCompany roleCompany) throws Exception {
		return (List<RoleCompany>)this.selectList("RoleCompanyXML.getAll", roleCompany);
	}

	@Override
	public PagerModel<RoleCompany> getPagerModelByQuery(RoleCompany roleCompany, Query query)
			throws Exception {
		return this.getPagerModelByQuery(roleCompany, query, "RoleCompanyXML.getPagerModelByQuery");
	}

	@Override
	public void insertRoleCompany(RoleCompany roleCompany) throws Exception {
		
		this.insert("RoleCompanyXML.insertRoleCompany", roleCompany);
	}
	
	
	@Override
	public void delRoleCompanyById(String id) throws Exception {
		this.delete("RoleCompanyXML.delRoleCompanyById", id);
	}
	
	@Override
	public void delRoleCompanyByIds(String ids) throws Exception {
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("ids", ids);
		this.delete("RoleCompanyXML.delRoleCompanyByIds", params);
	}
	
	@Override
	public void updateRoleCompany(RoleCompany roleCompany) throws Exception {
		this.update("RoleCompanyXML.updateRoleCompany", roleCompany);
	}

	@Override
	public void updateRoleCompanyByIds(String ids,RoleCompany roleCompany) throws Exception {
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("ids", ids);
		params.put("roleCompany", roleCompany);
		this.update("RoleCompanyXML.updateRoleCompanyByIds", params);
	}

	@Override
	public int deleteRoleCompanyByRoleCompanyId(String cId, String roleId) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("cId", cId);
		map.put("roleId", roleId);
		return this.delete("RoleCompanyXML.deleteRoleCompanyByRoleCompanyId", map);
	}
}

