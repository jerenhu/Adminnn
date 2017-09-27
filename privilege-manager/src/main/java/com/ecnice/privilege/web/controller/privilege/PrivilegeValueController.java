package com.ecnice.privilege.web.controller.privilege;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ecnice.privilege.constant.ReturnCode;
import com.ecnice.privilege.model.privilege.SystemPrivilegeValue;
import com.ecnice.privilege.service.privilege.ISystemPrivilegeValueService;
import com.ecnice.privilege.vo.ReturnVo;
import com.ecnice.tools.common.JsonUtils;

/** 
 * @Description:系统权限值controller
 * @Author:Martin.Wang
 * @Since:2014-7-28
浙江蘑菇加电子商务有限公司 2014 ~ 2015 版权所有 
 */
@Controller
@RequestMapping("/managment/privilege/pval")
public class PrivilegeValueController {
	private static Logger logger = Logger.getLogger(PrivilegeValueController.class);
	@Resource
	private ISystemPrivilegeValueService systemPrivilegeValueService;
	
	@RequestMapping("/list")
	public String list(String sessionId,ModelMap model){
		model.put("sessionId", sessionId);
		return "/privilege/pval_page";
	}
	
	@RequestMapping("/insertUI")
	public String insertUI(String sessionId,ModelMap model,String systemId){
		model.put("sessionId", sessionId);
		model.put("systemId", systemId);
		return "/privilege/pval_insert";
	}
	
	@RequestMapping("/updateUI")
	public String updateUI(String sessionId,ModelMap model,String systemId){
		model.put("sessionId", sessionId);
		model.put("systemId", systemId);
		return "/privilege/pval_update";
	}
	
	@ResponseBody
	@RequestMapping("/ajaxlist")
	public String ajaxlist(SystemPrivilegeValue privilegeValue){
		List<SystemPrivilegeValue> list=null;
		try{
			list=this.systemPrivilegeValueService.getAll(privilegeValue);
		}catch(Exception e){
			e.printStackTrace();
			logger.error("获取权限值列表异常：PrivilegeValueController-ajaxlist:"+e.getMessage());
		}
		return JsonUtils.toJson(list);
	}
	
	@ResponseBody
	@RequestMapping("/ajaxUpdate")
	public String ajaxUpdate(String id){
		SystemPrivilegeValue sp=null;
		try{
			sp=this.systemPrivilegeValueService.getSystemPrivilegeValueById(id);
		}catch(Exception e){
			e.printStackTrace();
			logger.error("获取权限值列表异常：PrivilegeValueController-ajaxlist:"+e.getMessage());
		}
		return JsonUtils.toJson(sp);
	}
	
	@ResponseBody
	@RequestMapping("/insert")
	public String insert(SystemPrivilegeValue privilegeValue){
		ReturnVo<SystemPrivilegeValue> vo=new ReturnVo<SystemPrivilegeValue>();
		try{
			this.systemPrivilegeValueService.insertSystemPrivilegeValue(privilegeValue);
			vo.setStatus(ReturnCode.SUCCESS.toString());
			vo.setMessage("成功");
		}catch(Exception e){
			e.printStackTrace();
			vo.setStatus(ReturnCode.FAIL.toString());
			vo.setMessage("异常");
			logger.error("添加权限值异常：PrivilegeValueController-insert:"+e.getMessage());
		}
		return JsonUtils.toJson(vo);
	}
	
	@ResponseBody
	@RequestMapping("/update")
	public String update(SystemPrivilegeValue privilegeValue){
		ReturnVo<SystemPrivilegeValue> vo=new ReturnVo<SystemPrivilegeValue>();
		try{
			this.systemPrivilegeValueService.updateSystemPrivilegeValue(privilegeValue);
			vo.setStatus(ReturnCode.SUCCESS.toString());
			vo.setMessage("成功");
		}catch(Exception e){
			e.printStackTrace();
			vo.setStatus(ReturnCode.FAIL.toString());
			vo.setMessage("异常");
			logger.error("修改权限值异常：PrivilegeValueController-update:"+e.getMessage());
		}
		return JsonUtils.toJson(vo);
	}
	
	/**
	 * 
	 * @Description:同一个系统下得权限值不能重复
	 * @param privilegeValue
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/checkExsits")
	public String checkSnExsits(SystemPrivilegeValue privilegeValue){
		List<SystemPrivilegeValue> list=null;
		try{
			SystemPrivilegeValue params=new SystemPrivilegeValue();
			params.setPosition(privilegeValue.getPosition());
			params.setSystemId(privilegeValue.getSystemId());
			list=this.systemPrivilegeValueService.getAll(privilegeValue);
			if(privilegeValue.getId()!=null){
				SystemPrivilegeValue spv=this.systemPrivilegeValueService.getSystemPrivilegeValueById(privilegeValue.getId().toString());
				if(spv!=null && spv.getPosition().equals(privilegeValue.getPosition())){
					return "0";
				}else{
					if(list!=null && list.size()>0){
						return "1";
					}
				}
			}else{
				if(list!=null && list.size()>0){
					return "1";
				}
			}
		}catch(Exception e){
			logger.error("校验唯一性异常：PrivilegeValueController-checkExsits:",e);
		}
		return JsonUtils.toJson(list);
	}
	
	@ResponseBody
	@RequestMapping("/delete")
	public String delete(String ids){
		ReturnVo<SystemPrivilegeValue> vo=new ReturnVo<SystemPrivilegeValue>();
		try{
			if(StringUtils.isNotBlank(ids)){
				String [] id=ids.split(",");
				this.systemPrivilegeValueService.delSystemPrivilegeValueById(id);
				vo.setStatus(ReturnCode.SUCCESS.toString());
				vo.setMessage("成功");
			}
			vo.setStatus(ReturnCode.FAIL.toString());
			vo.setMessage("id为空!");
		}catch(Exception e){
			e.printStackTrace();
			vo.setStatus(ReturnCode.FAIL.toString());
			vo.setMessage("异常");
			logger.error("删除权限值异常：PrivilegeValueController-delete:"+e.getMessage());
		}
		return JsonUtils.toJson(vo);
	}
}

	