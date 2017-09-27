package com.ecnice.privilege.web.controller.privilege;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ecnice.privilege.model.privilege.ICSystem;
import com.ecnice.privilege.model.privilege.RoleSystem;
import com.ecnice.privilege.service.privilege.IICSystemService;
import com.ecnice.privilege.service.privilege.IRoleSystemService;
import com.ecnice.privilege.web.controller.BaseController;
import com.ecnice.tools.common.JsonUtils;



/**
 * 延时短信Controller
 * @author bruce.liu
 * @date 2016-12-23 16:53:22
 */
@Controller
@RequestMapping("/managment/privilege/roleSystem")
public class RoleSystemController extends BaseController {
	
	private static Logger logger = Logger.getLogger(RoleSystemController.class);
	
	@Resource
	private IRoleSystemService roleSystemService;
	@Resource
	private IICSystemService icSystemService;
	
	/**
	 * 获取系统列表
	 * @return
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping("/systemList")
	public String systemList(String roleId) throws Exception  {
		//1:获取角色的系统
		List<RoleSystem> rsList = roleSystemService.getRoleSystemsByRoleId(roleId);
		List<ICSystem> list = icSystemService.getAllIcSystem(null);
		for(ICSystem system : list) {
			for(RoleSystem rs : rsList) {
				if(system.getId().equals(rs.getSystemId())) {
					system.setChecked(true);
					break;
				}
			}
		}
		return JsonUtils.toJson(list);
	}
	
}
