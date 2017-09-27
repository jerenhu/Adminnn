package com.ecnice.privilege.web.controller.privilege;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ecnice.privilege.common.PagerModel;
import com.ecnice.privilege.common.Permission;
import com.ecnice.privilege.common.Query;
import com.ecnice.privilege.constant.PermissionConatant;
import com.ecnice.privilege.constant.PrivilegeConstant;
import com.ecnice.privilege.model.privilege.Company;
import com.ecnice.privilege.model.privilege.Department;
import com.ecnice.privilege.model.privilege.ICSystem;
import com.ecnice.privilege.model.privilege.Role;
import com.ecnice.privilege.model.privilege.User;
import com.ecnice.privilege.model.privilege.UserRole;
import com.ecnice.privilege.service.privilege.ICompanyService;
import com.ecnice.privilege.service.privilege.IDepartmentService;
import com.ecnice.privilege.service.privilege.IICSystemService;
import com.ecnice.privilege.service.privilege.IRoleService;
import com.ecnice.privilege.service.privilege.IUserRoleService;
import com.ecnice.privilege.service.privilege.IUserService;
import com.ecnice.privilege.utils.DateUtil;
import com.ecnice.privilege.utils.JsonUtils;
import com.ecnice.privilege.utils.WebUtils;
import com.ecnice.privilege.vo.SimpleReturnVo;
import com.ecnice.privilege.web.controller.BaseController;
import com.ecnice.tools.common.MD5Util;

/**
 * 
 * @Description:用户控制器
 * @Author:Martin.Wang
 * @Since:2014-4-2
 * @Version:1.1.0 浙江蘑菇加电子商务有限公司 2014 ~ 2015 版权所有
 */
@Controller
@RequestMapping("/managment/privilege/user")
public class UserController extends BaseController{
	private static Logger logger = Logger.getLogger(UserController.class);
	@Resource
	private IUserService userService;
	@Resource
	private IICSystemService iICSystemService;
	@Resource
	private IUserRoleService userRoleService;
	@Resource
	private ICompanyService companyService;
	@Resource
	private IDepartmentService departmentService;
	@Resource
	private IRoleService roleService;

	/**
	 * 
	 * @return
	 * @throws Exception 
	 * @Description:用户列表页面
	 */
	@RequestMapping("/list")
	@Permission(systemSn="privilege",moduleSn="user",value=PermissionConatant.R)
	public String list(String sessionId,ModelMap model,HttpServletRequest request) throws Exception {
		model.addAttribute("sessionId", sessionId);
		return "/privilege/user_page";
	}
	
	/**
	 * 
	 * @param user
	 * @param query
	 * @param isCompany 1:表示是公司
	 * @return
	 * @Description:用户分页数据
	 */
	@ResponseBody
	@RequestMapping("ajaxlist")
	@Permission(systemSn="privilege",moduleSn="user",value=PermissionConatant.R)
	public String ajaxlist(User user,Query query,String isCompany){
		PagerModel<User> pm=null;
		try{
			if(StringUtils.isBlank(user.getDepartmentId()) || "0".equals(user.getDepartmentId()) || "null".equals(user.getDepartmentId())){
				user.setDepartmentId(null);
			}else{
				String deptIds = departmentService.getChildrenIdsByPid(user.getDepartmentId());
				user.setDepartmentId(deptIds);
			}
			pm=this.userService.getPagerModel(user, query);
			if (null != pm && CollectionUtils.isNotEmpty(pm.getDatas())) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				List<String> companyIds=new ArrayList<String>();
				for (User temp : pm.getDatas()) {
					if (null != temp) {
						companyIds.add(temp.getCompanyId());
						if (null != temp.getFailureTime()) {
							Date failureTime = temp.getFailureTime();
							temp.setFailureTimeStr(sdf.format(failureTime));
							int day = DateUtil.diffDate(sdf.parse(sdf.format(failureTime)), sdf.parse(sdf.format(new Date())));
							if (day <= 0) {
								temp.setFailFlag(1);
							} else {
								temp.setFailFlag(0);
							}
						} else {
							temp.setFailFlag(2);
						}

						if (null != temp.getPwdFtime()) {
							temp.setPwdFtimeStr(sdf.format(temp.getPwdFtime()));
						}
					}
				}
				if(CollectionUtils.isNotEmpty(companyIds)){
					Map<String, Object> companyMap=this.getMapByList(this.companyService.getCompanyByIdsList(companyIds), "getId","getCname");
					for (User temp : pm.getDatas()) {
						if (null != temp && null!=companyMap && null != temp.getCompanyId() && null!=companyMap.get(temp.getCompanyId())) {
							temp.setCompanyName((String)companyMap.get(temp.getCompanyId()));
						}
					}
				}
				
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.debug("UserController-ajaxlist:"+e.getMessage());
		}
		return JsonUtils.getPmJson(pm);
	}
	
	
	/**
	 * 
	 * @return
	 * @Description:重置密码页面
	 */
	@RequestMapping("/rePasswordUI")
	public String rePasswordUI(String sessionId,ModelMap model) {
		model.addAttribute("sessionId", sessionId);
		return "/privilege/user_re_pwd";
	}
	
