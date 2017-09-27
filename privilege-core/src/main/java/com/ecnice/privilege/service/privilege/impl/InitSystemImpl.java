package com.ecnice.privilege.service.privilege.impl;

import com.ecnice.privilege.dao.privilege.*;
import com.ecnice.privilege.dao.system.ISystemConfigDao;
import com.ecnice.privilege.model.privilege.*;
import com.ecnice.privilege.model.system.SystemConfig;
import com.ecnice.privilege.service.privilege.InitSystem;
import com.ecnice.tools.common.UUIDGenerator;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @Title:
 * @Description:初始化实现类
 * @Author:Bruce.Liu
 * @Since:2014年4月8日
 * @Version:1.1.0
 * @Copyright:Copyright (c) 浙江蘑菇加电子商务有限公司 2014 ~ 2015 版权所有
 */
@Service
public class InitSystemImpl implements InitSystem {
	@Resource
	private IDepartmentDao departmentDao;
	@Resource
	private IICSystemDao icSystemDao;
	@Resource
	private IModuleDao moduleDao;
	@Resource
	private IRoleDao roleDao;
	@Resource
	private IUserDao userDao;
	@Resource
	private IUserRoleDao userRoleDao;
	@Resource
	private ISystemConfigDao systemConfigDao;
	@Resource
	private IAclDao aclDao;
	@Resource
	private ISystemPrivilegeValueDao systemPrivilegeValueDao;
	@Override
	public void init() throws Exception {
		Document document = new SAXReader().read(Thread.currentThread()
				.getContextClassLoader().getResourceAsStream("init/init.xml"));
		Element rootElm = document.getRootElement();
		List<ACL> pvs = new ArrayList<ACL>();
		// 1.添加一个部门（电商平台总部门）
		importDepartment(rootElm.element("Department"));
		// 2.添加系统(权限系统)
		ICSystem icSystem = importICSystem(rootElm.element("IcSystem"));
		// 3.添加模块（权限管理 （系统管理，模块管理，用户管理，角色管理，系统配置，数据字典，系统权限值，公司管理））
		importModules(pvs, rootElm.elements("Module"));
		// 4.添加一个用户，用户名为admin，选择权限系统
		importUser(rootElm.element("User"));
		// 5.添加一个角色为超级管理员
		Role role = importRole(rootElm.element("Role"));
		// 5.1为超级管理员分配上面的所有权限值
		for (ACL acl : pvs) {
			acl.setId(UUIDGenerator.generate());
			acl.setReleaseId(role.getId());
			acl.setReleaseSn("role");
			acl.setSystemSn(icSystem.getSn());
			this.aclDao.insertAcl(acl);
		}
		//添加系统配置
		importSystemConfig();
	}
	
	// 添加角色
	private Role importRole(Element element) throws Exception {
		Role role = new Role();
		role.setId(element.attributeValue("id"));
		role.setName(element.attributeValue("name"));
		role.setSn(element.attributeValue("sn"));
		this.roleDao.insertRole(role);
		return role;
	}

	// 添加用户
	private void importUser(Element element) throws Exception {
		User user = new User();
		user.setId(element.attributeValue("id"));
		user.setRealName(element.attributeValue("realName"));
		user.setUsername(element.attributeValue("username"));
		user.setPassword(element.attributeValue("password"));
		user.setDepartmentId(element.attributeValue("departmentId"));
		this.userDao.insertUser(user);
		UserRole userRole = new UserRole();
		userRole.setId(UUIDGenerator.generate());
		userRole.setUserId(element.attributeValue("id"));
		userRole.setRoleId(element.attributeValue("roleId"));
		this.userRoleDao.insertUserRole(userRole);
	}

	// 添加系统
	private ICSystem importICSystem(Element element) throws Exception {
		ICSystem icSystem = new ICSystem();
		icSystem.setId(element.attributeValue("id"));
		icSystem.setName(element.attributeValue("name"));
		icSystem.setUrl(element.attributeValue("url"));
		icSystem.setSn(element.attributeValue("sn"));
		this.icSystemDao.insertIcSystem(icSystem);
		systemPrivilegeValueDao.initPval(icSystem.getId());
		return icSystem;
	}

	// 添加部门
	private void importDepartment(Element element) throws Exception {
		Department department = new Department();
		department.setId(element.attributeValue("id"));
		department.setName(element.attributeValue("name"));
		this.departmentDao.insertDepartment(department);
	}

	// 导入模块
	private void importModules(List<ACL> pvs, List modules)
			throws Exception {
		for (Iterator iter = modules.iterator(); iter.hasNext();) {
			Element elt = (Element) iter.next();
			Module module = new Module();
			module.setId(elt.attributeValue("id"));
			module.setName(elt.attributeValue("name"));
			module.setSn(elt.attributeValue("sn"));
			module.setUrl(elt.attributeValue("url"));
			module.setOrderNo(Integer.parseInt(elt.attributeValue("orderNo")));
			module.setSystemId(elt.attributeValue("systemId"));
			module.setPid(elt.attributeValue("pid"));
			if(module.getSn().equals("role") || module.getSn().equals("user")) {
				module.setState(new BigInteger("79"));
			}else {
				module.setState(new BigInteger("15"));
			}
			this.moduleDao.insertModule(module);
			ACL acl = new ACL();
			acl.setModuleId(module.getId());
			acl.setModuleSn(module.getSn());
			if(acl.getModuleSn().equals("role") || acl.getModuleSn().equals("user")) {
				acl.setAclState(new BigInteger("79"));
			}else {
				acl.setAclState(new BigInteger("15"));
			}
			pvs.add(acl);
		}
	}

	//添加系统配置
	private void importSystemConfig() throws Exception{
		SystemConfig config=new SystemConfig();
		config.setId(UUIDGenerator.generate());
		config.setConfigKey("is_prod");
		config.setConfigName("是否是开发环境");
		config.setConfigSn("192.168.0.164");
		config.setConfigValue("true");
		this.systemConfigDao.insertSystemConfig(config);
	}
}