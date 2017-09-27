package com.ecnice.privilege.service.privilege.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.ecnice.privilege.common.PagerModel;
import com.ecnice.privilege.common.Query;
import com.ecnice.privilege.constant.PermissionConatant;
import com.ecnice.privilege.dao.privilege.IAclDao;
import com.ecnice.privilege.dao.privilege.IICSystemDao;
import com.ecnice.privilege.dao.privilege.IModuleDao;
import com.ecnice.privilege.dao.privilege.IRoleDao;
import com.ecnice.privilege.dao.privilege.ISystemPrivilegeValueDao;
import com.ecnice.privilege.model.privilege.ACL;
import com.ecnice.privilege.model.privilege.ICSystem;
import com.ecnice.privilege.model.privilege.Module;
import com.ecnice.privilege.model.privilege.SystemPrivilegeValue;
import com.ecnice.privilege.service.privilege.IAclService;
import com.ecnice.privilege.service.privilege.IModuleService;
import com.ecnice.tools.common.UUIDGenerator;

/**
 * 模块管理
 * 
 * @author Martin.Wang
 * 
 */
@Service
public class ModuleServiceImpl implements IModuleService {
	private static final Logger logger = Logger.getLogger(ModuleServiceImpl.class);
	@Resource
	private IModuleDao moduleDao;
	@Resource
	private IRoleDao roleDao;
	@Resource
	private IAclDao aclDao;
	@Resource
	private IICSystemDao icSystemDao;
	@Resource
	private ISystemPrivilegeValueDao systemPrivilegeValueDao;
	@Resource
	private IAclService aclService;

	/**
	 * 得到系统所有的模块和模块值，当然的也做了每个权限值是否勾选判断
	 * 
	 * @param systemSn 系统标识
	 * @param type role或者user
	 * @param releaseId roleid或者userid
	 * @return
	 * @throws Exception
	 */
	public List<Module> getAllModuleBySystemSnAndReleaseId(String systemSn, String type, String releaseId)
			throws Exception {
		//获取ACL列表
		List<ACL> acls = null;
		if (type.equals(ACL.ROLE)) {
			acls = this.aclService.getOneAclsByRoleId(releaseId, systemSn);
		} else {
			acls = aclService.getOneAclsByUserId(releaseId, systemSn);
		}
		// 1:得到系统的所有模块
		List<Module> modules = moduleDao.getModulesBySystemSn(systemSn);
		// 2：得到系统的所有预先设置的权限值
		List<SystemPrivilegeValue> list = systemPrivilegeValueDao.getSystemPrivilegeValuesBySystemSn(systemSn);
		// 3：得到模块的权限列表
		for (Module module : modules) {
			List<SystemPrivilegeValue> havePrivales = new ArrayList<SystemPrivilegeValue>();
			for (SystemPrivilegeValue spv : list) {
				if (module.getPermission(spv.getPosition())) {
					SystemPrivilegeValue clObj = new SystemPrivilegeValue();
					BeanUtils.copyProperties(spv, clObj);
					//判断这个权限值是否是需要勾选
					for(ACL acl : acls) {
						if(acl.getModuleId().equals(module.getId())) {
							int yes = acl.getPermission(clObj.getPosition());
							if(yes>0) {
								clObj.setFlag(true);
								//如果存在就break掉
								break;
							}
						}
					}
					havePrivales.add(clObj);
				}
			}
			module.setPvs(havePrivales);
		}
		return modules;
	}

	@Override
	public void addPriVal(List<Integer> positions, String moduleId) throws Exception {
		Module module = moduleDao.getModuleById(moduleId);
		for (Integer p : positions) {
			module.setPermission(p, true);
		}
		moduleDao.updateModule(module);
	}

	@Override
	public void deletePriVal(String systemPrivilegeValueId, String moduleId) throws Exception {
		SystemPrivilegeValue spv = systemPrivilegeValueDao.getSystemPrivilegeValueById(systemPrivilegeValueId);
		Module module = moduleDao.getModuleById(moduleId);
		module.setPermission(spv.getPosition(), false);
		moduleDao.updateModule(module);
		
		//把acl也要相应的处理掉
		ACL acl = new ACL();
		acl.setModuleId(moduleId);
		List<ACL> acls = aclDao.getAllACL(acl);
		if(CollectionUtils.isNotEmpty(acls)) {
			for(ACL al : acls) {
				al.setPermission(spv.getPosition(), false);
				aclDao.updateAcl(al);
			}
		}
	}