	public static void main(String[] args) {
		System.err.println("a111111@<>111s$1".matches("(^[a-zA-Z])(?![a-zA-Z]+$).{7,14}$"));
	}
	
	/**
	 * 
	 * @param oldpwd
	 * @param newpwd
	 * @param request
	 * @return
	 * @Description:当前登录用户自己重置密码
	 */
	@ResponseBody
	@RequestMapping("/rePassword")
	public String rePassword(String oldpwd,String newpwd,HttpServletRequest request) {
		SimpleReturnVo vo;
		oldpwd = oldpwd.trim();
		newpwd = newpwd.trim();
		
		if (!newpwd.matches("(^[a-zA-Z])(?![a-zA-Z]+$).{7,14}$")){
			vo = new SimpleReturnVo(this.FAIL, "新密码规则不合法(字母开头，允许8-15字节，不能全是字母)!");
			return JsonUtils.toJson(vo);
		}
		
		if (oldpwd.equals(newpwd)) {
			vo = new SimpleReturnVo(this.FAIL, "原密码和新密码不能相同！");
			return JsonUtils.toJson(vo);
		}
		
		User user = WebUtils.getLoginUser(request);
		String password = MD5Util.getMD5String(PrivilegeConstant.USER_PASSWORD_FRONT+oldpwd);
		if(!password.equals(user.getPassword())){
			vo=new SimpleReturnVo(this.FAIL, "原密码不正确!");
			return JsonUtils.toJson(vo);
		}
		try{
			User newUser = new User();
			
			boolean pwdFlag=true;
			//2、验证密码有效性
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			//0.1 初始化密码未修改
			if (user.getPwdInit().intValue() != PrivilegeConstant.SUCCESS) {// 初始化密码未修改
				pwdFlag = false;
				newUser.setPwdInit(PrivilegeConstant.YES);
			}
			if(pwdFlag){
				//0.2 密码有效期
				newUser.setPwdInit(null);
				newUser.setPwdFtime(sdf.parse(sdf.format(DateUtil.addMonth(new Date(), PrivilegeConstant.PWD_ENABLE_MONTH)))); //密码失效日期=当前日期+3个月
				
				/*int dayPwd = DateUtil.diffDate(sdf.parse(sdf.format(user.getPwdFtime())), sdf.parse(sdf.format(new Date())));
				if (dayPwd <= PrivilegeConstant.PWD_WARN_DAY) {
					newUser.setPwdInit(null);
					newUser.setPwdFtime(sdf.parse(sdf.format(DateUtil.addMonth(new Date(), PrivilegeConstant.PWD_ENABLE_MONTH)))); //密码失效日期=当前日期+3个月
				}*/
			}
			newUser.setId(user.getId());
			password = MD5Util.getMD5String(PrivilegeConstant.USER_PASSWORD_FRONT+newpwd);
			newUser.setPassword(password);
			newUser.setUpdator(user.getUsername());
			this.userService.singleUpdateUser(newUser);
			vo=new SimpleReturnVo(this.SUCCESS, "成功");
		}catch(Exception e){
			e.printStackTrace();
			logger.debug("UserController-rePassword:"+e.getMessage());
			vo=new SimpleReturnVo(this.FAIL, "异常错误！");
		}
		
		return JsonUtils.toJson(vo);
	}
	
