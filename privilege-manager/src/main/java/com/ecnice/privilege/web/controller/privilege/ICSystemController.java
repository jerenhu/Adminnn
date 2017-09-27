package com.ecnice.privilege.web.controller.privilege;

import java.util.List;

import javax.annotation.Resource;

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
import com.ecnice.privilege.constant.ReturnCode;
import com.ecnice.privilege.model.privilege.ICSystem;
import com.ecnice.privilege.service.privilege.IICSystemService;
import com.ecnice.privilege.utils.JsonUtils;
import com.ecnice.privilege.vo.SimpleReturnVo;
import com.ecnice.privilege.web.controller.BaseController;

/**
 * 系统管理模块
 * 
 * @author Martin.Wang
 * 
 */
@Controller
@RequestMapping("/managment/privilege/icsystem")
public class ICSystemController extends BaseController {
	private static Logger logger = Logger.getLogger(ICSystemController.class);

	@Resource
	private IICSystemService iICSystemService;

	/**
	 * 跳转到系统分页列表
	 * 
	 * @author Martin.Wang
	 * @return 系统列表页面路径
	 */
	@RequestMapping("/list")
	@Permission(systemSn="privilege",moduleSn="icsystem",value=PermissionConatant.R)
	public String list(String sessionId,ModelMap model) {
		model.put("sessionId", sessionId);
		return "/privilege/icsystem_page";
	}

	/**
	 * 异步分页数据
	 * 
	 * @author Martin.Wang
	 * @param query
	 *            分页参数对象，主要参数：pageSize:每页显示的条数,pageIndex：页数
	 * @param icSystem
	 *            筛选条件对象，将会作为查询的条件
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/ajaxlist")
	@Permission(systemSn="privilege",moduleSn="icsystem",value=PermissionConatant.R)
	public String ajaxList(Query query, ICSystem icSystem) {
		PagerModel<ICSystem> pm = null;
		try {
			pm = this.iICSystemService.getPagerIcSystem(icSystem, query);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("ICSystemController-ajaxList:" + e.getMessage());
		}
		return JsonUtils.getPmJson(pm);
	}

	/**
	 * 跳转到系统添加页面
	 * 
	 * @author Martin.Wang
	 * @return 系统添加页面路径
	 */
	@RequestMapping("/insertUI")
	@Permission(systemSn="privilege",moduleSn="icsystem",value=PermissionConatant.C)
	public String insert(String sessionId,ModelMap model) {
		model.put("sessionId", sessionId);
		return "/privilege/icsystem_insert";
	}

	/**
	 * 跳转到系统修改页面
	 * 
	 * @param model
	 *            给页面传值
	 * @author Martin.Wang
	 * @return 系统修改页面路径
	 */
	@RequestMapping("/updateUI")
	@Permission(systemSn="privilege",moduleSn="icsystem",value=PermissionConatant.U)
	public String updateUI(String sessionId,ModelMap model) {
		model.put("sessionId", sessionId);
		return "/privilege/icsystem_update";
	}

	/**
	 * 根据id获取系统对象数据
	 * 
	 * @author Martin.Wang
	 * @param id
	 *            系统id
	 * @return 系统对象json字符串
	 */
	@ResponseBody
	@RequestMapping("/ajaxUpdate")
	@Permission(systemSn="privilege",moduleSn="icsystem",value=PermissionConatant.U)
	public String ajaxUpdate(String id) {
		ICSystem icSystem = null;
		try {
			icSystem = this.iICSystemService.getICSystemById(id);
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug("ICSystemController-ajaxUpdate:" + e.getMessage());
		}
		return JsonUtils.toJson(icSystem);
	}

	/**
	 * 添加系统对象
	 * 
	 * @author Martin.Wang
	 * @param request
	 * @param icSystem
	 *            系统对象，用于接收表单提交的参数
	 * @return 响应的json字符串
	 */
	@ResponseBody
	@RequestMapping("/insert")
	@Permission(systemSn="privilege",moduleSn="icsystem",value=PermissionConatant.C)
	public String insert(ICSystem icSystem) {
		SimpleReturnVo vo = null;
		try {
			this.iICSystemService.insertIcSystem(icSystem);
			vo = new SimpleReturnVo(ReturnCode.SUCCESS, "成功");
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug("ICSystemController-insert:" + e.getMessage());
			vo = new SimpleReturnVo(ReturnCode.FAIL, "异常");
		}
		return JsonUtils.toJson(vo);
	}

	/**
	 * 修改系统对象
	 * 
	 * @author Martin.Wang
	 * @param request
	 * @param icSystem
	 *            系统对象，用于接收表单提交的参数
	 * @return 响应的json字符串
	 */
	@ResponseBody
	@RequestMapping("/update")
	@Permission(systemSn="privilege",moduleSn="icsystem",value=PermissionConatant.U)
	public String update(ICSystem icSystem) {
		SimpleReturnVo vo = null;
		try {
			this.iICSystemService.updateIcSystem(icSystem);
			vo = new SimpleReturnVo(ReturnCode.SUCCESS, "成功");
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug("ICSystemController-insert:" + e.getMessage());
			vo = new SimpleReturnVo(ReturnCode.FAIL, "异常");
		}
		return JsonUtils.toJson(vo);
	}
	/**
	 * 删除
	 * 
	 * @author Martin.Wang
	 * @param ids
	 *            id,id,id格式的字符串
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/delete")
	@Permission(systemSn="privilege",moduleSn="icsystem",value=PermissionConatant.D)
	public String delete(String ids) {
		SimpleReturnVo vo;
		if (StringUtils.isNotBlank(ids)) {
			String[] id = ids.split(",");
			try {
				if (this.iICSystemService.exsitsModule(id)) {
					vo = new SimpleReturnVo(ReturnCode.FAIL, "该系统有子模块，请先删除子模块!");
					return JsonUtils.toJson(vo);
				}
				this.iICSystemService.deleteIcSystems(id);
			} catch (Exception e) {
				e.printStackTrace();
				logger.debug("ICSystemController-delete:" + e.getMessage());
				vo = new SimpleReturnVo(ReturnCode.FAIL, "异常错误!");
				return JsonUtils.toJson(vo);
			}
		}
		vo = new SimpleReturnVo(ReturnCode.SUCCESS, "成功");
		return JsonUtils.toJson(vo);
	}
	
	@ResponseBody
	@RequestMapping("/checkSnExsits")
	public String checkSnExsits(String sn,String id){
		ICSystem icSystem=new ICSystem();
		icSystem.setSn(sn);
		try {
			List<ICSystem> list=this.iICSystemService.getAllIcSystem(icSystem);
			if(StringUtils.isNotBlank(id)){//更新的时候
				ICSystem sys=this.iICSystemService.getICSystemById(id);
				if(sys.getSn().equals(sn)){
					return "0";
				}else{
					if(list!=null && list.size()>0){
						return "1";
					}
				}
			}else{//新增的时候
				if(list!=null && list.size()>0){
					return "1";
				}
			}
		} catch (Exception e) {
			logger.debug("ICSystemController-checkSnExsits:" + e.getMessage());
			e.printStackTrace();
		}
		return "0";
	}
}
