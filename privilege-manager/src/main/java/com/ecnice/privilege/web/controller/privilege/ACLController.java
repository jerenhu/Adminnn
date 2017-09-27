package com.ecnice.privilege.web.controller.privilege;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ecnice.privilege.common.Permission;
import com.ecnice.privilege.constant.PermissionConatant;
import com.ecnice.privilege.model.privilege.ACL;
import com.ecnice.privilege.service.privilege.IAclService;
import com.ecnice.privilege.utils.JsonUtils;
import com.ecnice.privilege.vo.SimpleReturnVo;
import com.ecnice.privilege.web.controller.BaseController;

/**
 * 
 * @Description:授权
 * @Author:Martin.Wang
 * @Since:2014-4-3
 * @Version:1.1.0
浙江蘑菇加电子商务有限公司 2014 ~ 2015 版权所有
 */
@Controller
@RequestMapping("/managment/privilege/acl")
public class ACLController extends BaseController {
	private static Logger logger = Logger.getLogger(ACLController.class);
	@Resource
	private IAclService aclService;

	
	/**
	 * 
	 * @return
	 * @Description:跳转到角色数据授权页面
	 */
	@RequestMapping("/roleDataModuleUI")
	@Permission(systemSn="privilege",moduleSn="role",value=PermissionConatant.PW)
	public String roleDataModuleUI(String sessionId,ModelMap model) {
		model.addAttribute("sessionId", sessionId);
		return "/privilege/role_datamodel_list";
	}
	
	/**
	 * 
	 * @return
	 * @Description:跳转到角色授权页面
	 */
	@RequestMapping("/roleModuleUI")
	@Permission(systemSn="privilege",moduleSn="role",value=PermissionConatant.PW)
	public String roleModuleUI(String sessionId,ModelMap model,String releaseSn, String systemId) {
		model.addAttribute("sessionId", sessionId);
		model.addAttribute("releaseSn", releaseSn);
		model.addAttribute("systemId", systemId);
		return "/privilege/role_module_list";
	}
	
	/**
	 * 
	 * @param priVal
	 * @return
	 * @Description:根据模块id和系统id获取权限值
	 */
	@ResponseBody
	@RequestMapping("/getAclByModuleId")
	@Permission(systemSn="privilege",moduleSn="role",value=PermissionConatant.PW)
	public String getPriValByModuleId(ACL params) {
		ACL acl = null;
		try {
			//acl = this.aclService.getAclBySystemSnAndModuleId(params);
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug("ACLController-getPriValByModuleId:" + e.getMessage());
		}
		return JsonUtils.toJson(acl);
	}
	
	/**
	 * 设置ACL的值
	 * @param acl acl
	 * @param position 多少位
	 * @param yes 是否选中
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/setAcl")
	@Permission(systemSn="privilege",moduleSn="role",value=PermissionConatant.C)
	public void setAcl(ACL params,Integer position,boolean yes) {
		try {
			if(position!=null) {
				this.aclService.createAcl(params, position, yes);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug("ACLController-getPriValByModuleId:" + e.getMessage());
		}
	}
	
	/**
	 * 批量设置ACL的值
	 * @param acl acl
	 * @param yes 全选或取消
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/setAllAcl")
	@Permission(systemSn="privilege",moduleSn="role",value=PermissionConatant.C)
	public void setAllAcl(ACL params,boolean yes) {
		try {
			this.aclService.createAllAcl(params, yes);
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug("ACLController-getPriValByModuleId:" + e.getMessage());
		}
	}
	
	/**
	 * 批量设置ACL的值
	 * @param acl acl
	 * @param yes 全选或取消
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/setAclByModule")
	@Permission(systemSn="privilege",moduleSn="role",value=PermissionConatant.C)
	public void setAclByModule(ACL params,boolean yes) {
		try {
			this.aclService.createAclByModule(params, yes);
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug("ACLController-getPriValByModuleId:" + e.getMessage());
		}
	}

	/**
	 * 
	 * @param releaseId
	 * @param systemSn
	 * @return
	 * @Description:根据角色id获取acl
	 */
	@ResponseBody
	@RequestMapping("/getAclsByRole")
	@Permission(systemSn="privilege",moduleSn="role",value=PermissionConatant.R)
	public String getAclByRole(String releaseId, String systemSn) {
		List<ACL> list = null;
		try {
			list = this.aclService.getOneAclsByRoleId(releaseId, systemSn);
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug("RoleController-getAclByRole:" + e.getMessage());
		}
		return JsonUtils.toJson(list);
	}

	/**
	 * 
	 * @param releaseId
	 * @param systemSn
	 * @return
	 * @Description:根据用户id获取acl
	 */
	@ResponseBody
	@RequestMapping("/getAclsByUser")
	@Permission(systemSn="privilege",moduleSn="role",value=PermissionConatant.R)
	public String getAclByUser(String releaseId, String systemSn) {
		List<ACL> list = null;
		try {
			list = this.aclService.getOneAclsByUserId(releaseId, systemSn);
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug("RoleController-getAclByUser:" + e.getMessage());
		}
		return JsonUtils.toJson(list);
	}

	/**
	 * @param acl
	 * @return
	 * @Description:添加acl
	 */
	@ResponseBody
	@RequestMapping("/insertAcl")
	@Permission(systemSn="privilege",moduleSn="role",value=PermissionConatant.C)
	public String insertAcl(ACL acl) {
		SimpleReturnVo vo;
		try {
			this.aclService.insertAcl(acl);
			vo = new SimpleReturnVo(this.SUCCESS, "成功");
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug("RoleController-insertAcl:" + e.getMessage());
			vo = new SimpleReturnVo(this.FAIL, "异常");
		}
		return JsonUtils.toJson(vo);
	}

	/**
	 * @param acl 删除的条件
	 * @return
	 * @Description:删除acl
	 */
	@ResponseBody
	@RequestMapping("/deleteAcl")
	@Permission(systemSn="privilege",moduleSn="role",value=PermissionConatant.D)
	public String deleteAcl(ACL acl) {
		SimpleReturnVo vo;
		try {
			this.aclService.deleteAcl(acl);
			vo = new SimpleReturnVo(this.SUCCESS, "成功");
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug("RoleController-deleteAcl:" + e.getMessage());
			vo = new SimpleReturnVo(this.FAIL, "异常");
		}
		return JsonUtils.toJson(vo);
	}
}