	/**
	 * 
	 * @return
	 * @Description:系统管理员修改密码 ui
	 */
	@RequestMapping("/udpatePasswordUI")
	@Permission(systemSn="privilege",moduleSn="user",value=PermissionConatant.U)
	public String udpatePasswordUI(String sessionId,ModelMap model){
		model.addAttribute("sessionId", sessionId);
		return "/privilege/udpate_password";
	}
	
	/**
	 * 
	 * @param request
	 * @return
	 * @Description:系统管理员修改密码
	 */
	@ResponseBody
	@RequestMapping("/updatePassowrd")
	public String updatePassowrd(User user,HttpServletRequest request) {
		SimpleReturnVo vo;
		String password = MD5Util.getMD5String(PrivilegeConstant.USER_PASSWORD_FRONT+user.getPassword());
		try{
			user.setPassword(password);
			
			//2、验证密码有效性
			//User query = this.userService.getUserById(user.getId());
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			//0.2 密码过期
			user.setPwdInit(PrivilegeConstant.NO); //管理员重置密码：设置初始密码未修改
			user.setPwdFtime(sdf.parse(sdf.format(DateUtil.addMonth(new Date(), PrivilegeConstant.PWD_ENABLE_MONTH)))); //密码失效日期=当前日期+3个月
			
			/*int dayPwd = DateUtil.diffDate(sdf.parse(sdf.format(query.getPwdFtime())), sdf.parse(sdf.format(new Date())));
			if (dayPwd <= PrivilegeConstant.PWD_WARN_DAY) {
				user.setPwdInit(PrivilegeConstant.NO); //管理员重置密码：设置初始密码未修改
				user.setPwdFtime(sdf.parse(sdf.format(DateUtil.addMonth(new Date(), PrivilegeConstant.PWD_ENABLE_MONTH)))); //密码失效日期=当前日期+3个月
			}*/
			
			User loginUser = WebUtils.getLoginUser(request);
			user.setUpdator(loginUser.getUsername());
			this.userService.singleUpdateUser(user);
			vo = new SimpleReturnVo(this.SUCCESS, "成功");
		}catch(Exception e){
			e.printStackTrace();
			logger.debug("UserController-updatePassowrd:"+e.getMessage());
			vo=new SimpleReturnVo(this.FAIL, "异常错误！");
		}
		return JsonUtils.toJson(vo);
	}
	
	/**
	 * 
	 * @return
	 * @Description:重置账户有效期
	 */
	@RequestMapping("/updateFailTimeUI")
	public String updateFailTimeUI(String sessionId,ModelMap model) {
		model.addAttribute("sessionId", sessionId);
		return "/privilege/udpate_failtime";
	}

	
	/**
	 * 
	 * @param request
	 * @return
	 * @Description:重置账户有效期
	 */
	@ResponseBody
	@RequestMapping("/updateFailTime")
	public String updateFailTime(User user,HttpServletRequest request) {
		SimpleReturnVo vo;
		try{
			//设置失效日期
			this.setFailTime(user);
			
			User updateUser = new User();
			updateUser.setId(user.getId());
			updateUser.setFailMonth(user.getFailMonth());
			updateUser.setFailureTime(user.getFailureTime()); //账户有效期
			User loginUser = WebUtils.getLoginUser(request);
			updateUser.setUpdator(loginUser.getUsername());
			this.userService.singleUpdateUser(updateUser);
			vo = new SimpleReturnVo(this.SUCCESS, "成功");
		}catch(Exception e){
			e.printStackTrace();
			logger.debug("UserController-updateFailTime:",e);
			vo=new SimpleReturnVo(this.FAIL, "异常错误！");
		}
		return JsonUtils.toJson(vo);
	}
	
	
	/**
	 * 
	 * @return
	 * @Description:添加用户页面
	 */
	@RequestMapping("/insertUI")
	@Permission(systemSn="privilege",moduleSn="user",value=PermissionConatant.C)
	public String insertUI(String sessionId,ModelMap model){
		model.addAttribute("sessionId", sessionId);
		return "/privilege/user_update";
	}
	
