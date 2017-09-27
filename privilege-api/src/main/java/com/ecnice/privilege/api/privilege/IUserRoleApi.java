package com.ecnice.privilege.api.privilege;

import java.util.List;

import com.ecnice.privilege.common.PagerModel;
import com.ecnice.privilege.common.Query;
import com.ecnice.privilege.model.privilege.Department;
import com.ecnice.privilege.model.privilege.Role;
import com.ecnice.privilege.model.privilege.User;
import com.ecnice.privilege.model.privilege.UserRole;
import com.ecnice.privilege.vo.ReturnVo;
import com.ecnice.privilege.vo.privilege.UserRoleVo;

/**
 * 用户角色接口
 * @author liuwenjun1
 */
public interface IUserRoleApi {
	/**
	 * 获取所有的用户
	 * @return
	 */
	public List<User> getUsers() throws Exception;
	
	
	/**
	 * 分页查询用户信息
	 * @return
	 * @throws Exception
	 * @Description:
	 * @author wentaoxiang 2016年7月11日 下午6:03:45
	 */
	public PagerModel<User> getUserPm(User user,Query query) throws Exception;
	
	/**
	 * 得到所有的部门
	 * @return
	 * @Description:
	 * @author wentaoxiang 2016年7月11日 下午6:08:29
	 */
	public List<Department> getAllDept();
	
	/**
	 * 通过部门id查询部门信息
	 * @param id
	 * @return
	 * @Description:
	 * @author wentaoxiang 2016年7月13日 下午1:43:02
	 */
	public Department getDepartmentById(String id);
	
	/**
	 * 获取所有的角色
	 * @return
	 */
	public List<Role> getRoles() throws Exception;
	
	/**
	 * 获取所有的用户角色
	 * @return
	 */
	public List<UserRoleVo> getUserRoles() throws Exception;
	
	/**
	 * 根据条件查询所有角色
	 * @param role
	 * @return @see status: PrivilegeConstant.SUCCESS_CODE \ PrivilegeConstant.ERROR_CODE
	 * @Description:
	 * @author xietongjian 2017年5月12日 下午1:34:14
	 */
	public ReturnVo<PagerModel<Role>> getRolesByQuery(Role role, Query query);
	
	/**
	 * 根据条件查询所有用户的角色
	 * @param userNo
	 * @return @see status: PrivilegeConstant.SUCCESS_CODE \ PrivilegeConstant.ERROR_CODE
	 * @Description:
	 * @author xietongjian 2017年5月12日 下午1:34:23
	 */
	public ReturnVo<UserRole> getUserRolesByQuery(UserRole userRole);
}
