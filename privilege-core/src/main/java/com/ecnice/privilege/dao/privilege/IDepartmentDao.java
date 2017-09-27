package com.ecnice.privilege.dao.privilege;

import java.util.List;

import com.ecnice.privilege.common.PagerModel;
import com.ecnice.privilege.common.Query;
import com.ecnice.privilege.model.privilege.Company;
import com.ecnice.privilege.model.privilege.Department;

/**
 * @Description:部门Dao
 * @Author:Bruce.Liu
 * @Since:2014年3月31日
 * @Version:1.1.0
浙江蘑菇加电子商务有限公司 2014 ~ 2015 版权所有
 */
public interface IDepartmentDao {
	
	/**
	 * @param id
	 * @return
	 * @throws Exception
	 * @Description:通过部门得到部门下面子部门的所有id，
	 */
	public List<String> getChildrenIdsByPid(String id) throws Exception;

	/**
	 * 得到公司下的第一级部门
	 * @param department
	 * @return
	 * @Description:
	 * @author wangzequan 2016 上午11:46:57
	 */
	public List<Department> getRootDepartment(Department department);

	/**
	 * @param department
	 * @throws Exception
	 * @Description:添加部门
	 */
	public void insertDepartment(Department department) throws Exception;
	
	/**
	 * 批量
	 * @param companys
	 * @throws Exception
	 * @Description:
	 */
	public void insertDepartmentBatch(List<Department> depts) throws Exception;

	/**
	 * @param department
	 * @throws Exception
	 * @Description:更新部门
	 */
	public void updateDepartment(Department department) throws Exception;
	
	/**
	 * 
	 * @param depts
	 * @throws Exception
	 * @Description:
	 */
	public int updateDepartmentList(List<Department> depts) throws Exception;

	/**
	 * @param id
	 * @throws Exception
	 * @Description:删除部门
	 */
	public void delDepatment(String id) throws Exception;

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
	 * @Description:获取所有部门
	 */
	public List<Department> getAll(Department department) throws Exception;
}
