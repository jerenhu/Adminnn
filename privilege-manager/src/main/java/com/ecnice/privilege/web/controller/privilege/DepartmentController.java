package com.ecnice.privilege.web.controller.privilege;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ecnice.privilege.common.Permission;
import com.ecnice.privilege.constant.PermissionConatant;
import com.ecnice.privilege.constant.PrivilegeConstant;
import com.ecnice.privilege.model.privilege.Company;
import com.ecnice.privilege.model.privilege.Department;
import com.ecnice.privilege.model.privilege.User;
import com.ecnice.privilege.service.privilege.ICompanyService;
import com.ecnice.privilege.service.privilege.IDepartmentService;
import com.ecnice.privilege.utils.JsonUtils;
import com.ecnice.privilege.vo.ReturnVo;
import com.ecnice.privilege.vo.SimpleReturnVo;
import com.ecnice.privilege.vo.privilege.OrgTreeApiVo;
import com.ecnice.privilege.web.controller.BaseController;
import com.ecnice.tools.common.ReadProperty;

/**
 * 
 * @Description:部门管理
 * @Author:Martin.Wang
 * @Since:2014-4-1
 * @Version:1.1.0
浙江蘑菇加电子商务有限公司 2014 ~ 2015 版权所有
 */
@Controller
@RequestMapping("/managment/privilege/dept")
public class DepartmentController extends BaseController{
	private static Logger logger = Logger.getLogger(DepartmentController.class);
	
	@Resource
	private ICompanyService companyService;
	@Resource
	private IDepartmentService departmentService;
	@Resource
	private ReadProperty readProperty;

	/**
	 * 
	 * @return
	 * @Description:跳转到部门列表页面
	 */
	@RequestMapping("/list")
	@Permission(systemSn="privilege",moduleSn="dept",value=PermissionConatant.R)
	public String list(String sessionId,ModelMap model) {
		model.addAttribute("sessionId", sessionId);
		return "/privilege/dept_page";
	}
	
	/**
	 * 
	 * @return
	 * @Description:部门列表页面数据
	 */
	@ResponseBody
	@RequestMapping("/ajaxlist")
	@Permission(systemSn="privilege",moduleSn="dept",value=PermissionConatant.R)
	public String ajaxlist(String companyId){
		List<Department> depts=null;
		try{
			if(StringUtils.isBlank(companyId)){
				return JsonUtils.toJson(depts);
			}
			Department dept = new Department();
			dept.setCompanyId(companyId);
			depts = this.departmentService.getAll(dept);
		}catch(Exception e){
			e.printStackTrace();
			logger.debug("DepartmentController-ajaxlist:",e);
		}
		return JsonUtils.toJson(depts);
	}
	
	/**
	 * 取得所有公司和部门的数据
	 * @param companyId
	 * @return
	 * @Description:
	 * @author wentaoxiang 2016年11月14日 上午8:26:04
	 */
	@ResponseBody
	@RequestMapping("/ajaxlistac")
	@Permission(systemSn="privilege",moduleSn="dept",value=PermissionConatant.R)
	public String ajaxlistac(){
		Map<String, Object> map = null;
		List<Company> companys = null;
		List<Department> depts = null;
		try{
			Company company=new Company();
			company.setStatus(PrivilegeConstant.YES);
			company.setDelFlag(PrivilegeConstant.NO_DELETE_FLAG);
			companys=this.companyService.getAll(company);
			
			Department dept = new Department();
			depts = this.departmentService.getAll(dept);
			
			map = new HashMap<String, Object>();
			if (CollectionUtils.isNotEmpty(companys)) {
				map.put("company", companys);
			}
			if (CollectionUtils.isNotEmpty(depts)) {
				map.put("dept", depts);
			}
			
		}catch(Exception e){
			e.printStackTrace();
			logger.debug("DepartmentController-ajaxlistac:",e);
		}
		return JsonUtils.toJson(map);
	}
	
