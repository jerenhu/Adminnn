package com.ecnice.privilege.api.privilege.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.ecnice.privilege.api.privilege.IUserRoleApi;
import com.ecnice.privilege.common.PagerModel;
import com.ecnice.privilege.common.Query;
import com.ecnice.privilege.constant.PrivilegeConstant;
import com.ecnice.privilege.model.privilege.Company;
import com.ecnice.privilege.model.privilege.Department;
import com.ecnice.privilege.model.privilege.Role;
import com.ecnice.privilege.model.privilege.User;
import com.ecnice.privilege.model.privilege.UserRole;
import com.ecnice.privilege.service.privilege.ICompanyService;
import com.ecnice.privilege.service.privilege.IDepartmentService;
import com.ecnice.privilege.service.privilege.IRoleService;
import com.ecnice.privilege.service.privilege.IUserRoleService;
import com.ecnice.privilege.service.privilege.IUserService;
import com.ecnice.privilege.utils.DateUtil;
import com.ecnice.privilege.vo.ReturnVo;
import com.ecnice.privilege.vo.privilege.UserRoleVo;

@Component
public class UserRoleApiImpl implements IUserRoleApi {
	private static Logger logger = Logger.getLogger(UserRoleApiImpl.class);
	@Resource
	private IUserService userService;
	@Resource
	private IRoleService roleService;
	@Resource
	private IUserRoleService userRoleService;
	@Resource
	private IDepartmentService departmentService;
	@Resource
	private ICompanyService companyService;
	
	@Override
	public List<User> getUsers() throws Exception {
		return userService.getAll(null);
	}
	
	@Override
	public PagerModel<User> getUserPm(User user, Query query) throws Exception {
		PagerModel<User> pm = null;
		try {
			if (StringUtils.isBlank(user.getDepartmentId()) || "0".equals(user.getDepartmentId()) || "null".equals(user.getDepartmentId())) {
				user.setDepartmentId(null);
			} else {
				String deptIds = departmentService.getChildrenIdsByPid(user.getDepartmentId());
				user.setDepartmentId(deptIds);
			}
			pm = this.userService.getPagerModel(user, query);
			if (null != pm && CollectionUtils.isNotEmpty(pm.getDatas())) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				for (User temp : pm.getDatas()) {
					if (null != temp) {
						temp.setPassword(null);
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
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("UserRoleApiImpl-getUserPm:", e);
		}
		return pm;

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

	@Override
	public List<Department> getAllDept() {
		List<Department> depts = null;
		try {
			depts = this.departmentService.getAll(null);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("UserRoleApiImpl-getAllDept:", e);
		}
		return depts;
	}
	
	@Override
	public Department getDepartmentById(String id) {
		if (StringUtils.isBlank(id)) {
			return null;
		}
		try {
			return this.departmentService.getDepartmentById(id.trim());
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("UserRoleApiImpl-getDepartmentById:", e);
		}
		return null;
	}

	@Override
	public List<Role> getRoles()throws Exception {
		return roleService.getAll(null);
	}

	@Override
	public List<UserRoleVo> getUserRoles() throws Exception{
		return userRoleService.getAllUserRoleVo();
	}

	@Override
	public ReturnVo<UserRole> getUserRolesByQuery(UserRole userRole) {
		ReturnVo<UserRole> returnVo = new ReturnVo<UserRole>(PrivilegeConstant.ERROR_CODE, "查询数据失败！");
		try {
			if(StringUtils.isBlank(userRole.getUserNo())){
				returnVo.setMessage("用户工号不能为空！");
				return returnVo;
			}
			userRole.setDelFlag(PrivilegeConstant.NO_DELETE_FLAG);
			List<UserRole> list = this.userRoleService.getAll(userRole);
			returnVo.setStatus(PrivilegeConstant.SUCCESS_CODE);
			returnVo.setDatas(list);
			returnVo.setMessage("查询数据成功！");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("查询用户角色接口异常！" + e);
			returnVo.setMessage("查询用户角色接口异常！");
		}
		return returnVo;
	}

	@Override
	public ReturnVo<PagerModel<Role>> getRolesByQuery(Role role, Query query) {
		ReturnVo<PagerModel<Role>> returnVo = new ReturnVo<PagerModel<Role>>(PrivilegeConstant.ERROR_CODE, "查询数据失败！");
		try {
			role.setDelFlag(PrivilegeConstant.NO_DELETE_FLAG);
			PagerModel<Role> pm = roleService.getPagerModelByQuery4Api(role, query);
			returnVo.setStatus(PrivilegeConstant.SUCCESS_CODE);
			returnVo.setData(pm);
			returnVo.setMessage("查询数据成功！");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("分页查询角色接口异常！" + e);
			returnVo.setMessage("分页查询角色接口异常！");
		}
		return returnVo;
	}
	
}
