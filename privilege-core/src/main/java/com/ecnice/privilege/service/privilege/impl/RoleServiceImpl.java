package com.ecnice.privilege.service.privilege.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.ecnice.privilege.common.PagerModel;
import com.ecnice.privilege.common.Query;
import com.ecnice.privilege.constant.PrivilegeConstant;
import com.ecnice.privilege.dao.privilege.IAclDao;
import com.ecnice.privilege.dao.privilege.IRoleDao;
import com.ecnice.privilege.dao.privilege.IUserRoleDao;
import com.ecnice.privilege.model.privilege.ACL;
import com.ecnice.privilege.model.privilege.ICSystem;
import com.ecnice.privilege.model.privilege.Role;
import com.ecnice.privilege.model.privilege.RoleCompany;
import com.ecnice.privilege.model.privilege.User;
import com.ecnice.privilege.service.privilege.IAclService;
import com.ecnice.privilege.service.privilege.IICSystemService;
import com.ecnice.privilege.service.privilege.IRoleCompanyService;
import com.ecnice.privilege.service.privilege.IRoleService;
import com.ecnice.privilege.utils.DateUtil;
import com.ecnice.tools.common.UUIDGenerator;

/**
 * @Title:
 * @Description:角色service
 * @Author:Bruce.Liu
 * @Since:2014年3月31日
 * @Version:1.1.0
浙江蘑菇加电子商务有限公司 2014 ~ 2015 版权所有
 */
@Service
public class RoleServiceImpl implements IRoleService {
	
	@Resource
	private IRoleDao roleDao;
	@Resource
	private IUserRoleDao userRoleDao;
	@Resource
	private IAclDao aclDao;
	@Resource
	private IICSystemService icSystemService;
	@Resource
	private IAclService aclService;
	@Resource
	private IRoleCompanyService roleCompanyService;
	
	@Override
	public List<Role> getRolesByUserId(String userId) throws Exception {
		return roleDao.getRolesByUserId(userId);
	}

	@Override
	public void insertRole(Role role) throws Exception {
		Date currDate = new Date();
		role.setId(UUIDGenerator.generate());
		role.setValidState(PrivilegeConstant.YES);
		role.setCreateTime(currDate);
		role.setUpdateTime(currDate);
		roleDao.insertRole(role);
	}

	@Override
	public void updateRole(Role role) throws Exception {
		role.setUpdateTime(new Date());
		roleDao.updateRole(role);
	}

	@Override
	public void delRole(String id) throws Exception {
		roleDao.delRole(id);
		userRoleDao.delUserRoleByRoleId(id);
		ACL acl=new ACL();
		acl.setReleaseId(id);
		aclDao.delAcl(acl);
	}
	
	@Override
	public void delRoles(String[] ids) throws Exception {
		for(String id : ids){
			// 1如果删除角色时，将角色所授予的权限值全部删除掉。
			Role persistent = getRoleById(id);
			if(StringUtils.isNotBlank(persistent.getSystemId())){
				ICSystem sysSn = this.icSystemService.getICSystemById(persistent.getSystemId());
				ACL dacl = new ACL();
				dacl.setReleaseId(persistent.getId());
		        dacl.setSystemSn(sysSn.getSn());
				aclService.createAllAcl(dacl, false);
			}
			this.delRole(id);
		}
	}

	@Override
	public Role getRoleById(String id) throws Exception {
		return roleDao.getRoleById(id);
	}
	
	@Override
	public PagerModel<Role> getPagerModelByQuery(Role role, Query query)
			throws Exception {
		return roleDao.getPagerModelByQuery(role, query);
	}
	
	@Override
	public PagerModel<Role> getPagerModelByQuery4Api(Role role, Query query)
			throws Exception {
		return roleDao.getPagerModelByQuery4Api(role, query);
	}
	@Override
	public PagerModel<Role> getPagerModel(Role role, Query query)
			throws Exception {
		PagerModel<Role> pm = roleDao.getPagerModel(role, query);
		List<Role> datas = pm.getDatas();
		if(CollectionUtils.isNotEmpty(datas)) {
			for(Role r : datas) {
				String roleId = r.getId();
				if(StringUtils.isNotBlank(r.getLongName())){
					r.setName(r.getLongName());
				}
				List<User> users = userRoleDao.getUsersByRoleId(roleId);
				StringBuffer userStrs = new StringBuffer("");
				if(CollectionUtils.isNotEmpty(users)) {
					for(User user : users) {
						//1.1验证是否过期 
						if (null != user && null != user.getFailureTime()) {
							SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
							int day = DateUtil.diffDate(sdf.parse(sdf.format(user.getFailureTime())), sdf.parse(sdf.format(new Date())));
							if (day > 0) {// 未过期
								userStrs.append(user.getRealName()).append(" ");
							}
						}
					}
					r.setUsers(userStrs.toString());
				}
			}
		}
		return pm;
	}
	
	@Override
	public List<Role> getAll(Role role) throws Exception {
		return this.roleDao.getAll(role);
	}

	@Override
	public void insertRole(Role role, String roleCompanyIdsStr) throws Exception {
		this.insertRole(role);
		
		insertRoleCompany(role, roleCompanyIdsStr);
	}
	
	private void insertRoleCompany(Role role, String roleCompanyIdsStr) throws Exception {
		if(StringUtils.isNotBlank(roleCompanyIdsStr)){
			RoleCompany roleCompany = new RoleCompany();
			String[] companyIdArr = roleCompanyIdsStr.split(PrivilegeConstant.SEPARATOR);
			for(String id : companyIdArr){
				if(StringUtils.isNotBlank(id)){
					RoleCompany rc = new RoleCompany();
					rc.setRoleId(role.getId());
					rc.setCompanyId(id);
					rc.setCreator(role.getCreator());
					rc.setUpdator(role.getUpdator());
					rc.setCreateTime(role.getCreateTime());
					rc.setUpdateTime(role.getUpdateTime());
					this.roleCompanyService.insertRoleCompany(rc);
				}
			}
		}
	}

	@Override
	public void updateRole(Role role, String roleCompanyIdsStr) throws Exception {
		this.updateRole(role);
		RoleCompany roleCompany = new RoleCompany();
		roleCompany.setRoleId(role.getId());
		roleCompany.setDelFlag(PrivilegeConstant.NO_DELETE_FLAG);
		List<RoleCompany> rcs = this.roleCompanyService.getAll(roleCompany);
		for (RoleCompany rc : rcs) {
			this.roleCompanyService.delRoleCompanyById(rc.getId());
		}
		insertRoleCompany(role, roleCompanyIdsStr);
	}

	@Override
	public void updateValidState(Role role) throws Exception {
		if(PrivilegeConstant.YES == role.getValidState().intValue()){
			
		}else if(PrivilegeConstant.NO == role.getValidState().intValue()){
			Role persistent = this.getRoleById(role.getId());
			ICSystem sysSn = this.icSystemService.getICSystemById(persistent.getSystemId());
			ACL dacl = new ACL();
			dacl.setReleaseId(persistent.getId());
	        dacl.setSystemSn(sysSn.getSn());
			aclService.createAllAcl(dacl, false);
		}
		this.roleDao.updateRole(role);
	}
}
