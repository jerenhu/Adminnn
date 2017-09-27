package com.ecnice.privilege.web.controller.privilege;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

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
import com.ecnice.privilege.model.privilege.User;
import com.ecnice.privilege.service.privilege.ICompanyService;
import com.ecnice.privilege.utils.JsonUtils;
import com.ecnice.privilege.vo.SimpleReturnVo;
import com.ecnice.privilege.web.controller.BaseController;


/**
 * 公司Controller
 * @author wentaoxiang
 * @date 2016-11-13 15:16:07
 */
@Controller
@RequestMapping("/managment/privilege/company")
public class CompanyController extends BaseController {
	private static Logger logger = Logger.getLogger(CompanyController.class);
	private final String nameSpace="company";
	
	@Resource
	private ICompanyService companyService;
	
	//列表
	@RequestMapping("/list")
	@Permission(systemSn = PrivilegeConstant.SYSTEM_SN, moduleSn = nameSpace, value = PermissionConatant.R)
	public String list(ModelMap model,String sessionId) {
		model.addAttribute("sessionId", sessionId);
		return "/privilege/company_list";
	}

	@ResponseBody
	@RequestMapping("/ajaxList")
	@Permission(systemSn = PrivilegeConstant.SYSTEM_SN, moduleSn = nameSpace, value = PermissionConatant.R)
	public String ajaxList(Company company, Query query,String sessionId) {
		PagerModel<Company> pm = null;
		try {
			company.setStatus(PrivilegeConstant.YES);
			company.setDelFlag(PrivilegeConstant.NO_DELETE_FLAG);
			pm = this.companyService.getPagerModelByQuery(company, query);
		} catch (Exception e) {
			logger.error("CompanyController-ajaxList:",e);
			e.printStackTrace();
		}
		return JsonUtils.getPmJson(pm);
	}
	
	@ResponseBody
	@RequestMapping("/ajaxlista")
	@Permission(systemSn="privilege",moduleSn="dept",value=PermissionConatant.R)
	public String ajaxlist(){
		List<Company> companys=null;
		try{
			Company company=new Company();
			company.setStatus(PrivilegeConstant.YES);
			company.setDelFlag(PrivilegeConstant.NO_DELETE_FLAG);
			companys=this.companyService.getAll(company);
		}catch(Exception e){
			e.printStackTrace();
			logger.debug("CompanyController-ajaxlista:",e);
		}
		return JsonUtils.toJson(companys);
	}
	
	/*@RequestMapping("/addUI")
	@Permission(systemSn = PrivilegeConstant.SYSTEM_SN, moduleSn = nameSpace, value = PermissionConatant.C)
	public String addUI(String id,ModelMap model,String sessionId) {
		return "/privilege/company_update";
	}*/

	//添加
	@ResponseBody
	@RequestMapping("/add")
	@Permission(systemSn = PrivilegeConstant.SYSTEM_SN, moduleSn = nameSpace, value = PermissionConatant.C)
	public String add(Company company, HttpServletRequest request) {
		SimpleReturnVo returnVo = new SimpleReturnVo(error, "添加失败");
		try {
			User user = this.getLoginUser(request);
			if (null != user && StringUtils.isNotBlank(user.getUsername())) {
				String userName=user.getUsername();
				company.setCreator(userName);
				company.setUpdator(userName);
				this.companyService.insertCompany(company);
				returnVo = new SimpleReturnVo(success, "添加成功");
			}else{
				returnVo = new SimpleReturnVo(error, "用户信息获取失败，请重新登录");
			}
		} catch (Exception e) {
			logger.error("CompanyController-add:",e);
			e.printStackTrace();
		}
		return JsonUtils.toJson(returnVo);
	}

	//添加、修改的UI
	@RequestMapping("/updateUI")
	@Permission(systemSn = PrivilegeConstant.SYSTEM_SN, moduleSn = nameSpace, value = PermissionConatant.R)
	public String updateUI(ModelMap model,String id,String sessionId) {
		try {
			if(StringUtils.isNotBlank(id)){
				Company company=this.companyService.getCompanyById(id);
				model.addAttribute("company", company);
			}
		} catch (Exception e) {
			logger.error("CompanyController-updateUI:",e);
			e.printStackTrace();
		}
		return "/privilege/company_update";
	}

	//修改
	@ResponseBody
	@RequestMapping("/update")
	@Permission(systemSn = PrivilegeConstant.SYSTEM_SN, moduleSn = nameSpace, value = PermissionConatant.U)
	public String update(Company company,HttpServletRequest request) {
		SimpleReturnVo returnVo = new SimpleReturnVo(error, "修改失败");
		try {
			User user = this.getLoginUser(request);
			if (null != user && StringUtils.isNotBlank(user.getUsername())) {
				String userName=user.getUsername();
				company.setUpdator(userName);
				company.setDelFlag(null);
				this.companyService.updateCompany(company);
				returnVo = new SimpleReturnVo(success, "修改成功");
			}else{
				returnVo = new SimpleReturnVo(error, "用户信息获取失败，请重新登录");
			}
		} catch (Exception e) {
			logger.error("CompanyController-update:",e);
			e.printStackTrace();
		}
		return JsonUtils.toJson(returnVo);
	}
	
	//修改状态
	@ResponseBody
	@RequestMapping("/updateStatus")
	@Permission(systemSn = PrivilegeConstant.SYSTEM_SN, moduleSn = nameSpace, value = PermissionConatant.U)
	public String updateStatus(String ids,Integer status, HttpServletRequest request) {
		SimpleReturnVo returnVo = new SimpleReturnVo(error, "修改状态失败");
		try {
			User user = this.getLoginUser(request);
			if (null != user && StringUtils.isNotBlank(user.getUsername())) {
				String userName=user.getUsername();
				if (StringUtils.isNotBlank(ids) && null != status) {
					Company company = new  Company();
					company.setUpdator(userName);
					company.setDelFlag(null);
					company.setStatus(status);
					this.companyService.updateCompanyByIds(ids, company);
				}
				returnVo = new SimpleReturnVo(success, "修改状态成功");
			}else{
				returnVo = new SimpleReturnVo(error, "用户信息获取失败，请重新登录");
			}
		} catch (Exception e) {
			logger.error("CompanyController-updateStatus:",e);
			e.printStackTrace();
		}
		return JsonUtils.toJson(returnVo);
	}
	
	//删除
	@ResponseBody
	@RequestMapping("/dels")
	@Permission(systemSn = PrivilegeConstant.SYSTEM_SN, moduleSn = nameSpace, value = PermissionConatant.D)
	public String dels(String ids,HttpServletRequest request) {
		SimpleReturnVo returnVo = new SimpleReturnVo(error, "删除失败");
		try {
			User user = this.getLoginUser(request);
			if (null != user && StringUtils.isNotBlank(user.getUsername())) {
				String userName=user.getUsername();
				if(StringUtils.isNotBlank(ids)){
					Company company = new  Company();
					company.setUpdator(userName);
					company.setDelFlag(PrivilegeConstant.HAS_DELETE_FLAG);
					this.companyService.updateCompanyByIds(ids,company);//逻辑删除
					//this.companyService.delCompanyByIds(ids); //物理删除
				}
				returnVo = new SimpleReturnVo(success, "删除成功");
			}else{
				returnVo = new SimpleReturnVo(error, "用户信息获取失败，请重新登录");
			}
		} catch (Exception e) {
			logger.error("CompanyController-dels:",e);
			e.printStackTrace();
		}
		return JsonUtils.toJson(returnVo);
	}

}
