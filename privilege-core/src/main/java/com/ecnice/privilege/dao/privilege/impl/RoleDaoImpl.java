package com.ecnice.privilege.dao.privilege.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.ecnice.privilege.common.PagerModel;
import com.ecnice.privilege.common.Query;
import com.ecnice.privilege.dao.MybatisTemplate;
import com.ecnice.privilege.dao.privilege.IRoleDao;
import com.ecnice.privilege.model.privilege.Role;

/**
 * 
 * @Title:
 * @Description:角色Dao实现类
 * @Author:Bruce.Liu
 * @Since:2014年3月31日
 * @Version:1.1.0 浙江蘑菇加电子商务有限公司 2014 ~ 2015 版权所有
 */
@Repository
public class RoleDaoImpl extends MybatisTemplate implements IRoleDao {

	private static final Logger logger = Logger.getLogger(RoleDaoImpl.class);

	@Override
	public List<Role> getRolesByUserId(String userId) throws Exception {
		return (List<Role>) this.selectList("RoleXML.getRolesByUserId", userId);
	}

	@Override
	public void insertRole(Role role) throws Exception {
		Date currDate = new Date();
		role.setCreateTime(currDate);
		role.setUpdateTime(currDate);
		this.insert("RoleXML.insertRole", role);
	}

	@Override
	public void updateRole(Role role) throws Exception {
		this.update("RoleXML.updateRole", role);
	}

	@Override
	public void delRole(String id) throws Exception {
		Role role = this.getRoleById(id);
		this.delete("RoleXML.delRole", id);
	}

	@Override
	public Role getRoleById(String id) throws Exception {
		return (Role) this.selectOne("RoleXML.getRoleById", id);
	}

	@Override
	public PagerModel<Role> getPagerModel(Role role, Query query) throws Exception {
		return this.getPagerModelByQuery(role, query, "RoleXML.getPagerModel");
	}
	@Override
	public PagerModel<Role> getPagerModelByQuery(Role role, Query query) throws Exception {
		return this.getPagerModelByQuery(role, query, "RoleXML.getPagerModelByQuery");
	}
	@Override
	public PagerModel<Role> getPagerModelByQuery4Api(Role role, Query query) throws Exception {
		return this.getPagerModelByQuery(role, query, "RoleXML.getPagerModelByQuery4Api");
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Role> getAll(Role role) throws Exception {
		return (List<Role>) this.selectList("RoleXML.getAll", role);
	}

	@Override
	public int updateSyncRoles(List<Role> roles) throws Exception {
		if(CollectionUtils.isNotEmpty(roles)){
			return this.update("RoleXML.synRoleList", roles);
		}else{
			return 0;
		}
	}
}
