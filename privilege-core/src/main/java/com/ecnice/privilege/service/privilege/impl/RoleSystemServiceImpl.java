package com.ecnice.privilege.service.privilege.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.ecnice.privilege.dao.privilege.IRoleSystemDao;
import com.ecnice.privilege.model.privilege.RoleSystem;
import com.ecnice.privilege.service.privilege.IRoleSystemService;
import com.ecnice.tools.common.UUIDGenerator;
import com.ecnice.tools.pager.PagerModel;
import com.ecnice.tools.pager.Query;

/**
 * 延时短信Service实现
 * 
 * @author bruce.liu
 * @date 2016-12-23 16:53:22
 */
@Service
public class RoleSystemServiceImpl implements IRoleSystemService {

	@Resource
	private IRoleSystemDao roleSystemDao;

	@Override
	public boolean updateRoleSystemByRoleId(String roleId, List<String> systemIds) throws Exception {
		boolean flag = false;
		// 1：通过roleId删除它的系统
		roleSystemDao.delRoleSystemByRoleId(roleId);
		List<RoleSystem> roleSystems = new ArrayList<RoleSystem>();
		for (String systemId : systemIds) {
			RoleSystem ns = new RoleSystem();
			ns.setId(UUIDGenerator.generate());
			ns.setRoleId(roleId);
			ns.setSystemId(systemId);
			roleSystems.add(ns);
		}
		// 2：添加数据
		roleSystemDao.insertRoleSystemBatch(roleSystems);
		flag = true;
		return flag;
	}

	@Override
	public List<RoleSystem> getRoleSystemsByRoleId(String roleId) throws Exception {
		return roleSystemDao.getRoleSystemsByRoleId(roleId);
	}

	@Override
	public RoleSystem getRoleSystemById(String id) throws Exception {
		return StringUtils.isNotBlank(id) ? this.roleSystemDao.getRoleSystemById(id.trim()) : null;
	}

	@Override
	public List<RoleSystem> getRoleSystemByIds(String ids) throws Exception {
		ids = this.converString(ids);
		return StringUtils.isNotBlank(ids) ? this.roleSystemDao.getRoleSystemByIds(ids) : null;
	}

	@Override
	public List<RoleSystem> getRoleSystemByIdsList(List<String> ids) throws Exception {
		return CollectionUtils.isNotEmpty(ids) ? this.roleSystemDao.getRoleSystemByIdsList(ids) : null;
	}

	@Override
	public List<RoleSystem> getAll(RoleSystem RoleSystem) throws Exception {
		return null != RoleSystem ? this.roleSystemDao.getAll(RoleSystem) : null;
	}

	@Override
	public PagerModel<RoleSystem> getPagerModelByQuery(RoleSystem RoleSystem, Query query) throws Exception {
		return (null != RoleSystem && null != query) ? this.roleSystemDao.getPagerModelByQuery(RoleSystem, query)
				: null;
	}

	@Override
	public int getByPageCount(RoleSystem RoleSystem) throws Exception {
		return (null != RoleSystem) ? this.roleSystemDao.getByPageCount(RoleSystem) : 0;
	}

	@Override
	public void insertRoleSystem(RoleSystem RoleSystem) throws Exception {
		this.roleSystemDao.insertRoleSystem(RoleSystem);
	}

	@Override
	public void insertRoleSystemBatch(List<RoleSystem> RoleSystems) throws Exception {
		this.roleSystemDao.insertRoleSystemBatch(RoleSystems);
	}

	@Override
	public void delRoleSystemById(String id) throws Exception {
		if (StringUtils.isNotBlank(id)) {
			this.roleSystemDao.delRoleSystemById(id.trim());
		}
	}

	@Override
	public void delRoleSystemByIds(String ids) throws Exception {
		ids = this.converString(ids);
		if (StringUtils.isNotBlank(ids)) {
			this.roleSystemDao.delRoleSystemByIds(ids);
		}
	}

	@Override
	public void delRoleSystemByIdsList(List<String> ids) throws Exception {
		if (CollectionUtils.isNotEmpty(ids)) {
			this.roleSystemDao.delRoleSystemByIdsList(ids);
		}
	}

	@Override
	public int updateRoleSystem(RoleSystem RoleSystem) throws Exception {
		return this.roleSystemDao.updateRoleSystem(RoleSystem);
	}

	@Override
	public int updateRoleSystemByIdsList(List<String> ids, RoleSystem RoleSystem) throws Exception {
		return this.roleSystemDao.updateRoleSystemByIdsList(ids, RoleSystem);
	}

	@Override
	public int updateRoleSystemList(List<RoleSystem> RoleSystems) throws Exception {
		return this.roleSystemDao.updateRoleSystemList(RoleSystems);
	}

	/***
	 * 将"1,2,3,4,5..."这种形式的字符串转成"'1','2','3','4'..."这种形式
	 * 
	 * @param strs
	 * @return
	 */
	private String converString(String strs) {
		if (StringUtils.isNotBlank(strs)) {
			String[] idStrs = strs.trim().split(",");
			if (null != idStrs && idStrs.length > 0) {
				StringBuffer sbf = new StringBuffer("");
				for (String str : idStrs) {
					if (StringUtils.isNotBlank(str)) {
						sbf.append("'").append(str.trim()).append("'").append(",");
					}
				}
				if (sbf.length() > 0) {
					sbf = sbf.deleteCharAt(sbf.length() - 1);
					return sbf.toString();
				}
			}
		}
		return "";
	}

	/***
	 * 将"1,2,3,4,5..."这种形式的字符串转成List<String> 集合
	 * 
	 * @param strs
	 * @return
	 * 
	 * 		private List<String> converStringToList(String strs) { if
	 *         (StringUtils.isNotBlank(strs)) { String[] idStrs =
	 *         strs.trim().split(","); if (null != idStrs && idStrs.length > 0)
	 *         { List<String> strsList = new ArrayList<String>(); for (String
	 *         str : idStrs) { if (StringUtils.isNotBlank(str)) {
	 *         strsList.add(str.trim()); } } if (strsList.size() > 0) { return
	 *         strsList; } } } return null; }
	 */

}