	/**
	 * 
	 * @return
	 * @Description:用户修改页面
	 */
	@RequestMapping("/updateUI")
	@Permission(systemSn="privilege",moduleSn="user",value=PermissionConatant.U)
	public String updateUI(String sessionId,ModelMap model,String userId){
		model.addAttribute("sessionId", sessionId);
		model.addAttribute("userId", userId);
		this.setModel(model, userId);
		return "/privilege/user_update";
	}
	
	/**
	 * 
	 * @return
	 * @Description:详情页面
	 */
	@RequestMapping("/detailUI")
	@Permission(systemSn="privilege",moduleSn="user",value=PermissionConatant.R)
	public String detailUI(String sessionId,ModelMap model,String userId){
		model.addAttribute("sessionId", sessionId);
		model.addAttribute("userId", userId);
		this.setModel(model, userId);
		return "/privilege/user_detail";
	}
	private void setModel(ModelMap model, String userId) {
		try {
			User user=this.userService.getUserById(userId);
			if(null!=user && null!=user.getFailureTime()){
				model.put("failTime", new SimpleDateFormat("yyyy-MM-dd").format(user.getFailureTime()));
			}
		} catch (Exception e) {
			logger.error(e);
		}
	}
	
	/**
	 * 
	 * @param userId
	 * @param model
	 * @return
	 * @Description:分配角色页面
	 */
	@RequestMapping("/insertRoleUI")
	@Permission(systemSn="privilege",moduleSn="user",value=PermissionConatant.C)
	public String insertRoleUI(ModelMap model,String id,String companyId,String sessionId, String personNo){
		model.addAttribute("sessionId", sessionId);
		model.put("id", id);
		model.put("personNo", personNo);
		model.put("companyId", companyId);
		UserRole userRole = new UserRole();
		try {
			userRole.setUserId(id);
			List<UserRole> userRoles = userRoleService.getAll(userRole);
			for (UserRole pr : userRoles) {
				pr.setCreateTime(null);
				pr.setCreator(null);
				pr.setUpdateTime(null);
				pr.setUpdator(null);
				pr.setDelFlag(null);
			}
			model.put("personRoles", JsonUtils.toJson(userRoles));
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("获取用户角色异常！" + e);
		}
		return "/privilege/user_role";
	}
	
	/**
	 * 
	 * @param user
	 * @return
	 * @Description:添加用户
	 */
	@ResponseBody
	@RequestMapping("/insert")
	@Permission(systemSn="privilege",moduleSn="user",value=PermissionConatant.C)
	public String insert(User user,HttpServletRequest request){
		SimpleReturnVo vo;
		try{
			User loginUser = WebUtils.getLoginUser(request);
			String loginUserName=loginUser.getUsername();
			user.setCreator(loginUserName);
			user.setUpdator(loginUserName);
			//设置失效日期
			this.setFailTime(user);
			this.userService.insertUser(user);
			vo=new SimpleReturnVo(this.SUCCESS, "成功");
		}catch(Exception e){
			e.printStackTrace();
			logger.debug("UserController-insert:"+e.getMessage());
			vo=new SimpleReturnVo(this.FAIL, "异常");
		}
		return JsonUtils.toJson(vo);
	}
	
	/**
	 * 
	 * @param id
	 * @return
	 * @Description:根据id获取用户信息
	 */
	@ResponseBody
	@RequestMapping("/ajaxUpdate")
	@Permission(systemSn="privilege",moduleSn="user",value=PermissionConatant.U)
	public String ajaxUpdate(String id){
		User user=null;
		try{
			user=this.userService.getUserById(id);
		}catch(Exception e){
			e.printStackTrace();
			logger.debug("UserController-ajaxUpdate:"+e.getMessage());
		}
		return JsonUtils.toJson(user);
	}
	
