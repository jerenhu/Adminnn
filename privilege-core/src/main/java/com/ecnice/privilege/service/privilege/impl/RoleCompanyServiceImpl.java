package com.ecnice.privilege.service.privilege.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.ecnice.privilege.common.PagerModel;
import com.ecnice.privilege.common.Query;
import com.ecnice.privilege.dao.privilege.IRoleCompanyDao;
import com.ecnice.privilege.model.privilege.RoleCompany;
import com.ecnice.privilege.service.privilege.IRoleCompanyService;
import com.ecnice.tools.common.UUIDGenerator;

/**
 * @Title:角色分配的公司Service实现
 * @Description:
 * @Author:XTJ
 * @Since:2017-05-09 21:25:08
 * @Version:1.1.0
 * @Copyright:Copyright (c) 浙江蘑菇加电子商务有限公司 2015 ~ 2016 版权所有  
 */
@Service
public class RoleCompanyServiceImpl implements IRoleCompanyService {

	@Resource
	private IRoleCompanyDao roleCompanyDao;

	@Override
	public RoleCompany getRoleCompanyById(String id) throws Exception {
		return StringUtils.isNotBlank(id) ? this.roleCompanyDao.getRoleCompanyById(id.trim()) : null;
	}

	@Override
	public List<RoleCompany> getAll(RoleCompany roleCompany) throws Exception {
		return null != roleCompany ? this.roleCompanyDao.getAll(roleCompany) : null;
	}

	@Override
	public PagerModel<RoleCompany> getPagerModelByQuery(RoleCompany roleCompany, Query query)
			throws Exception {
		return (null != roleCompany && null != query) ? this.roleCompanyDao.getPagerModelByQuery(roleCompany, query) : null;
	}

	@Override
	public void insertRoleCompany(RoleCompany roleCompany) throws Exception {
		if (null != roleCompany) {
			roleCompany.setId(UUIDGenerator.generate());
			roleCompany.setCreateTime(new Date());
			roleCompany.setUpdateTime(new Date());
			this.roleCompanyDao.insertRoleCompany(roleCompany);
		}
	}
	
	@Override
	public void delRoleCompanyById(String id) throws Exception {
		if (StringUtils.isNotBlank(id)) {
			this.roleCompanyDao.delRoleCompanyById(id.trim());
		}
	}
	
	@Override
	public void delRoleCompanyByIds(String ids) throws Exception {
		ids = this.converString(ids);
		if(StringUtils.isNotBlank(ids)){
			this.roleCompanyDao.delRoleCompanyByIds(ids);
		}
	}
	
	@Override
	public void updateRoleCompany(RoleCompany roleCompany) throws Exception {
		if (null != roleCompany) {
			roleCompany.setUpdateTime(new Date());
			this.roleCompanyDao.updateRoleCompany(roleCompany);
		}
	}

	@Override
	public void updateRoleCompanyByIds(String ids,RoleCompany roleCompany) throws Exception {
		ids = this.converString(ids);
		if (StringUtils.isNotBlank(ids) && null != roleCompany) {
			roleCompany.setUpdateTime(new Date());
			this.roleCompanyDao.updateRoleCompanyByIds(ids, roleCompany);
		}
	}
	
	/**
	 * 将"1,2,3,4,5..."这种形式的字符串转成"'1','2','3','4'..."这种形式
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

	@Override
	public int deleteRoleCompanyByRoleCompanyId(String cId, String roleId) throws Exception {
		if(StringUtils.isNotBlank(cId) && StringUtils.isNotBlank(roleId)){
			return this.roleCompanyDao.deleteRoleCompanyByRoleCompanyId(cId, roleId);
		}
		return 0;
	}
}