	@Override
	public List<Module> getAllModulePri(Module module) throws Exception {
		List<Module> modules = moduleDao.getAllModule(module);
		SystemPrivilegeValue ps = new SystemPrivilegeValue();
		ps.setSystemId(module.getSystemId());
		List<SystemPrivilegeValue> spvs = systemPrivilegeValueDao.getAll(ps);
		for (Module m : modules) {
			List<SystemPrivilegeValue> msvs = new ArrayList<SystemPrivilegeValue>();
			for (SystemPrivilegeValue spv : spvs) {
				boolean yes = m.getPermission(spv.getPosition());
				if (yes) {
					spv.setModuleId(m.getId());
					SystemPrivilegeValue clObj = new SystemPrivilegeValue();
					// 对象一定要克隆一下，否则就会出现问题
					org.springframework.beans.BeanUtils.copyProperties(spv, clObj);
					msvs.add(clObj);
				}
			}
			m.setPvs(msvs);
		}
		return modules;
	}

	@Override
	public List<Module> getTreeModuleBySystemIdAndAcls(Set<ACL> acls, String systemId) throws Exception {
		ICSystem icSystem = icSystemDao.getICSystemById(systemId.trim());
		if (null == icSystem) {
			logger.info("通过系统id=" + systemId + "查询不到系统信息！！");
			return null;
		}
		StringBuffer moduleIds = new StringBuffer("");
		if (CollectionUtils.isNotEmpty(acls)) {
			for (ACL acl : acls) {
				if (icSystem.getSn().equals(acl.getSystemSn())) {
					int yes = acl.getPermission(PermissionConatant.R);
					if (yes > 0) {
						moduleIds.append("'").append(acl.getModuleId()).append("'");
						moduleIds.append(",");
					}
				}
			}
		}
		if (moduleIds.length() > 0) {
			moduleIds = moduleIds.deleteCharAt(moduleIds.length() - 1);
			return moduleDao.getModulesByIds(moduleIds.toString(), systemId);
			//return moduleDao.getModulesByIdsAndOther(moduleIds.toString(), systemId,1);
		}
		return null;
	}

	@Override
	public boolean checkChildren(String pid) throws Exception {
		return moduleDao.checkChildren(pid);
	}

	@Override
	public List<Module> getModulesBySystemId(String systemId) throws Exception {
		return moduleDao.getModulesBySystemId(systemId);
	}

	/**
	 * 添加模块
	 * 
	 * @param module
	 *            要添加的模块对象
	 */
	@Override
	public void insertModule(Module module) throws Exception {
		module.setId(UUIDGenerator.generate());
		module.setState(new BigInteger("15"));
		this.moduleDao.insertModule(module);
	}

	/**
	 * 修改模块
	 * 
	 * @param module
	 *            要修改的模块对象
	 */
	@Override
	public void updateModule(Module module) throws Exception {
		// 判断是否需要修改其它模块关联的模块标识
		Module oldModule = this.moduleDao.getModuleById(module.getId());
		if (!oldModule.getSn().equals(module.getSn())) {
			this.aclDao.updateModuleSnByModuleId(module.getSn(), module.getId());
		}
		this.moduleDao.updateModule(module);
	}

	/**
	 * 删除模块
	 * 
	 * @param id
	 *            模块id
	 */
	@Override
	public void deleteModule(String id) throws Exception {
		//this.moduleDao.deleteModule(id);
		this.deleteModules(new String[]{id});
	}

	/**
	 * 批量删除
	 */
	@Override
	public void deleteModules(String[] ids) throws Exception {
		for (String id : ids) {
			this.moduleDao.deleteModule(id);
			ACL acl = new ACL();
			/**20161103 wen hz 取消默认值*/
			acl.setReleaseSn(null); 
			acl.setModuleId(id);
			this.aclDao.delAcl(acl);
		}
	}

	/**
	 * 获取所有模块列表
	 * 
	 * @param module
	 *            查询条件
	 */
	@Override
	public List<Module> getAllModule(Module module) throws Exception {
		return this.moduleDao.getAllModule(module);
	}

	/**
	 * 分页获取模块列表
	 * 
	 * @param module
	 *            查询条件
	 * @param query
	 *            分页条件
	 */
	@Override
	public PagerModel<Module> getPagerModule(Module module, Query query) throws Exception {
		return this.moduleDao.getPagerModule(module, query);
	}

	/**
	 * 根据id获取模块对象
	 * 
	 * @param id
	 *            模块id
	 */
	@Override
	public Module getModuleById(String id) throws Exception {
		return this.moduleDao.getModuleById(id);
	}
}
