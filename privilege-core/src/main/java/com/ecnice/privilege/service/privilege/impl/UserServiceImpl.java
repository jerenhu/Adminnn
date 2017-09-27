package com.ecnice.privilege.service.privilege.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.ecnice.privilege.common.PagerModel;
import com.ecnice.privilege.common.PrivilegeException;
import com.ecnice.privilege.common.Query;
import com.ecnice.privilege.common.SessionMap;
import com.ecnice.privilege.constant.PrivilegeConstant;
import com.ecnice.privilege.dao.privilege.IAclDao;
import com.ecnice.privilege.dao.privilege.IDepartmentDao;
import com.ecnice.privilege.dao.privilege.IICSystemDao;
import com.ecnice.privilege.dao.privilege.IUserDao;
import com.ecnice.privilege.dao.privilege.IUserRoleDao;
import com.ecnice.privilege.model.privilege.ACL;
import com.ecnice.privilege.model.privilege.Department;
import com.ecnice.privilege.model.privilege.ICSystem;
import com.ecnice.privilege.model.privilege.Module;
import com.ecnice.privilege.model.privilege.Role;
import com.ecnice.privilege.model.privilege.User;
import com.ecnice.privilege.model.system.LoginLog;
import com.ecnice.privilege.service.privilege.IAclService;
import com.ecnice.privilege.service.privilege.IModuleService;
import com.ecnice.privilege.service.privilege.IUserService;
import com.ecnice.privilege.service.system.ILoginLogService;
import com.ecnice.privilege.utils.DateUtil;
import com.ecnice.privilege.utils.JsonUtils;
import com.ecnice.privilege.utils.RandPwdUtils;
import com.ecnice.privilege.vo.privilege.LoginVo;
import com.ecnice.privilege.vo.privilege.PrivilegeVo;
import com.ecnice.tools.common.ReadProperty;
import com.ecnice.tools.common.UUIDGenerator;

/**
 * @Description:用户service实现类
 * @Author:Bruce.Liu
 * @Since:2014年3月31日
 * @Version:1.1.0
浙江蘑菇加电子商务有限公司 2014 ~ 2015 版权所有
 */
@Service
public class UserServiceImpl implements IUserService {
	private static final Logger logger = Logger.getLogger(UserServiceImpl.class);

	@Resource
	private IUserDao userDao;
	@Resource
	private IUserRoleDao userRoleDao;
	@Resource
	private IAclService aclService;
	@Resource
	private IAclDao aclDao;
	@Resource
	private ILoginLogService loginLogService;
	@Resource
	private IModuleService moduleService;
	@Resource
	private IICSystemDao icSystemDao;
	@Resource
	private IDepartmentDao departmentDao;
	@Resource
	private ReadProperty readProperty;

	/**
	 * 通过系统标识得到系统下面的所有的人
	 * 
	 * @param systemSn
	 *            系统标识
	 * @return
	 */
	public List<User> getSystemUsersBySystemSn(String systemSn) {
		return userDao.getSystemUsersBySystemSn(systemSn);
	}

	@Override
	public List<User> getUserByDeptIdAndRoleSn(String deptId, String roleSn) throws PrivilegeException {
		return userDao.getUserByDeptIdAndRoleSn(deptId, roleSn);
	}

	@Override
	public User login(String username, String password) throws Exception {
		return userDao.login(username, password);
	}
	