	/**
	 * 
	 * @param user
	 * @return
	 * @Description:更新用户
	 */
	@ResponseBody
	@RequestMapping("/update")
	@Permission(systemSn="privilege",moduleSn="user",value=PermissionConatant.U)
	public String update(User user,HttpServletRequest request){
		SimpleReturnVo vo;
		try{
			User loginUser = WebUtils.getLoginUser(request);
			String loginUserName=loginUser.getUsername();
			User query = this.userService.getUserById(user.getId());
			if (null != query.getFailMonth() && null != user.getFailMonth()){
				if(query.getFailMonth().intValue() == user.getFailMonth().intValue()){
				}else{
					//设置失效日期
					this.setFailTime(user);
				}
			}
			user.setUpdator(loginUserName);
			this.userService.updateUser(user);
			vo=new SimpleReturnVo(this.SUCCESS, "成功");
		}catch(Exception e){
			e.printStackTrace();
			logger.debug("UserController-update:"+e.getMessage());
			vo=new SimpleReturnVo(this.FAIL, "异常");
		}
		return JsonUtils.toJson(vo);
	}
	
	/**设置失效日期*/
	private void setFailTime(User user) throws ParseException {
		SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
		user.setFailureTime(sdf.parse(sdf.format(DateUtil.addMonth(new Date(), user.getFailMonth().intValue()))));
	}
	
