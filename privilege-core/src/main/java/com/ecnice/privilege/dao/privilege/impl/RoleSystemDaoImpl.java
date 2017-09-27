package com.ecnice.privilege.dao.privilege.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.ecnice.privilege.dao.privilege.IRoleSystemDao;
import com.ecnice.privilege.model.privilege.RoleSystem;
import com.ecnice.tools.common.UUIDGenerator;
import com.ecnice.tools.pager.PagerModel;
import com.ecnice.tools.pager.Query;
import com.ecnice.tools.template.MybatisTemplate;




/**
 * 延时短信Dao实现
 * @author bruce.liu
 * @date 2016-12-23 16:53:22
 */
@Repository
public class RoleSystemDaoImpl extends MybatisTemplate implements IRoleSystemDao {

	@Override
	public void delRoleSystemByRoleId(String roleId) throws Exception {
		this.delete("RoleSystemXML.delRoleSystemByRoleId", roleId);
	}

	@Override
	public List<RoleSystem> getRoleSystemsByRoleId(String roleId) throws Exception {
		return this.selectList("RoleSystemXML.getRoleSystemsByRoleId", roleId);
	}

	@Override
	public RoleSystem getRoleSystemById(String id) throws Exception {
		return (RoleSystem)this.selectOne("RoleSystemXML.getRoleSystemById", id);
	}
	
	@Override
	public List<RoleSystem> getRoleSystemByIds(String ids) throws Exception {
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("ids", ids);
		return this.selectList("RoleSystemXML.getRoleSystemByIds", params);
	}
	
	@Override
	public List<RoleSystem> getRoleSystemByIdsList(List<String> ids) throws Exception {
		return this.selectList("RoleSystemXML.getRoleSystemByIdsList", ids);
	}

	@Override
	public List<RoleSystem> getAll(RoleSystem RoleSystem) throws Exception {
		return this.selectList("RoleSystemXML.getAll", RoleSystem);
	}

	@Override
	public PagerModel<RoleSystem> getPagerModelByQuery(RoleSystem RoleSystem, Query query)
			throws Exception {
		return this.getPagerModelByQuery(RoleSystem, query, "RoleSystemXML.getPagerModelByQuery");
	}
	
	@Override
	public int getByPageCount(RoleSystem RoleSystem)throws Exception {
		return this.selectOne("RoleSystemXML.getByPageCount", RoleSystem);
	}

	@Override
	public void insertRoleSystem(RoleSystem RoleSystem) throws Exception {
		if (null != RoleSystem) {
			RoleSystem.setId(UUIDGenerator.generate());
			RoleSystem.setCreateTime(new Date());
			RoleSystem.setUpdateTime(new Date());
			this.insert("RoleSystemXML.insertRoleSystem", RoleSystem);
		}
	}
	
	@Override
	public void insertRoleSystemBatch(List<RoleSystem> RoleSystems) throws Exception {
		if(CollectionUtils.isNotEmpty(RoleSystems)){
			for (RoleSystem RoleSystem : RoleSystems) {
				if (null != RoleSystem) {
					RoleSystem.setId(UUIDGenerator.generate());
					RoleSystem.setCreateTime(new Date());
				}
			}
			this.insert("RoleSystemXML.insertRoleSystemBatch", RoleSystems);
		}
	}
	
	@Override
	public void delRoleSystemById(String id) throws Exception {
		this.delete("RoleSystemXML.delRoleSystemById", id);
	}
	
	@Override
	public void delRoleSystemByIds(String ids) throws Exception {
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("ids", ids);
		this.delete("RoleSystemXML.delRoleSystemByIds", params);
	}
	
	@Override
	public void delRoleSystemByIdsList(List<String> ids) throws Exception {
		this.delete("RoleSystemXML.delRoleSystemByIdsList", ids);
	}
	
	@Override
	public int updateRoleSystem(RoleSystem RoleSystem) throws Exception {
		if (null != RoleSystem) {
			RoleSystem.setUpdateTime(new Date());
			return this.update("RoleSystemXML.updateRoleSystem", RoleSystem);
		} else {
			return 0;
		}
	}
	
	
	@Override
	public int updateRoleSystemByIdsList(List<String> ids,RoleSystem RoleSystem) throws Exception {
		if (CollectionUtils.isNotEmpty(ids) && null != RoleSystem) {
			RoleSystem.setUpdateTime(new Date());
			Map<String,Object> params = new HashMap<String, Object>();
			params.put("ids", ids);
			params.put("RoleSystem", RoleSystem);
			return this.update("RoleSystemXML.updateRoleSystemByIdsList", params);
		} else {
			return 0;
		}
	}
	
	@Override
	public int updateRoleSystemList(List<RoleSystem> RoleSystems) throws Exception {
		if(CollectionUtils.isNotEmpty(RoleSystems)){
			for (RoleSystem RoleSystem : RoleSystems) {
				if (null != RoleSystem) {
					RoleSystem.setUpdateTime(new Date());
				}
			}
			return this.update("RoleSystemXML.updateRoleSystemList", RoleSystems);
		} else {
			return 0;
		}
	}
}

