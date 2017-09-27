package com.ecnice.privilege.service.privilege;

import java.util.List;

import com.ecnice.privilege.common.PagerModel;
import com.ecnice.privilege.common.Query;
import com.ecnice.privilege.model.privilege.Department;

/**
 * @Title:
 * @Description:部门service
 * @Author:Bruce.Liu
 * @Since:2014年3月31日
 * @Version:1.1.0
浙江蘑菇加电子商务有限公司 2014 ~ 2015 版权所有
 */
public interface IDepartmentService {
	
	/**
	 * @param id
	 * @return
	 * @throws Exception
	 * @Description:通过部门得到部门下面子部门的所有id，最后id以 '1','2'这种形势返回
	 */
	public String getChildrenIdsByPid(String id) throws Exception;
	
	/**
	 * 通过部门ID获取所有子部门的ID，包括本身
	 * @param deptId
	 * @return
	 * @Description:
	 * @author xietongjian 2017 上午11:52:35
	 */
	public List<String> getChildrenDeptIdsByDeptId(String deptId);
	
	/**
	 * 得到公司下所有的顶层部门
	 * @return
	 * @Description:
	 * @author wangzequan 2016 上午11:42:44
	 * @param department 
	 */
	public List<Department> getRootDepartment(Department department);
	
	/**
	 * @param department
	 * @throws Exception
	 * @Description:添加部门
	 */
	public void insertDepartment(Department department) throws Exception;

	/**
	 * @param department
	 * @throws Exception
	 * @Description:更新部门
	 */
	public void updateDepartment(Department department) throws Exception;

	/**
	 * @param id
	 * @throws Exception
	 * @Description:删除部门
	 */
	public void delDepatment(String id) throws Exception;

	/**
	 * 
	 * @param ids
	 * @throws Exception
	 * @Description:批量删除部门
	 */
	public void delDept(String[] ids) throws Exception;

	/**
	 * @param id
	 * @return
	 * @throws Exception
	 * @Description:根据id查询部门对象
	 */
	public Department getDepartmentById(String id) throws Exception;

	/**
	 * @param department
	 * @param query
	 * @return
	 * @throws Exception
	 * @Description: 分页查询部门列表
	 */
	public PagerModel<Department> getPagerModel(Department department,
			Query query) throws Exception;

	/**
	 * 
	 * @return
	 * @throws Exception
	 * @Description:获取所有部门
	 */
	public List<Department> getAll(Department dept) throws Exception;
	
	/**
	 * 同步部门数据，新增，修改一起同步
	 * @param departments
	 * @return
	 * @throws Exception
	 * @Description:
	 * @author xietongjian 2017 下午3:01:30
	 */
	public int updateSyncDepartmentList(List<Department> departments) throws Exception;
	
	/**
	 * 
	 * @return
	 * @throws Exception
	 * @Description:获取所有子部门
	 */
	public List<Department> getChildDeptsById(String deptId) throws Exception;
	
}