	@ResponseBody
	@RequestMapping("/getOrgTree")
	@Permission(systemSn="privilege",moduleSn="dept",value=PermissionConatant.R)
	public String getOrgTree() {
		ReturnVo<OrgTreeApiVo> returnVo = new ReturnVo<OrgTreeApiVo>();
		List<OrgTreeApiVo> orgTreeApiVos = new ArrayList<OrgTreeApiVo>();
		try {
			//得到顶层公司列表
			List<Company> companyList = null;
			Company queryCompany = new Company();
			queryCompany.setDelFlag(PrivilegeConstant.NO_DELETE_FLAG);
			//queryCompany.setOrgtype(CompanyOrgtypeEnum.Y.getCode());
			queryCompany.setStatus(1);
			companyList = companyService.getAll(queryCompany);
			
			OrgTreeApiVo root = new OrgTreeApiVo();
			
			//设置树根节点
			for (Company company : companyList) {
				//设置树根
				if(null == company.getPid() || StringUtils.isBlank(company.getPid())){
					root.setId(company.getId());
					root.setpId(PrivilegeConstant.TREE_PARENT_ROOT);
					root.setCompanyId(company.getId());
					root.setText(readProperty.getValue(PrivilegeConstant.ORG_TREE_ROOT_TEXT));
					root.setCode(company.getCode());
					root.setType("1");
					orgTreeApiVos.add(root);
				}
			}
			
			// 已经加入树形结构中的Map
			Map<String, String> ardTreeNodeMap = new HashMap<String, String>();
			
			for (Company company : companyList) {
				if(null == company.getPid() || StringUtils.isBlank(company.getPid())){
					continue;
				}
				
				OrgTreeApiVo scdRoot = new OrgTreeApiVo();
				scdRoot.setId(company.getId());
				scdRoot.setpId(root.getId());
				scdRoot.setCompanyId(company.getId());
				scdRoot.setText(company.getCname());
				scdRoot.setType("1");
				
				// 如果 公司下 第一个部门  和公司 同名  则合并  
				Department department=  new Department();  
				department.setCompanyId(company.getId());
				department.setDelFlag(PrivilegeConstant.NO_DELETE_FLAG);
				List<Department> deptList = departmentService.getRootDepartment(department);
				
				if(company!=null && CollectionUtils.isNotEmpty(deptList) && deptList.get(0).getName().trim().equals(company.getCname().trim())){
					scdRoot.setType("2");
					scdRoot.setId(deptList.get(0).getId());
					scdRoot.setCompanyId(company.getId());
					scdRoot.setIsMerge("1");
					ardTreeNodeMap.put(deptList.get(0).getId(), deptList.get(0).getId());
				}
				orgTreeApiVos.add(scdRoot);
			}
			
			Department department=  new Department();  
			department.setDelFlag(PrivilegeConstant.NO_DELETE_FLAG);
			List<Department> deptList = departmentService.getAll(department);//departmentService.getRootDepartment(department);
			
			for (Department dept : deptList){
				if(dept.getId().equals(ardTreeNodeMap.get(dept.getId()))){
					continue;
				}
				OrgTreeApiVo orgTree = new OrgTreeApiVo();
				orgTree.setId(dept.getId());
				orgTree.setCompanyId(dept.getCompanyId());
				
				if(StringUtils.isBlank(dept.getPid())){
					orgTree.setpId(dept.getCompanyId());
				}else{
					orgTree.setpId(dept.getPid());
				}
				orgTree.setText(dept.getName());
				orgTree.setType("2");
				orgTreeApiVos.add(orgTree);
			}
			returnVo.setDatas(orgTreeApiVos);
			returnVo.setStatus(""+PrivilegeConstant.SUCCESS);
			returnVo.setMessage("查询数据成功！");
		} catch (Exception e) {
			e.printStackTrace();
			returnVo.setMessage("查询数据失败！");
			logger.error("查询数据失败！  - " + e);
		}
		return JsonUtils.toJson(returnVo);
	}
	
	/**
	 * 
	 * @return
	 * @Description:it部门列表
	 */
	@ResponseBody
	@RequestMapping("/itdept")
	@Permission(systemSn="privilege",moduleSn="dept",value=PermissionConatant.R)
	public String itdept(){
		List<Department> depts=null;
		try{
			depts=this.departmentService.getAll(null);
		}catch(Exception e){
			e.printStackTrace();
			logger.debug("DepartmentController-ajaxlist:",e);
		}
		return JsonUtils.toJson(depts);
	}
	
