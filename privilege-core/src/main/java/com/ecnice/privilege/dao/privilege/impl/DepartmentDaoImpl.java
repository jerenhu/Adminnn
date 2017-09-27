package com.ecnice.privilege.dao.privilege.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.ecnice.privilege.common.PagerModel;
import com.ecnice.privilege.common.Query;
import com.ecnice.privilege.dao.MybatisTemplate;
import com.ecnice.privilege.dao.privilege.IDepartmentDao;
import com.ecnice.privilege.model.privilege.Department;
import com.ecnice.tools.common.UUIDGenerator;

@Repository
public class DepartmentDaoImpl extends MybatisTemplate implements IDepartmentDao {

	@Override
	public List<String> getChildrenIdsByPid(String id) throws Exception {
		return (List<String>) this.selectList("DepartmentXML.getChildrenIdsByPid", id);
	}
	
	@Override
	public List<Department> getRootDepartment(Department department) {
		return (List<Department>) this.selectList("DepartmentXML.getRootDepartment", department);
	}

	@Override
	public void insertDepartment(Department department) throws Exception {
		this.insert("DepartmentXML.insertDepartment", department);
	}

	@Override
	public void updateDepartment(Department department) throws Exception {
		this.update("DepartmentXML.updateDepartment", department);
	}

	@Override
	public int updateDepartmentList(List<Department> depts) throws Exception {
		if(CollectionUtils.isNotEmpty(depts)){
			for (Department dept : depts) {
				if (null != dept) {
					dept.setUpdateTime(new Date());
					/**清理不需要更新的数据*/
					this.cleanWhenUpdate(dept);
				}
			}
			return this.update("DepartmentXML.updateDepartmentList", depts);
		} else {
			return 0;
		}
	}
	
	/**
	 * 清理不需要更新的数据
	 * @param orderRebok
	 * @Description:
	 * @author wentaoxiang 2016年6月1日 下午5:19:16
	 */
	private void cleanWhenUpdate(Department department) {
		department.setCreateTime(null);
		department.setCreator(null);
	}

	@Override
	public void delDepatment(String id) throws Exception {
		this.delete("DepartmentXML.delDepatment", id);
	}

	@Override
	public Department getDepartmentById(String id) throws Exception {
		return (Department) this.selectOne("DepartmentXML.getDepartmentById", id);
	}

	@Override
	public PagerModel<Department> getPagerModel(Department department,
			Query query) throws Exception {
		return this.getPagerModelByQuery(department, query, "DepartmentXML.getPagerModel");
	}
	
	@Override
	public List<Department> getAll(Department department) throws Exception {
		return (List<Department>)this.selectList("DepartmentXML.getAll",department);
	}

	@Override
	public int updateSyncDepartmentList(List<Department> departments) throws Exception {
		if(CollectionUtils.isNotEmpty(departments)){
			for (Department department : departments) {
				if (null != department) {
					department.setUpdateTime(new Date());
					/**清理不需要更新的数据*/
					//this.cleanWhenUpdate(department);
				}
			}
			return this.update("DepartmentXML.synDepartmentList", departments);
		} else {
			return 0;
		}
	}
	
	@Override
	public void insertDepartmentBatch(List<Department> depts) throws Exception {
		if(CollectionUtils.isNotEmpty(depts)){
			for (Department dept : depts) {
				if (null != dept) {
					if(StringUtils.isBlank(dept.getId())){
						dept.setId(UUIDGenerator.generate());
					}
					dept.setCreateTime(new Date());
				}
			}
			this.insert("DepartmentXML.insertDepartmentBatch", depts);
		}
	}
}