	@Override
	public void insertUser(User user) throws Exception {
		//1、创建用户
		logger.info("正在创建用户..."+user.getRealName());
		user.setId(UUIDGenerator.generate());
		String pwd = RandPwdUtils.randPassword(4, 2, 2);
		user.setPassword(pwd);
		
		//设置 密码初始化的状态和密码有效期
		SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
		user.setPwdInit(PrivilegeConstant.NO); //初始化密码未修改
		user.setPwdFtime(sdf.parse(sdf.format(DateUtil.addMonth(new Date(), PrivilegeConstant.PWD_ENABLE_MONTH)))); //密码失效日期=当前日期+3个月
		
		userDao.insertUser(user);
//		String systemIds = user.getSystemIds();
//		if (StringUtils.isNotBlank(systemIds)) {
//			String[] syses = systemIds.split(",");
//			for (String sys : syses) {
//				UserSystem userSystem = new UserSystem();
//				userSystem.setSystemId(sys);
//				userSystem.setId(UUIDGenerator.generate());
//				userSystem.setUserId(user.getId());
//				this.userSystemDao.insertUserSystem(userSystem);
//			}
//		}
		logger.info("创建用户成功！【"+user.getRealName()+",pwd="+pwd+"】");
		//2、发送邮件
		logger.info("正在发送邮件..."+user.getEmail());
		Map<String,Object> datas = new HashMap<String, Object>();
		datas.put("realName", user.getRealName());
		datas.put("username", user.getUsername());
		datas.put("pwd", pwd);
		datas.put("sysUrl", readProperty.getValue("privilege.system.url"));
		datas.put("date", new SimpleDateFormat("yyyy年MM月dd日").format(new Date()));
//		EmailInfo emailInfo = new EmailInfo();
//		emailInfo.setConsignees(new String[] { user.getEmail() });
//		emailInfo.setSubject("MOS账号开通");
//		emailInfo.setType(0);
		//this.sendEmailApi.sendEmail("system_account_kt_alert", datas, emailInfo);
		logger.info("发送邮件完成！【"+user.getEmail()+"】");
	}

	@Override
	public void updateUser(User user) throws Exception {
		userDao.updateUser(user);
//		String systemIds = user.getSystemIds();
//		if (StringUtils.isNotBlank(systemIds)) {
//			String[] syses = systemIds.split(",");
//			for (String sys : syses) {
//				UserSystem userSystem = new UserSystem();
//				userSystem.setSystemId(sys);
//				userSystem.setId(UUIDGenerator.generate());
//				userSystem.setUserId(user.getId());
//				this.userSystemDao.insertUserSystem(userSystem);
//			}
//		}
	}

	@Override
	public void insertUserBatch(List<User> users) throws Exception {
		this.userDao.insertUserBatch(users);
	}
	
	@Override
	public int updateUserBatch(List<User> users) throws Exception {
		return this.userDao.updateUserBatch(users);
	}
	
	@Override
	public void delUser(String id) throws Exception {
		userDao.delUser(id);
		this.userRoleDao.delUserRoleByUserId(id);
		this.userRoleDao.delUserRoleByUserId(id);
		ACL acl = new ACL();
		acl.setReleaseId(id);
		acl.setReleaseSn(ACL.USER);
		aclDao.delAcl(acl);
	}

	@Override
	public void delUsers(String[] ids) throws Exception {
		for (String id : ids) {
			this.delUser(id);
		}
	}
	
	/**
	 * 将EHR数据中的性别  1男；2女   ===转换成==》 0男；1女
	 * @param sourceSex
	 * @return
	 * @Description:
	 * @author xietongjian 2017 下午4:04:47
	 */
	private Integer genSex(Integer sourceSex){
		if(null != sourceSex && sourceSex>0){
			sourceSex = sourceSex - 1;
		}
		return sourceSex;
	}
	
	@Override
	public User getUserById(String id) throws Exception {
//		UserSystem userSystem = new UserSystem();
//		userSystem.setUserId(id);
//		List<UserSystem> list = this.userSystemDao.getAll(userSystem);
//		StringBuffer systemIds = new StringBuffer("");
//		if (list != null && list.size() > 0) {
//			for (int i = 0, len = list.size(); i < len; i++) {
//				if (i != 0) {
//					systemIds.append(",");
//				}
//				systemIds.append(list.get(i).getSystemId());
//			}
//		}
		User user = userDao.getUserById(id);
		Department department = this.departmentDao.getDepartmentById(user.getDepartmentId());
		if (department != null) {
			user.setDeptName(department.getName());
		}
//		if (user != null && systemIds.length() > 0) {
//			user.setSystemIds(systemIds.toString());
//			Department department = this.departmentDao.getDepartmentById(user.getDepartmentId());
//			if (department != null) {
//				user.setDeptName(department.getName());
//			}
//		}
		return user;
	}