	/**
	 * 
	 * @param ids id，id,id 
	 * @return
	 * @Description:根据用户id批量删除用户
	 */
	@ResponseBody
	@RequestMapping("/delete")
	@Permission(systemSn="privilege",moduleSn="user",value=PermissionConatant.D)
	public String delete(String ids){
		SimpleReturnVo vo;
		try{
			if(StringUtils.isNotBlank(ids)){
				String [] id=ids.split(",");
				this.userService.delUsers(id);
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.debug("UserController-delete:"+e.getMessage());
			vo=new SimpleReturnVo(this.FAIL, "异常");
		}
		vo=new SimpleReturnVo(this.SUCCESS, "成功");
		return JsonUtils.toJson(vo);
	}
	
	/**
	 * 
	 * @param User
	 * @return
	 * @Description:检测用户名的唯一性
	 */
	@ResponseBody
	@RequestMapping("/checkUserNameExsits")
	public String checkUserNameExsits(User User){
		try{
			User param=new User();
			//param.setDelFlag(PrivilegeConstant.NO_DELETE_FLAG);
			param.setUsername(User.getUsername());
			List<User> list=this.userService.getAll(param);
			if(StringUtils.isNotBlank(User.getId())){
				User u = this.userService.getUserById(User.getId());
				if(u!=null && u.getUsername().equals(User.getUsername())){
					return "0";
				}else{
					if(list!=null && list.size()>0){
						return "1";
					}
				}
			}else{
				if(list!=null && list.size()>0){
					return "1";
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.debug("UserController-checkUserNameExsits:"+e.getMessage());
		}
		return "0";
	}
	
	/**
	 * 
	 * @return
	 * @Description:获取所有系统列表
	 */
	@ResponseBody
	@RequestMapping("/getAllSystems")
	@Permission(systemSn="privilege",moduleSn="user",value=PermissionConatant.R)
	public String getAllSystems(){
		List<ICSystem> sy=null;
		try {
			sy=this.iICSystemService.getAllIcSystem(null);
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug("UserController-getAllSystems:"+e.getMessage());
		}
		return JsonUtils.toJson(sy);
	}
	
	/**
	 * 
	 * @param userId
	 * @return
	 * @Description:查询所有角色。标记用户已经拥有的角色
	 */
	@ResponseBody
	@RequestMapping("/getRoles")
	@Permission(systemSn="privilege",moduleSn="user",value=PermissionConatant.R)
	public String getRoles(String userId, Role role, Query query) {
		PagerModel<Role> pm = new PagerModel<Role>();
		List<Role> roles = null;
		List<Role> uroles = null;
		try {
			pm = this.roleService.getPagerModel(role, query);
			if (pm != null && CollectionUtils.isNotEmpty(pm.getDatas())) {
				roles = pm.getDatas();
				uroles = this.userRoleService.getRolesByUserId(userId);
				if (uroles != null && uroles.size() > 0) {
					for (Role ur : uroles) {
						for (Role r : roles) {
							if (ur.getId().equals(r.getId())) {
								r.setChecked(true);
								break;
							}
						}
					}
				}
				pm.setRows(pm.getDatas());
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug("UserController-getRoles:" + e.getMessage());
		}
		return JsonUtils.toJson(pm);
	}
	
	/**
	 * 
	 * @param userId
	 * @return
	 * @Description:根据用户id获取该用户的所有角色
	 */
	@ResponseBody
	@RequestMapping("/getRoleByUserId")
	@Permission(systemSn="privilege",moduleSn="user",value=PermissionConatant.R)
	public String getRoleByUserId(String userId){
		List<Role> roles=null;
		try {
			roles=this.userRoleService.getRolesByUserId(userId);
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug("UserController-getRoleByUserId:"+e.getMessage());
		}
		return JsonUtils.toJson(roles);
	}
	/**
	 * 
	 * @param userId
	 * @param roleIds
	 * @return
	 * @Description:添加用户角色
	 */
	@ResponseBody
	@RequestMapping("/saveUserRole")
	@Permission(systemSn="privilege",moduleSn="user",value=PermissionConatant.C)
	public String saveUserRole(String userId, String userNo, String roleIds, HttpServletRequest request){
		SimpleReturnVo vo;
		try {
			User user = this.getLoginUser(request);
			if(StringUtils.isNotBlank(roleIds)){
				this.userRoleService.insertUserRoles(roleIds.split(","), userId, userNo, user.getUsername());
			}else{
				this.userRoleService.insertUserRoles(userId, userNo, user.getUsername());
			}
			vo=new SimpleReturnVo(this.SUCCESS, "操作成功！");
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug("UserController-saveUserRole:"+e.getMessage());
			vo=new SimpleReturnVo(this.FAIL, "操作出现异常！");
		}
		return JsonUtils.toJson(vo);
	}
	
	/**
	 * 【没用】
	 * @param userId
	 * @param failureTime
	 * @return
	 * @Description:设置过期时间
	 */
	@ResponseBody
	@RequestMapping("/setFailureTime")
	@Permission(systemSn="privilege",moduleSn="user",value=PermissionConatant.D)
	public String setFailureTime(String userId, Date failureTime){
		SimpleReturnVo vo;
		try {
			if(StringUtils.isNotBlank(userId) && failureTime!=null){
				User user = userService.getUserById(userId);
				user.setFailureTime(failureTime);
				userService.updateUser(user);
			}
			vo=new SimpleReturnVo(this.SUCCESS, "成功");
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug("UserController-saveUserRole:"+e.getMessage());
			vo=new SimpleReturnVo(this.FAIL, "异常");
		}
		return JsonUtils.toJson(vo);
	}
	
//	@ResponseBody
//	@RequestMapping("/getUserSystemIds")
//	@Permission(systemSn="privilege",moduleSn="user",value=PermissionConatant.R)
//	public String getUserSystemIds(String userId){
//		List<ICSystem> sy=null;

//		UserSystem userSystem = new UserSystem();
//		userSystem.setUserId(userId);
//		List<UserSystem> list;
//		try {
//			sy=this.iICSystemService.getAllIcSystem(null);
//			if(sy!=null && sy.size()>0 && StringUtils.isNotBlank(userId)){
//				list = this.userSystemDao.getAll(userSystem);
//				if(list!=null && list.size()>0){
//					for(UserSystem us : list){
//						for(ICSystem ic : sy){
//							if(us.getSystemId().equals(ic.getId())){
//								ic.setChecked(true);
//								break;
//							}
//						}
//					}
//				}
//			}
//		} catch (Exception e) {
//			logger.error(e);
//		}
//		return JsonUtils.toJson(sy);
//	}
	
	//得到失效时间
	@ResponseBody
	@RequestMapping("/getFailTime")
	@Permission(systemSn="privilege",moduleSn="user",value=PermissionConatant.R)
	public String getFailTime(Integer month) {
		Date date = null;
		try {
			if (null != month) {
				date = DateUtil.addMonth(new Date(), month.intValue());
			}
		} catch (Exception e) {
			logger.error(e);
		}
		return JsonUtils.toJson(null != date ? new SimpleDateFormat("yyyy-MM-dd").format(date) : "");
	}
	
	//立即失效
	@ResponseBody
	@RequestMapping("/setFailure")
	@Permission(systemSn="privilege",moduleSn="user",value=PermissionConatant.U)
	public String setFailure(String userId) {
		SimpleReturnVo vo = new SimpleReturnVo(0,"立即失效失败");
		try {
			if(StringUtils.isNotBlank(userId)){
				User user = userService.getUserById(userId);
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				user.setFailureTime(sdf.parse(sdf.format(new Date())));
				userService.updateUser(user);
				vo = new SimpleReturnVo(1, "立即失效成功");
			}
		} catch (Exception e) {
			logger.error(e);
		}
		return JsonUtils.toJson(vo);
	}
	
	/**
	 * 通过List得到Map
	 * @param lists List集合
	 * @param methodNames 【methods.length==1:key:默认为 getSn,value:Object;methods.length!=1:key:默认为 getSn,value:默认为 getCode】
	 * @return key:默认为 getSn,value:Object
	 * @throws NoSuchMethodException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @Description:
	 * @author wentaoxiang 2016年7月11日 下午4:00:28
	 */
	public static Map<String, Object> getMapByList(List<?> lists, String... methodNames) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException{
		boolean methodsFlag = false;
		if (ArrayUtils.isNotEmpty(methodNames)) {
			methodsFlag = true;
		}
		Map<String, Object> map = getMapInfo(methodsFlag, lists, methodNames);
		return MapUtils.isNotEmpty(map) ? map : null;
	}
	/**
	 * 转成map
	 * @param methodsFlag
	 * @param objs
	 * @param methodNames 【methods.length==1:key:默认为 getSn,value:Object;methods.length!=1:key:默认为 getSn,value:默认为 getCode】
	 * @return key:默认为 getSn,value:Object
	 * @throws NoSuchMethodException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @Description:
	 * @author wentaoxiang 2016年7月11日 下午4:00:02
	 */
	private static Map<String, Object> getMapInfo(boolean methodsFlag,List<?> objs, String... methodNames)
			throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		if (CollectionUtils.isEmpty(objs)) {
			return null;
		}
		Map<String, Object> map = new TreeMap<String, Object>();
		for (Object obj : objs) {
			Class<? extends Object> cls = obj.getClass();
			if (null != methodNames && methodNames.length == 1) {
				Method getSnMtd = cls.getMethod(methodsFlag && StringUtils.isNotBlank(methodNames[0]) ? methodNames[0].trim() : "getSn");
				String sn = getSnMtd.invoke(obj).toString();
				if (StringUtils.isNotBlank(sn)) {
					map.put(sn.trim(), obj);
				}
			} else {
				Method getSnMtd = cls.getMethod(methodsFlag && StringUtils.isNotBlank(methodNames[0]) ? methodNames[0].trim() : "getSn");
				Method getCodeMtd = cls.getMethod(methodsFlag && StringUtils.isNotBlank(methodNames[1]) ? methodNames[1].trim() : "getCode");
				String sn = getSnMtd.invoke(obj).toString();
				Object code = getCodeMtd.invoke(obj);

				if (StringUtils.isNotBlank(sn) && null != code) {
					map.put(sn.trim(), code);
				}
			}
		}
		return map;
	}
	
	/**
	 * 设置公司和部门数据
	 * @param user
	 * @Description:
	 * @author xietongjian 2017 下午4:07:24
	 * @param companyMap 
	 */
	private void genCompanyDept(User user,Company com, String deptCode){
		//设置公司ID，name；设置部门ID，name
		try {
			if(null != com){
				user.setCompanyName(com.getCname());
				user.setCompanyId(com.getId());
			
				Department deptQuery = new Department();
				deptQuery.setCode(deptCode);
				deptQuery.setCompanyId(com.getId());
				List<Department> dept = departmentService.getAll(deptQuery);
				if(CollectionUtils.isNotEmpty(dept)){
					user.setDeptName(dept.get(0).getName());
					user.setDepartmentId(dept.get(0).getId());
				}
			}else{
				logger.info("通过公司编码【"+com+"】未找到公司数据！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("通过公司ID和部门编码查询部门数据异常！");
		}
	}
}
