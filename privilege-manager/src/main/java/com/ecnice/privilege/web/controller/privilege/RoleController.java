package com.ecnice.privilege.web.controller.privilege;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
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
import com.ecnice.privilege.model.privilege.ACL;
import com.ecnice.privilege.model.privilege.Company;
import com.ecnice.privilege.model.privilege.ICSystem;
import com.ecnice.privilege.model.privilege.Role;
import com.ecnice.privilege.model.privilege.RoleCompany;
import com.ecnice.privilege.model.privilege.User;
import com.ecnice.privilege.model.system.Dictionary;
import com.ecnice.privilege.service.privilege.IAclService;
import com.ecnice.privilege.service.privilege.ICompanyService;
import com.ecnice.privilege.service.privilege.IDepartmentService;
import com.ecnice.privilege.service.privilege.IICSystemService;
import com.ecnice.privilege.service.privilege.IRoleCompanyService;
import com.ecnice.privilege.service.privilege.IRoleService;
import com.ecnice.privilege.service.privilege.IUserRoleService;
import com.ecnice.privilege.service.privilege.IUserService;
import com.ecnice.privilege.service.system.IDictionaryService;
import com.ecnice.privilege.utils.CommUtils;
import com.ecnice.privilege.utils.JsonUtils;
import com.ecnice.privilege.vo.ReturnVo;
import com.ecnice.privilege.vo.SimpleReturnVo;
import com.ecnice.privilege.web.controller.BaseController;
import com.ecnice.tools.common.ReadProperty;

/**
 * @Description:角色管理
 * @Author:Martin.Wang
 * @Since:2014-4-1
 * @Version:1.1.0 浙江蘑菇加电子商务有限公司 2014 ~ 2015 版权所有
 */
@Controller
@RequestMapping("/managment/privilege/role")
public class RoleController extends BaseController {
	private static Logger logger = Logger.getLogger(RoleController.class);
	@Resource
	private IRoleService roleService;
	@Resource
	private IUserRoleService userRoleService;
	@Resource
	private IDepartmentService departmentService;
	@Resource
	private IUserService userService;
	@Resource
	private IDictionaryService dictionaryService;
	@Resource
	private IRoleCompanyService roleCompanyService;
	@Resource
	private ReadProperty readProperty;
	@Resource
	private IICSystemService icSystemService;
	@Resource
	private IAclService aclService;
	@Resource
	private ICompanyService companyService;
	
