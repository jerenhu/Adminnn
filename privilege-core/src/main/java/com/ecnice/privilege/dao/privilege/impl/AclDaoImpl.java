package com.ecnice.privilege.dao.privilege.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Repository;

import com.ecnice.privilege.common.PrivilegeException;
import com.ecnice.privilege.dao.MybatisTemplate;
import com.ecnice.privilege.dao.privilege.IAclDao;
import com.ecnice.privilege.model.privilege.ACL;
import com.ecnice.privilege.vo.privilege.AclVo;

@Repository
public class AclDaoImpl extends MybatisTemplate implements IAclDao {

	@Override
	public void delAclById(String id) {
		this.delete("AclXML.delAclById", id);
	}

	@Override
	public void updateAcl(ACL acl) {
		this.update("AclXML.updateAcl", acl);
	}

	@Override
	public List<ACL> getAllACL(ACL acl) {
		return (List<ACL>) this.selectList("AclXML.getAllACL", acl);
	}

	@Override
	public List<ACL> getAclsByRoleId(String roleId) throws Exception {
		Map<String, String> params = new HashMap<String, String>();
		params.put("roleId", roleId);
		return (List<ACL>) this.selectList("AclXML.getAclsByRoleId", params);
	}

	@Override
	public List<ACL> getAclsByRoleId(String roleId, String systemSn) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("roleId", roleId);
		params.put("systemSn", systemSn);
		return (List<ACL>) this.selectList("AclXML.getAclsByRoleId", params);
	}

	@Override
	public List<ACL> getAclsByUserId(String userId, String systemSn) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("userId", userId);
		params.put("systemSn", systemSn);
		return (List<ACL>) this.selectList("AclXML.getAclsByUserId", params);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ACL> getAclsByUserId(String userId) throws Exception {
		Map<String, String> params = new HashMap<String, String>();
		params.put("userId", userId);
		return (List<ACL>) this.selectList("AclXML.getAclsByUserId", params);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AclVo> getModuleIdsByUserId(String userId) throws Exception {
		return (List<AclVo>) this.selectList("AclXML.getModuleIdsByUserId",
				userId);

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AclVo> getModuleIdsByRoleIds(String roleIds) throws Exception {
		return (List<AclVo>) this.selectList("AclXML.getModuleIdsByRoleIds",
				roleIds);

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ACL> getAclsByRoleIds(String roleIds) throws Exception {
		return (List<ACL>) this.selectList("AclXML.getAclsByRoleIds", roleIds);
	}

	@Override
	public void insertAcl(ACL acl) throws Exception {
		Date currDate = new Date();
		acl.setCreateTime(currDate);
		acl.setUpdateTime(currDate);
		this.insert("AclXML.insertAcl", acl);
	}

	@Override
	public void delAcl(ACL acl) throws Exception {
		this.delete("AclXML.delAcl", acl);
	}
	
	@Override
	public void updateSystemSn(String systemSn, String oldSystemSn) {
		Map<String, String> map=new HashMap<String, String>();
		map.put("systemSn", systemSn);
		map.put("oldSystemSn", oldSystemSn);
		this.update("AclXML.updateSn", map);
	}
	
	@Override
	public void updateModuleSn(String moduleSn, String moduleId) {
		Map<String, String> map=new HashMap<String, String>();
		map.put("moduleSn", moduleSn);
		map.put("moduleId", moduleId);
		this.update("AclXML.updateSn", map);
	}
	
	@Override
	public void updateModuleSnByModuleId(String newModuleSn, String moduleId) {
		Map<String, String> map=new HashMap<String, String>();
		map.put("newModuleSn", newModuleSn);
		map.put("moduleId", moduleId);
		this.update("AclXML.updateModuleSnByModuleId", map);
	}

	@Override
	public void insertAclBatch(List<ACL> acls) throws Exception {
		this.insert("AclXML.insertAclBatch", acls);
	}

	@Override
	public ACL getAclBySystemSnAndModuleId(ACL acl) {
		List<ACL> acls = (List<ACL>) this.selectList("AclXML.getAclBySystemSnAndModuleId", acl);
		if(CollectionUtils.isNotEmpty(acls)) {
			if(acls.size()>1) {
				throw new PrivilegeException("101", "查询出多条记录，数据有问题，一个模块不可能有多个ACL");
			}else {
				return acls.get(0);
			}
		}else {
			return null;
		}
	}
}