	@Override
	public User getUserByUserName(String userName) throws Exception {
		User user = this.userDao.getUserByUserName(userName);
		if (user != null) {
			Department department = this.departmentDao.getDepartmentById(user.getDepartmentId());
			if (department != null) {
				user.setDeptName(department.getName());
			}
		}
		return user;
	}

	@Override
	public PagerModel<User> getPagerModel(User user, Query query) throws Exception {
		PagerModel<User> pm = userDao.getPagerModel(user, query);
		for (User ur : pm.getDatas()) {
			List<Role> list = this.userRoleDao.getRolesByUserId(ur.getId());
			if (list != null && list.size() > 0) {
				StringBuffer s = new StringBuffer();
				int idx = 0;
				for (int i = 0, len = list.size(); i < len; i++) {
					if(StringUtils.isNotBlank(list.get(i).getName())){
						if (idx != 0) {
							s.append(PrivilegeConstant.SPECIAL_SEPARATOR);
						}
						idx++;
						s.append(list.get(i).getName());
					}
				}
				ur.setRoles(s.toString());
			}
		}
		return pm;
	}

	@Override
	public List<User> getAll(User user) throws Exception {
		return this.userDao.getAll(user);

	}

	@Override
	public void singleUpdateUser(User user) throws Exception {
		this.userDao.updateUser(user);
	}

	@Override
	public List<User> getUserByRoleSn(String roleSn) throws Exception {
		return this.userDao.getUserByRoleSn(roleSn);
	}

	@Override
	public List<User> getUsersByPrivilegeVo(PrivilegeVo vo) throws Exception {
		return userDao.getUsersByPrivilegeVo(vo);
	}

	@Override
	public List<User> getUserByRoleSns(String... roleSns) throws Exception {
		return this.userDao.getUserByRoleSns(roleSns);
	}

	@Override
	public List<User> getUserByRoleSns(User user, String... roleSns) throws Exception {
		return this.userDao.getUserByRoleSns(user, roleSns);
	}

	@Override
	public List<User> getUsersByDeptId(String deptId) throws Exception {
		return this.userDao.getUsersByDeptId(deptId);
	}

	// 对外提供的登录接口 这个方法暂时不用
	public LoginVo login(String username, String password, String companyId, String ip, String systemSn)
			throws Exception {
		LoginVo vo = new LoginVo();
		SessionMap sessionMap = new SessionMap();
		// 1:查询到用户对象
		User user = login(username, password);
		if (user != null) {
			vo.setLoginUser(user);
			vo.setSid(user.getId());
			sessionMap.put(PrivilegeConstant.LOGIN_USER, JsonUtils.toJson(user));
			Set<ACL> acls = (Set<ACL>) aclService.getAclsByUserId(user.getId());
			sessionMap.put(PrivilegeConstant.LOGIN_USER_ACLS, JsonUtils.toJson(acls));
			ICSystem icSystem = icSystemDao.getICSystemBySn(systemSn);
			if (icSystem != null) {
				List<Module> modules = moduleService.getTreeModuleBySystemIdAndAcls(acls, icSystem.getId());
				vo.setModules(modules);
			} else {
				throw new PrivilegeException("系统标示找不到");
			}
			// 插入登录日志
			LoginLog loginLog = new LoginLog(ip, user.getId(), user.getUsername(), user.getRealName(), "登录");
			loginLogService.insertLoginLog(loginLog);

//			CacheEntity ce = new CacheEntity(user.getId(), sessionMap, PrivilegeConstant.SESSION_OUT_TIME);
//			CacheListHandler.addCache(user.getId(), ce);
		}
		return vo;
	}

