package com.ecnice.privilege.service.privilege.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.ecnice.privilege.common.PagerModel;
import com.ecnice.privilege.common.Query;
import com.ecnice.privilege.dao.privilege.IDepartmentDao;
import com.ecnice.privilege.model.privilege.Department;
import com.ecnice.privilege.service.privilege.IDepartmentService;
import com.ecnice.tools.common.UUIDGenerator;

/**
 * @Title:
 * @Description:部门service实现类
 * @Author:Bruce.Liu
 * @Since:2014年3月31日
 * @Version:1.1.0
浙江蘑菇加电子商务有限公司 2014 ~ 2015 版权所有
 */
@Service
public class DepartmentServiceImpl implements IDepartmentService {
	private static Logger logger = Logger.getLogger(DepartmentServiceImpl.class);
	
	@Resource
	private IDepartmentDao departmentDao;

	@Override
	public String getChildrenIdsByPid(String id) throws Exception {
		//递归得到他下面的子部门的列表
		StringBuffer cids = new StringBuffer("");
		cids.append("'");
		cids.append(id);
		cids.append("'").append(",");
		this.getChildrenIdsByPid(cids, id);
		cids = cids.deleteCharAt(cids.length()-1);
		return cids.toString();
	}
	
	private void getChildrenIdsByPid(StringBuffer cids,String pid) throws Exception {
		List<String> childrenIds = departmentDao.getChildrenIdsByPid(pid);
		if(childrenIds==null || childrenIds.size()==0) {
			return ;
		}
		for(String id:childrenIds) {
			cids.append("'");
			cids.append(id);
			cids.append("'").append(",");
			this.getChildrenIdsByPid(cids,id);
		}
	}
	
	public List<String> getChildrenDeptIdsByDeptId(String deptId){
		List<String> resultdepts  = new ArrayList<String>();
		List<String> tempdepts  = new ArrayList<String>();
		Department deptQuery = new Department();
		try {
			List<Department> alldepts  = getAll(deptQuery);
			resultdepts.add(deptId);
			tempdepts.add(deptId);
			getInnerCollection(tempdepts,alldepts,resultdepts);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultdepts;
	}
	
	private void getInnerCollection(List<String> sublist,List<Department> alllist,List<String> resultlist){
		List<String> tempdepts  = new ArrayList<String>();
		for (String subdepartment : sublist) {
			for (Department alldepartment : alllist) {
				if(null!=alldepartment.getPid() && alldepartment.getPid().equals(subdepartment)){
					tempdepts.add(alldepartment.getId());
				}
			}
		}
		resultlist.addAll(tempdepts);
		if(CollectionUtils.isNotEmpty(tempdepts)){
			getInnerCollection(tempdepts,alllist,resultlist);
		}
	}
	
	@Override
	public List<Department> getRootDepartment(Department department) {
		return null != department ? this.departmentDao.getRootDepartment(department) : null;
	}
	
	@Override
	public void insertDepartment(Department department) throws Exception {
		department.setId(UUIDGenerator.generate());
		departmentDao.insertDepartment(department);
	}

	@Override
	public void updateDepartment(Department department) throws Exception {
		departmentDao.updateDepartment(department);
	}

	@Override
	public void delDepatment(String id) throws Exception {
		departmentDao.delDepatment(id);
	}
	
	@Override
	public void delDept(String[] ids) throws Exception {
		for(String id : ids){
			this.departmentDao.delDepatment(id);
		}
	}

	@Override
	public Department getDepartmentById(String id) throws Exception {
		return departmentDao.getDepartmentById(id);
	}

	@Override
	public PagerModel<Department> getPagerModel(Department department,
			Query query) throws Exception {
		return departmentDao.getPagerModel(department, query);
	}
	
	@Override
	public List<Department> getAll(Department dept) throws Exception {
		return this.departmentDao.getAll(dept);
	}
	
	@Override
	public List<Department> getChildDeptsById(String deptId) throws Exception {
		Department department=new Department();
		department.setPid(deptId);
		return this.departmentDao.getAll(department);
	}
	
	@Override
	public int updateSyncDepartmentList(List<Department> departments) throws Exception {
		return this.departmentDao.updateSyncDepartmentList(departments);
	}
}