	/**
	 * 
	 * @return
	 * @Description:部门列表页面分页数据
	 */
	@ResponseBody
	@RequestMapping("/getAll")
	public String getAll(){
		List<Department> depts=null;
		try{
			depts=this.departmentService.getAll(null);
			for(Department dep:depts){
				if(StringUtils.isBlank(dep.getPid())) {
					dep.setPid("0");
				}
			}
			Department department=new Department();
			department.setId("0");
			department.setName("电商部门");
			depts.add(department);
		}catch(Exception e){
			e.printStackTrace();
			logger.debug("DepartmentController-ajaxlist:",e);
		}
		return JsonUtils.toJson(depts);
	}
	
	/**
	 * 
	 * @param pid
	 * @param model
	 * @return
	 * @Description:跳转到添加部门页面
	 */
	@RequestMapping("/insertUI")
	@Permission(systemSn="privilege",moduleSn="dept",value=PermissionConatant.C)
	public String insertUI(String pid,String sessionId,ModelMap model){
		model.addAttribute("sessionId", sessionId);
		if(StringUtils.isNotBlank(pid)){
			model.put("pid", pid);
		}
		return "/privilege/dept_insert";
	}
	
	/**
	 * 
	 * @return
	 * @Description:跳转到修改页面
	 */
	@RequestMapping("/updateUI")
	@Permission(systemSn="privilege",moduleSn="dept",value=PermissionConatant.U)
	public String updateUI(String sessionId,ModelMap model){
		model.addAttribute("sessionId", sessionId);
		return "/privilege/dept_update";
	}
	
	/**
	 * 
	 * @param dept
	 * @return
	 * @Description:添加部门信息
	 */
	@ResponseBody
	@RequestMapping("/insert")
	@Permission(systemSn="privilege",moduleSn="dept",value=PermissionConatant.C)
	public String insert(Department dept){
		SimpleReturnVo vo;
		try{
			if(StringUtils.isBlank(dept.getPid())){
				dept.setPid(null);
			}
			this.departmentService.insertDepartment(dept);
			vo=new SimpleReturnVo(this.SUCCESS, "成功");
		}catch(Exception e){
			e.printStackTrace();
			logger.debug("DepartmentController-insert:",e);
			vo=new SimpleReturnVo(this.FAIL, "异常");
		}
		return JsonUtils.toJson(vo);
	}
	
	/**
	 * 
	 * @param id
	 * @return
	 * @Description:根据id获取部门信息
	 */
	@ResponseBody
	@RequestMapping("/ajaxUpdate")
	@Permission(systemSn="privilege",moduleSn="dept",value=PermissionConatant.U)
	public String ajaxUpdate(String id){
		Department dept=null;
		try{
			dept=this.departmentService.getDepartmentById(id);
		}catch(Exception e){
			e.printStackTrace();
			logger.debug("DepartmentController-update:",e);
		}
		return JsonUtils.toJson(dept);
	}
	
	/**
	 * 
	 * @param dept
	 * @return
	 * @Description:修改部门信息
	 */
	@ResponseBody
	@RequestMapping("/update")
	@Permission(systemSn="privilege",moduleSn="dept",value=PermissionConatant.U)
	public String update(Department dept){
		SimpleReturnVo vo;
		try{
			this.departmentService.updateDepartment(dept);
			vo=new SimpleReturnVo(this.SUCCESS, "成功");
		}catch(Exception e){
			e.printStackTrace();
			logger.debug("DepartmentController-update:",e);
			vo=new SimpleReturnVo(this.FAIL, "异常");
		}
		return JsonUtils.toJson(vo);
	}
	
	/**
	 * 
	 * @param ids
	 * @return
	 * @Description:删除部门信息
	 */
	@ResponseBody
	@RequestMapping("/delete")
	@Permission(systemSn="privilege",moduleSn="dept",value=PermissionConatant.D)
	public String delete(String ids){
		SimpleReturnVo vo;
		try{
			if(StringUtils.isNotBlank(ids)){
				String [] id=ids.split(",");
				this.departmentService.delDept(id);
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.debug("DepartmentController-update:",e);
			vo=new SimpleReturnVo(this.FAIL, "异常");
		}
		vo=new SimpleReturnVo(this.SUCCESS, "成功");
		return JsonUtils.toJson(vo);
	}
}