	/**
	 * 
	 * @return
	 * @Description:跳转到角色列表页面
	 */
	@RequestMapping("/list")
	@Permission(systemSn="privilege",moduleSn="role",value=PermissionConatant.R)
	public String list(String sessionId,ModelMap model) {
		model.addAttribute("sessionId", sessionId);
		List<Dictionary> roleLevels = new ArrayList<Dictionary>();
		List<ICSystem> systems = new ArrayList<ICSystem>();
		try {
			Dictionary dictionary = new Dictionary();
			dictionary.setPcode(readProperty.getValue(PrivilegeConstant.ROLE_LEVELS_SN));
			roleLevels = dictionaryService.getAll(dictionary);
			
			//获取所有系统
			ICSystem icSystem = new ICSystem();
			icSystem.setDelFlag(PrivilegeConstant.NO_DELETE_FLAG);
			systems = icSystemService.getAllIcSystem(icSystem);
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.put("systemsJson", JsonUtils.toJson(systems));
		model.put("roleLevelsJson", JsonUtils.toJson(roleLevels));
		return "/privilege/role_page";
	}

	/**
	 * 
	 * @param role
	 * @param query
	 * @return
	 * @Description:角色列表分页数据
	 */
	@ResponseBody
	@RequestMapping("/ajaxlist")
	@Permission(systemSn="privilege",moduleSn="role",value=PermissionConatant.R)
	public String ajaxlist(Role role, Query query) {
		PagerModel<Role> roles = null;
		try {
			roles = this.roleService.getPagerModel(role, query);
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug("RoleController-ajaxlist:" + e.getMessage());
		}
		return JsonUtils.getPmJson(roles);
	}
	
	/**
	 * 显示角色列表
	 * @param model
	 * @param role
	 * @param querycontent
	 * @param query
	 * @param sessionId
	 * @param showRoleUser
	 * @return
	 * @Description:
	 * @author xietongjian 2017 下午3:29:48
	 */
	@ResponseBody
	@RequestMapping("/deleteRoleUser")
	@Permission(systemSn="privilege",moduleSn="role",value=PermissionConatant.D)
	public String deleteRoleUser(String roleId, String userNo, String sessionId){
		SimpleReturnVo vo = new SimpleReturnVo(PrivilegeConstant.ERROR, "删除失败！");
		try {
			if(StringUtils.isNotBlank(userNo) && StringUtils.isNotBlank(roleId)){
				int result = this.userRoleService.deleteUserRoleByUserRoleId(userNo, roleId);
				if(result > 0){
					vo.setResponseCode(PrivilegeConstant.SUCCESS);
					vo.setResponseMsg("删除成功！");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return JsonUtils.toJson(vo);
	}
	
	
	@ResponseBody
	@RequestMapping("/deleteRoleCompany")
	@Permission(systemSn="privilege",moduleSn="role",value=PermissionConatant.D)
	public String deleteRoleCompany(String roleId, String cId, String sessionId){
		SimpleReturnVo vo = new SimpleReturnVo(PrivilegeConstant.ERROR, "删除失败！");
		try {
			if(StringUtils.isNotBlank(cId) && StringUtils.isNotBlank(roleId)){
				int result = this.roleCompanyService.deleteRoleCompanyByRoleCompanyId(cId, roleId);
				if(result > 0){
					vo.setResponseCode(PrivilegeConstant.SUCCESS);
					vo.setResponseMsg("删除成功！");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return JsonUtils.toJson(vo);
	}
	
	/**
	 * 显示角色列表
	 * @param model
	 * @param role
	 * @param querycontent
	 * @param query
	 * @param sessionId
	 * @param showRoleUser
	 * @return
	 * @Description:
	 * @author xietongjian 2017 下午3:29:48
	 */
	@ResponseBody
	@RequestMapping("/getRolesData")
	public String getRolesData(ModelMap model, Role role, String querycontent,Query query,String sessionId, Integer showRoleUser){
		PagerModel<Role> rolepm = new PagerModel<Role>();
		try {
			role.setDelFlag(PrivilegeConstant.NO_DELETE_FLAG);
//			if(StringUtils.isNotBlank(role.getCompanyId())){
//				List<String> childDeptIds  = new ArrayList<String>();
//				childDeptIds = this.departmentService.getChildrenDeptIdsByDeptId(role.getCompanyId());
//				role.setCompanyIdList(childDeptIds);
//				role.setCompanyId(null);
//			}
			rolepm = roleService.getPagerModelByQuery(role, query);
			if(null != showRoleUser && PrivilegeConstant.YES == showRoleUser.intValue() && CollectionUtils.isNotEmpty(rolepm.getDatas())){
				List<Role> roles = rolepm.getDatas();
				for (Role role2 : roles) {
					if(CommUtils.isNotEmpty(role2.getPersonids())){
						if(null!=role2.getPersonids()){
							List<User> ps = userService.getUserByIdsList(Arrays.asList(role2.getPersonids().split(",")));
							if(CollectionUtils.isNotEmpty(ps)){
								String names = "";
								for (int i = 0; i < ps.size(); i++) {
									names += PrivilegeConstant.SPECIAL_SEPARATOR + ps.get(i).getUsername() + PrivilegeConstant.SEPARATOR + ps.get(i).getRealName();
								}
								role2.setUsernames(names);
							}
						}
					}
					if(CommUtils.isNotEmpty(role2.getCompanyIds())){
						if(null!=role2.getCompanyIds()){
							List<Company> cpn = companyService.getCompanyByIdsList(Arrays.asList(role2.getCompanyIds().split(",")));
							if(CollectionUtils.isNotEmpty(cpn)){
								String names = "";
								for (int i = 0; i < cpn.size(); i++) {
									names += PrivilegeConstant.SPECIAL_SEPARATOR + cpn.get(i).getId() + PrivilegeConstant.SEPARATOR + cpn.get(i).getCname();
								}
								role2.setCompanyNames(names);
							}
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error(this + "Controller-getPersonRoleData:", e);
			e.printStackTrace();
		}
		return JsonUtils.getPmJson(rolepm);
	}
	
	/**
	 * 
	 * @param role
	 * @param userId
	 * @param query
	 * @return
	 * @Description:用户分配角色列表
	 */
	@ResponseBody
	@RequestMapping("/getRoleByUserId")
	@Permission(systemSn="privilege",moduleSn="role",value=PermissionConatant.R)
	public String getRoleByUserId(Role role, String userId, Query query) {
		PagerModel<Role> roles = null;
		try {
			List<Role> userRoles = this.userRoleService
					.getRolesByUserId(userId);
			roles = this.roleService.getPagerModel(role, query);
			if (roles != null) {
				List<Role> allRoles = roles.getDatas();
				if (allRoles != null && allRoles.size() > 0) {
					if (userRoles != null && userRoles.size() > 0) {
						for (Role ur : userRoles) {
							for (Role r : allRoles) {
								if (ur.getId().equals(r.getId())) {
									r.setStatus(1);
									break;
								}
							}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug("RoleController-ajaxlist:" + e.getMessage());
		}
		return JsonUtils.getPmJson(roles);
	}

	/**
	 * 设置数据到模型中
	 * @param model
	 * @Description:
	 * @author xietongjian 2017 下午1:45:44
	 */
	private void setData2ModelMap(ModelMap model, String companyId){
		try {
			Company company = this.companyService.getCompanyById(companyId);
			model.put("company", company);
			
			//从数据字典获取角色等级
			Dictionary dictionary = new Dictionary();
			dictionary.setPcode(readProperty.getValue(PrivilegeConstant.ROLE_LEVELS_SN));
			List<Dictionary> roleLevels = dictionaryService.getAll(dictionary);
			model.put("roleLevels", roleLevels);
			
			//获取所有系统
			ICSystem icSystem = new ICSystem();
			icSystem.setDelFlag(PrivilegeConstant.NO_DELETE_FLAG);
			List<ICSystem> systems = icSystemService.getAllIcSystem(icSystem);
			model.put("systems", systems);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @return
	 * @Description:跳转到添加角色页面
	 */
	@RequestMapping("/insertUI")
	@Permission(systemSn="privilege",moduleSn="role",value=PermissionConatant.C)
	public String insertUI(String sessionId,ModelMap model, String companyId) {
		model.addAttribute("sessionId", sessionId);
		setData2ModelMap(model, companyId);
		return "/privilege/role_insert";
	}

	/**
	 * @return
	 * @Description:跳转到更新角色页面
	 */
	@RequestMapping("/updateUI")
	@Permission(systemSn="privilege",moduleSn="role",value=PermissionConatant.U)
	public String updateUI(String sessionId,String roleId, ModelMap model, String companyId) {
		model.addAttribute("sessionId", sessionId);
		setData2ModelMap(model, companyId);
		try {
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/privilege/role_update";
	}
	
	/**
	 * @return
	 * @Description:跳转到分配公司页面
	 */
	@RequestMapping("/settingCompanyUI")
	@Permission(systemSn="privilege",moduleSn="role",value=PermissionConatant.U)
	public String settingCompanyUI(String sessionId,ModelMap model, String roleId) {
		model.addAttribute("sessionId", sessionId);
		try {
			RoleCompany roleCompany = new RoleCompany();
			roleCompany.setRoleId(roleId);
			List<RoleCompany> roleCompanys = this.roleCompanyService.getAll(roleCompany);
			model.put("roleCompanysJson", JsonUtils.toJson(roleCompanys));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "/privilege/role_setting_company";
	}
	
	/**
	 * @param role
	 * @return
	 * @Description:修改角色管理的公司
	 */
	@ResponseBody
	@RequestMapping("/settingCompany")
	@Permission(systemSn="privilege",moduleSn="role",value=PermissionConatant.C)
	public String settingCompany(String roleId, String companyIds, HttpServletRequest request) {
		SimpleReturnVo vo;
		try {
			User user = this.getLoginUser(request);
			if(StringUtils.isNotBlank(roleId)){
				//删除已经分配的所有公司
				RoleCompany roleCompany = new RoleCompany();
				roleCompany.setRoleId(roleId);
				List<RoleCompany> roleCompanys = roleCompanyService.getAll(roleCompany);
				if(CollectionUtils.isNotEmpty(roleCompanys)){
					String ids = "";
					for (int i = 0; i< roleCompanys.size(); i++) {
						ids += "," + roleCompanys.get(i).getId();
					}
					this.roleCompanyService.delRoleCompanyByIds(ids.substring(1));
				}
				
				//把现有的选择的加入进去
				if(StringUtils.isNotBlank(companyIds)){
					String[] compnayIdArr = companyIds.split(",");
					for (String companyId : compnayIdArr) {
						RoleCompany rcObj = new RoleCompany();
						rcObj.setCompanyId(companyId);
						rcObj.setRoleId(roleId);
						rcObj.setCreator(user.getUsername());
						rcObj.setUpdator(user.getUsername());
						rcObj.setDelFlag(PrivilegeConstant.NO_DELETE_FLAG);
						this.roleCompanyService.insertRoleCompany(rcObj);
					}
				}
			}
			vo = new SimpleReturnVo(this.SUCCESS, "成功");
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug("RoleController-insert:" + e.getMessage());
			vo = new SimpleReturnVo(this.FAIL, "异常");
		}
		return JsonUtils.toJson(vo);
	}
	
	/**
	 * 
	 * @param role
	 * @return
	 * @Description:添加角色信息
	 */
	@ResponseBody
	@RequestMapping("/insert")
	@Permission(systemSn="privilege",moduleSn="role",value=PermissionConatant.C)
	public String insert(Role role, String roleCompanyIdsStr, HttpServletRequest request) {
		SimpleReturnVo vo;
		try {
			User user = this.getLoginUser(request);
			role.setCreator(user.getUsername());
			role.setUpdator(user.getUsername());
			this.roleService.insertRole(role);
			vo = new SimpleReturnVo(this.SUCCESS, "成功");
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug("RoleController-insert:" + e.getMessage());
			vo = new SimpleReturnVo(this.FAIL, "异常");
		}
		return JsonUtils.toJson(vo);
	}

	/**
	 * 
	 * @param id
	 * @return
	 * @Description:根据id获取角色信息
	 */
	@ResponseBody
	@RequestMapping("/ajaxUpdate")
	@Permission(systemSn="privilege",moduleSn="role",value=PermissionConatant.U)
	public String ajaxUpdate(String id) {
		Role role = null;
		try {
			role = this.roleService.getRoleById(id);
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug("RoleController-ajaxUpdate:" + e.getMessage());
		}
		return JsonUtils.toJson(role);
	}

	/**
	 * 
	 * @param role
	 * @return
	 * @Description:更新角色信息
	 */
	@ResponseBody
	@RequestMapping("/update")
	@Permission(systemSn="privilege",moduleSn="role",value=PermissionConatant.U)
	public String update(Role role, String roleCompanyIdsStr, HttpServletRequest request) {
		SimpleReturnVo vo;
		try {
			User user = this.getLoginUser(request);
			// 1修改时判断是否修改了系统，如果修改了系统则删除原来的系统的所有的权限
			Role persistent = this.roleService.getRoleById(role.getId());
			if(StringUtils.isNotBlank(persistent.getSystemId()) && !role.getSystemId().equals(persistent.getSystemId())){
				ICSystem sysSn = this.icSystemService.getICSystemById(persistent.getSystemId());
				ACL dacl = new ACL();
				dacl.setReleaseId(persistent.getId());
		        dacl.setSystemSn(sysSn.getSn());
				aclService.createAllAcl(dacl, false);
			}
			role.setUpdator(user.getUsername());
			this.roleService.updateRole(role);
			vo = new SimpleReturnVo(this.SUCCESS, "成功");
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug("RoleController-update:" + e.getMessage());
			vo = new SimpleReturnVo(this.FAIL, "异常");
		}
		return JsonUtils.toJson(vo);
	}

	/**
	 * 
	 * @param ids id,id,id
	 * @return
	 * @Description:删除角色信息
	 */
	@ResponseBody
	@RequestMapping("/delete")
	@Permission(systemSn="privilege",moduleSn="role",value=PermissionConatant.D)
	public String delete(String ids) {
		SimpleReturnVo vo;
		try {
			if (StringUtils.isNotBlank(ids)) {
				String[] id = ids.split(",");
				this.roleService.delRoles(id);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug("RoleController-delete:" + e.getMessage());
			vo = new SimpleReturnVo(this.FAIL, "异常");
		}
		vo = new SimpleReturnVo(this.SUCCESS, "成功");
		return JsonUtils.toJson(vo);
	}

	/**
	 * 
	 * @param role
	 * @return
	 * @Description:检测角色标识的唯一性
	 */
	@ResponseBody
	@RequestMapping("/checkSnExsits")
	public String checkSnExsits(Role role) {
		Role r=new Role();
		r.setSn(role.getSn());
		try {
			List<Role> list=this.roleService.getAll(r);
			if(StringUtils.isNotBlank(role.getId())){//更新的时候
				Role ro=this.roleService.getRoleById(role.getId());
				if(ro.getSn().equals(role.getSn())){
					return "0";
				}else{
					if(list!=null && list.size()>0){
						return "1";
					}
				}
			}else{//新增的时候
				if(list!=null && list.size()>0){
					return "1";
				}
			}
		} catch (Exception e) {
			logger.debug("RoleController-checkSnExsits:" + e.getMessage());
			e.printStackTrace();
		}
		return "0";
	}
	
	/**
	 * 修改有效状态
	 * @param roleId
	 * @param validState
	 * @return
	 * @Description:
	 * @author xietongjian 2017年5月31日 下午12:34:14
	 */
	@ResponseBody
	@RequestMapping("/changeValidState")
	@Permission(systemSn="privilege",moduleSn="role",value=PermissionConatant.U)
	public String changeValidState(Role role, String sessionId, HttpServletRequest request) {
		ReturnVo<String> returnVo = new ReturnVo<String>(FAIL+"", "操作失败！");
		try {
			User user = this.getLoginUser(request);
			if(null == user){
				returnVo.setMessage("获取登录用户信息异常！");
				return JsonUtils.toJson(returnVo);
			}
			if(StringUtils.isNotBlank(role.getId()) && null != role.getValidState()){ 
				if(PrivilegeConstant.YES == role.getValidState().intValue()){
					role.setValidState(PrivilegeConstant.YES);
					role.setUpdator(user.getUsername());
					this.roleService.updateValidState(role);
				}else if(PrivilegeConstant.NO == role.getValidState().intValue()){
					role.setValidState(PrivilegeConstant.NO);
					role.setUpdator(user.getUsername());
					this.roleService.updateValidState(role);
				}
				returnVo.setStatus(SUCCESS+"");
				returnVo.setMessage("操作成功！");
			}else{
				returnVo.setMessage("参数错误！");
			}
		} catch (Exception e) {
			logger.debug("RoleController-checkSnExsits:" + e.getMessage());
			e.printStackTrace();
			returnVo.setMessage("操作异常！");
		}
		return JsonUtils.toJson(returnVo);
	}
}
