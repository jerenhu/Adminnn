package com.ecnice.privilege.web.controller.common;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ecnice.privilege.model.privilege.Department;
import com.ecnice.privilege.model.privilege.Role;
import com.ecnice.privilege.service.privilege.IRoleService;
import com.ecnice.privilege.web.controller.BaseController;
import com.ecnice.tools.common.JsonUtils;
import com.ecnice.tools.pager.PagerModel;
import com.ecnice.tools.pager.Query;

/**
 * @Title: 工具类
 * @Description:
 * @Author:xietongjian
 * @Since:2016年10月19日 上午9:20:28
 * @Version:1.1.0
 * @Copyright:Copyright (c) 浙江蘑菇加电子商务有限公司 2015 ~ 2016 版权所有  
 */
@Controller
@RequestMapping("/common")
public class CommonToolController extends BaseController {
	private static Logger logger = Logger.getLogger(CommonToolController.class);
	
	@Resource
	private IRoleService roleService;
	
	
	/**
	 * 人员选择器
	 * @param model
	 * @param sessionId
	 * @return
	 * @Description:
	 */
	@RequestMapping("/personSelector")
	public String personSelector(ModelMap model, String sessionId) {
		try {
			model.put("sessionId", sessionId);
		} catch (Exception e) {
			logger.error("personSelector:"+e);
			e.printStackTrace();
		}
		return "/common/person_selector";
	}
	
	/**
	 * 人员角色选择器
	 * @param model
	 * @param sessionId
	 * @return
	 * @Description:
	 */
	@RequestMapping("/personRoleSelector")
	public String personRoleSelector(ModelMap model, String sessionId) {
		try {
			model.put("sessionId", sessionId);
//			List<PersonRole> personRoles = new ArrayList<PersonRole>();
//			model.put("personRoles", JsonUtils.toJson(personRoles));
		} catch (Exception e) {
			logger.error("personSelector:"+e);
			e.printStackTrace();
		}
		return "/common/person_role_selector";
	}
	
	/**
	 * 组织选择器
	 * @param model
	 * @param sessionId
	 * @return
	 * @Description:
	 */
	@RequestMapping("/orgSelector")
	public String orgSelector(ModelMap model, String sessionId) {
		try {
			model.put("sessionId", sessionId);
		} catch (Exception e) {
			logger.error("orgSelector:"+e);
			e.printStackTrace();
		}
		return "/common/org_selector";
	}
	
//	/**
//	 * 加载人员列表数据
//	 * @param pro
//	 * @param query
//	 * @param sessionId
//	 * @author xietongjian 2016 上午10:10:22
//	 */
//	@ResponseBody
//	@RequestMapping("/ajaxListPerson")
//	public String ajaxListPerson(PersonnelApiVo personnelApiVo, com.ys.tools.pager.Query query,String sessionId) {
//		com.ys.tools.pager.PagerModel<PersonnelApiVo> pm = new com.ys.tools.pager.PagerModel<PersonnelApiVo>();
//		try {
//			query.setPageNumber(Integer.parseInt(query.getPage()));
//			query.setPageSize(query.getRows());
//			ReturnVo<com.ys.tools.pager.PagerModel<PersonnelApiVo>> returnVo = personnelApi.getPersonnel(personnelApiVo, query);
//			if(null != returnVo && UcenterConstant.SUCCESS == returnVo.getCode().intValue()){
//				pm = returnVo.getData();
//			}
//		} catch (Exception e) {
//			logger.error("ProController-ajaxList:"+e);
//			e.printStackTrace();
//		}
//		return "callback("+JsonUtils.toJson(pm)+")";
//	}
	