	//---api---
	
	@Override
	public List<User> getUserByUserNames(String... userName) {
		return this.userDao.getUserByUserNames(userName);
	}

	@Override
	public void checkFailUser() throws ParseException {
		int pageSize = 200; // 每页大小
		int pageIndex = 0; // 第几页
		long total = 0; // 总记录数
		Query query = new Query();
		query.setPageIndex(pageIndex);
		query.setPageSize(pageSize);
		PagerModel<User> pm = this.userDao.getUserFailTime(new User(), query);
		if (null != pm) {
			total = pm.getTotal();
		}
		this.checkFailUser(pm);
		long page = ((total - 1 + pageSize) / pageSize); // 总页数
		for (int i = 2; i <= page; i++) {
			pageIndex = i;
			query.setPageIndex(pageIndex);
			pm = this.userDao.getUserFailTime(new User(), query);
			this.checkFailUser(pm);
		}
	}

	//检查账户和密码失效发送邮件
	private void checkFailUser(PagerModel<User> pm) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if(null!=pm && CollectionUtils.isNotEmpty(pm.getDatas())){
			for (User user : pm.getDatas()) {
				if (null != user) {
					if (null != user.getFailureTime()) { //账户有效期
						int day = DateUtil.diffDate(sdf.parse(sdf.format(user.getFailureTime())), sdf.parse(sdf.format(new Date())));
						if (day > 0 && day <= PrivilegeConstant.PWD_WARN_DAY) {
							//2、发送邮件
							logger.info("账户有效期：正在发送邮件..."+user.getEmail());
							Map<String,Object> datas = new HashMap<String, Object>();
							datas.put("realName", user.getRealName());
							datas.put("expiredTime",new SimpleDateFormat("yyyy年MM月dd日").format(user.getFailureTime()));
							datas.put("sysUrl", readProperty.getValue("privilege.system.url"));
							datas.put("date", new SimpleDateFormat("yyyy年MM月dd日").format(new Date()));
//							EmailInfo emailInfo = new EmailInfo();
//							emailInfo.setConsignees(new String[] { user.getEmail() });
//							emailInfo.setSubject("MOS账号失效提醒");
//							emailInfo.setType(0);
//							this.sendEmailApi.sendEmail("center_account_expired", datas, emailInfo);
							logger.info("账户有效期：发送邮件完成！【"+user.getEmail()+"】");
						}
					}

					if (null != user.getPwdFtime()) {//密码有效期
						int day = DateUtil.diffDate(sdf.parse(sdf.format(user.getPwdFtime())), sdf.parse(sdf.format(new Date())));
						if (day > 0 && day <= PrivilegeConstant.PWD_WARN_DAY) {
							//2、发送邮件
							logger.info("账户失效：正在发送邮件..."+user.getEmail());
							Map<String,Object> datas = new HashMap<String, Object>();
							datas.put("realName", user.getRealName());
							datas.put("expiredTime",new SimpleDateFormat("yyyy年MM月dd日").format(user.getPwdFtime()));
							datas.put("sysUrl", readProperty.getValue("privilege.system.url"));
							datas.put("date", new SimpleDateFormat("yyyy年MM月dd日").format(new Date()));
//							EmailInfo emailInfo = new EmailInfo();
//							emailInfo.setConsignees(new String[] { user.getEmail() });
//							emailInfo.setSubject("MOS账号密码过期提醒");
//							emailInfo.setType(0);
//							this.sendEmailApi.sendEmail("center_account_pwd_expired", datas, emailInfo);
							logger.info("账户失效：发送邮件完成！【"+user.getEmail()+"】");
						}
					}
				}
			}
		}
	}

	@Override
	public List<User> getUserByIdsList(List<String> ids) throws Exception {
		return userDao.getUserByIdsList(ids);
	}
}
