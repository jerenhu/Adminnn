package com.ecnice.privilege.web.controller.system;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ecnice.privilege.common.Permission;
import com.ecnice.privilege.common.Query;
import com.ecnice.privilege.constant.PermissionConatant;
import com.ecnice.privilege.model.system.Dictionary;
import com.ecnice.privilege.service.system.IDictionaryService;
import com.ecnice.privilege.utils.JsonUtils;
import com.ecnice.privilege.vo.SimpleReturnVo;
import com.ecnice.privilege.web.controller.BaseController;

/**
 * 
 * @Description:数据字典控制器
 * @Author:Martin.Wang
 * @Since:2014-4-2
 * @Version:1.1.0
浙江蘑菇加电子商务有限公司 2014 ~ 2015 版权所有
 */
@Controller
@RequestMapping("/managment/system/dictionary")
public class DictionaryController extends BaseController{
	private static Logger logger = Logger.getLogger(DictionaryController.class);
	@Resource
	private IDictionaryService dictionaryService;
	
	/**
	 * 
	 * @return
	 * @Description:数据字典列表页面
	 */
	@RequestMapping("/list")
	@Permission(systemSn="privilege",moduleSn="dictionary",value=PermissionConatant.R)
	public String list(String sessionId,ModelMap model) {
		model.addAttribute("sessionId", sessionId);
		return "/system/dictionary_page";
	}
	
	/**
	 * 
	 * @param Dictionary
	 * @param query
	 * @return
	 * @Description:数据字典列表数据
	 */
	@ResponseBody
	@RequestMapping("/getAll")
	public String getAll(Dictionary dictionary,Query query){
		List<Dictionary> dictionarys=null;
		try{
			dictionarys=this.dictionaryService.getAll(dictionary);
		}catch(Exception e){
			e.printStackTrace();
			logger.debug("DictionaryController-getAll:"+e.getMessage());
		}
		return JsonUtils.toJson(dictionarys);
	}
	
	/**
	 * 
	 * @return
	 * @Description:添加数据字典页面
	 */
	@RequestMapping("/insertUI")
	@Permission(systemSn="privilege",moduleSn="dictionary",value=PermissionConatant.C)
	public String insertUI(String sessionId,String pcode,ModelMap model,String systemSn){
		model.addAttribute("systemSn", systemSn);
		if(StringUtils.isNotBlank(pcode)){
			model.addAttribute("pcode", pcode);
		}else{
			model.addAttribute("pcode", "");
		}
		model.addAttribute("sessionId", sessionId);
		return "/system/dictionary_insert";
	}
	
	/**
	 * 
	 * @return
	 * @Description:数据字典修改页面
	 */
	@RequestMapping("/updateUI")
	@Permission(systemSn="privilege",moduleSn="dictionary",value=PermissionConatant.U)
	public String updateUI(String sessionId,ModelMap model){
		model.addAttribute("sessionId", sessionId);
		return "/system/dictionary_update";
	}
	
	/**
	 * 
	 * @param Dictionary
	 * @return
	 * @Description:添加数据字典
	 */
	@ResponseBody
	@RequestMapping("/insert")
	@Permission(systemSn="privilege",moduleSn="dictionary",value=PermissionConatant.C)
	public String insert(Dictionary dictionary){
		SimpleReturnVo vo;
		try{
			if(StringUtils.isBlank(dictionary.getSn())){
				dictionary.setSn(null);
			}
			if(StringUtils.isBlank(dictionary.getPcode())){
				dictionary.setPcode(null);
			}
			this.dictionaryService.insertDictionary(dictionary);
			vo=new SimpleReturnVo(this.SUCCESS, "成功");
		}catch(Exception e){
			e.printStackTrace();
			logger.debug("DictionaryController-insert:"+e.getMessage());
			vo=new SimpleReturnVo(this.FAIL, "异常");
		}
		return JsonUtils.toJson(vo);
	}
	
	/**
	 * 
	 * @param id
	 * @return
	 * @Description:根据id获取数据字典信息
	 */
	@ResponseBody
	@RequestMapping("/ajaxUpdate")
	@Permission(systemSn="privilege",moduleSn="dictionary",value=PermissionConatant.U)
	public String ajaxUpdate(String id){
		Dictionary dictionary=null;
		try{
			dictionary=this.dictionaryService.getDictionaryById(id);
		}catch(Exception e){
			e.printStackTrace();
			logger.debug("DictionaryController-ajaxUpdate:"+e.getMessage());
		}
		return JsonUtils.toJson(dictionary);
	}
	
	/**
	 * 
	 * @param Dictionary
	 * @return
	 * @Description:更新数据字典
	 */
	@ResponseBody
	@RequestMapping("/update")
	@Permission(systemSn="privilege",moduleSn="dictionary",value=PermissionConatant.U)
	public String update(Dictionary dictionary){
		SimpleReturnVo vo;
		try{
			if(StringUtils.isBlank(dictionary.getSn())){
				dictionary.setSn(null);
			}
			this.dictionaryService.updateDictionary(dictionary);
			vo=new SimpleReturnVo(this.SUCCESS, "成功");
		}catch(Exception e){
			e.printStackTrace();
			logger.debug("DictionaryController-update:"+e.getMessage());
			vo=new SimpleReturnVo(this.FAIL, "异常");
		}
		return JsonUtils.toJson(vo);
	}
	
	/**
	 * 
	 * @param ids id，id,id 
	 * @return
	 * @Description:根据数据字典id批量删除数据字典
	 */
	@ResponseBody
	@RequestMapping("/delete")
	@Permission(systemSn="privilege",moduleSn="dictionary",value=PermissionConatant.D)
	public String delete(String ids){
		SimpleReturnVo vo;
		try{
			if(StringUtils.isNotBlank(ids)){
				String [] id=ids.split(",");
				this.dictionaryService.delDictionarys(id);
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.debug("DictionaryController-delete:"+e.getMessage());
			vo=new SimpleReturnVo(this.FAIL, "异常");
		}
		vo=new SimpleReturnVo(this.SUCCESS, "成功");
		return JsonUtils.toJson(vo);
	}
	
	/**
	 * 
	 * @param Dictionary
	 * @return
	 * @Description:检测数据字典编码的唯一性
	 */
	@ResponseBody
	@RequestMapping("/checkDictionaryCodeExsits")
	public String checkDictionaryNameExsits(Dictionary dictionary){
		try{
			Dictionary params=new Dictionary();
			params.setCode(dictionary.getCode());
			params.setSystemSn(dictionary.getSystemSn());
			List<Dictionary> list=this.dictionaryService.getAll(params);
			if(dictionary.getId()!=null){
				Dictionary dic=this.dictionaryService.getDictionaryById(dictionary.getId().toString());
				if(dic!=null && dic.getCode().equals(dictionary.getCode())){
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
			e.printStackTrace();
			logger.debug("DictionaryController-checkDictionaryCodeExsits:"+e.getMessage());
		}
		return "0";
	}
	
}