	/**
	 * 加载角色列表数据
	 * @param role	companyId
	 * @param query
	 * @param sessionId
	 * @author xietongjian 2016 上午10:10:22
	 */
	@ResponseBody
	@RequestMapping("/ajaxListRoles")
	public String ajaxListRoles(Role role, com.ecnice.privilege.common.Query query,String sessionId) {
		com.ecnice.privilege.common.PagerModel<Role> pm = new com.ecnice.privilege.common.PagerModel<Role>();
		try {
			pm = roleService.getPagerModelByQuery4Api(role, query);
		} catch (Exception e) {
			logger.error("ProController-ajaxList:"+e);
			e.printStackTrace();
		}
		return "callback("+JsonUtils.toJson(pm)+")";
	}
	
//	/**
//	 * 加载组织机构数据
//	 * @param pro
//	 * @param query
//	 * @param sessionId
//	 * @author xietongjian 2016 上午10:10:22
//	 */
//	@ResponseBody
//	@RequestMapping("/ajaxListOrgTree")
//	public String ajaxListOrgTree(String sessionId) {
//		List<OrgTreeApiVo> orgTreeList = new ArrayList<OrgTreeApiVo>();
//		try {
//			ReturnVo<OrgTreeApiVo> returnVo =  orgApi.getOrgTree();
//			if(null != returnVo && UcenterConstant.SUCCESS == returnVo.getCode().intValue()){
//				orgTreeList = returnVo.getDatas();
//			}
//		} catch (Exception e) {
//			logger.error("ProController-ajaxList:"+e);
//			e.printStackTrace();
//		}
//		return "callback("+JsonUtils.toJson(orgTreeList)+")";
//	}
//	
//	/**
//	 * 加载公司数据
//	 * @param pro
//	 * @param query
//	 * @param sessionId
//	 * @author xietongjian 2016 上午10:10:22
//	 */
//	@ResponseBody
//	@RequestMapping("/ajaxListCompanyTree")
//	public String ajaxListCompanyTree(String sessionId) {
//		List<OrgTreeApiVo> orgTreeList = new ArrayList<OrgTreeApiVo>();
//		try {
//			ReturnVo<OrgTreeApiVo> returnVo =  orgApi.getCompanyTree();
//			if(null != returnVo && UcenterConstant.SUCCESS == returnVo.getCode().intValue()){
//				orgTreeList = returnVo.getDatas();
//			}
//		} catch (Exception e) {
//			logger.error("ProController-ajaxList:"+e);
//			e.printStackTrace();
//		}
//		return "callback("+JsonUtils.toJson(orgTreeList)+")";
//	}
//	
	
	
	
	
	
	
	
	
	
	
	
	
//	
//
//	private void setMap(ModelMap model){
//		Map<String,List<DictionaryVo>> dataDict=new HashMap<String,List<DictionaryVo>>();
//		List<DictionaryVo> deptLevel = new ArrayList<DictionaryVo>();
//		List<DictionaryVo> original = new ArrayList<DictionaryVo>();
//		List<DictionaryVo> deptType = new ArrayList<DictionaryVo>();
//		List<DictionaryVo> sysStatus = new ArrayList<DictionaryVo>();
//		//部门级别
//		for(DepartmentLevelEnum item :DepartmentLevelEnum.values()){
//			DictionaryVo dv = new DictionaryVo();
//			dv.setCode(Integer.valueOf(item.getCode())+"" );
//			dv.setName(item.getName());
//			deptLevel.add(dv);
//		}
//		dataDict.put("deptLevel", deptLevel);
//		model.put("deptLevel", deptLevel);
//		
//		//来源
//		for(OriginalEnum item :OriginalEnum.values()){
//			DictionaryVo dv = new DictionaryVo();
//			dv.setCode(item.getCode()+"");
//			dv.setName(item.getName());
//			original.add(dv);
//		}
//		dataDict.put("original", original);
//		model.put("original", original);
//		
//		//部门类型
//		for(DepartmentType item:DepartmentType.values()){
//			DictionaryVo dv = new DictionaryVo();
//			dv.setCode(item.getCode()+"");
//			dv.setName(item.getName());
//			deptType.add(dv);
//		}
//		dataDict.put("deptType", deptType);
//		model.put("deptType", deptType);
//		
//		dataDict.put("sysStatus", sysStatus);
//		model.put("sysStatus", sysStatus);
//		
//		model.addAttribute("dataDict", JsonUtils.toJson(dataDict));
//	}
//	/**
//	 * 选择部门
//	 * @param model
//	 * @param roomId
//	 * @param sessionId
//	 * @return
//	 * @Description:
//	 * @author xietongjian 2016 上午9:22:19
//	 */
//	@RequestMapping("/deptSelector")
//	public String deptSelector(ModelMap model, String sessionId) {
//		try {
////			List<ProType> proTypes = this.proTypeService.getAvailable();
////			model.put("proTypes", proTypes);
//			setMap(model);
//			
//		} catch (Exception e) {
//			logger.error("CommonToolController-deptSelector:"+e);
//			e.printStackTrace();
//		}
//		return "/common/dept_selector";
//	}
	
//	/**
//	 * 选择人员
//	 * @param model
//	 * @param roomId
//	 * @param sessionId
//	 * @return
//	 * @Description:
//	 * @author xietongjian 2016 上午9:22:19
//	 */
//	@RequestMapping("/personnelSelector")
//	public String personnelSelector(ModelMap model, String sessionId) {
//		try {
////			List<ProType> proTypes = this.proTypeService.getAvailable();
////			model.put("proTypes", proTypes);
//			setMap(model);
//			
//		} catch (Exception e) {
//			logger.error("CommonToolController-personnelSelector:"+e);
//			e.printStackTrace();
//		}
//		return "/common/personnel_selector";
//	}
	
	/**
	 * 加载部门列表数据
	 * @param pro
	 * @param query
	 * @param sessionId
	 * @author xietongjian 2016 上午10:10:22
	 */
	@ResponseBody
	@RequestMapping("/deptSelectorAjax")
	public String deptSelectorAjax(Department department, String keyword, Query query,String sessionId) {
		PagerModel<Department> pm = null;
		try {
//			pro.setProName(keyword);
//			pm = this.proService.getPagerModelByQuery(pro, query);
		} catch (Exception e) {
			logger.error("CommonToolController-ajaxList:"+e);
			e.printStackTrace();
		}
		return JsonUtils.toJson(pm);
	}
}