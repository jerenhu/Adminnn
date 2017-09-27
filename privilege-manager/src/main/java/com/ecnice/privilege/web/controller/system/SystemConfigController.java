package com.ecnice.privilege.web.controller.system;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.ecnice.privilege.common.PagerModel;
import com.ecnice.privilege.common.Permission;
import com.ecnice.privilege.common.Query;
import com.ecnice.privilege.constant.PermissionConatant;
import com.ecnice.privilege.model.system.SystemConfig;
import com.ecnice.privilege.service.system.ISystemConfigService;
import com.ecnice.privilege.utils.JsonUtils;
import com.ecnice.privilege.vo.SimpleReturnVo;
import com.ecnice.privilege.web.controller.BaseController;
import com.ecnice.tools.common.ServletContextUtil;

/**
 * @Title:
 * @Description:系统配置控制中心
 * @Author:Bruce.Liu
 * @Since:2014年4月1日
 * @Version:1.1.0 浙江蘑菇加电子商务有限公司 2014 ~ 2015 版权所有
 */
@Controller
@RequestMapping("/managment/system/systemConfig")
public class SystemConfigController extends BaseController {
	private static Logger logger = Logger.getLogger(SystemConfigController.class);
	@Resource
	private ISystemConfigService systemConfigService;

	public void setSystemConfigsInfoToServletContext() {
		try {
			List<SystemConfig> systemConfigs = this.systemConfigService.getSystemConfigs();
			ServletContextUtil.getServletContext().setAttribute("systemConfigs", systemConfigs);
		} catch (Exception e) {
			logger.error("设置systemConfigs信息报错", e);
			e.printStackTrace();
		}
	}

	@RequestMapping("/list")
	@Permission(systemSn = "privilege", moduleSn = "config", value = PermissionConatant.R)
	public String list(String sessionId, ModelMap model) {
		model.addAttribute("sessionId", sessionId);
		return "/system/system_config_page";
	}

	@ResponseBody
	@RequestMapping("/init")
	@Permission(systemSn = "privilege", moduleSn = "config", value = PermissionConatant.C)
	public String init(HttpServletRequest request) throws Exception {
		List<SystemConfig> systemConfigs = systemConfigService.getSystemConfigs();
		request.getSession().getServletContext().setAttribute("systemConfigs", systemConfigs);
		SimpleReturnVo vo = new SimpleReturnVo(SUCCESS, "成功");
		return JsonUtils.toJson(vo);
	}

	@ResponseBody
	@RequestMapping("/ajaxList")
	@Permission(systemSn = "privilege", moduleSn = "config", value = PermissionConatant.R)
	public String ajaxList(SystemConfig config, Query query) {
		PagerModel<SystemConfig> pm = null;
		try {
			pm = systemConfigService.getPagerModel(config, query);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("SystemConfigController-ajaxList:" + e.getMessage());
		}
		return JsonUtils.getPmJson(pm);
	}

	@RequestMapping("/addUI")
	@Permission(systemSn = "privilege", moduleSn = "config", value = PermissionConatant.C)
	public String addUI(String sessionId, ModelMap model) {
		model.addAttribute("sessionId", sessionId);
		return "/system/system_config_insert";
	}

	@ResponseBody
	@RequestMapping("/add")
	@Permission(systemSn = "privilege", moduleSn = "config", value = PermissionConatant.C)
	public String add(SystemConfig systemConfig) {
		SimpleReturnVo vo = null;
		try {
			systemConfigService.insertSystemConfig(systemConfig);
			vo = new SimpleReturnVo(SUCCESS, "成功");

			// 把配置信息放到servletContext中一份
			this.setSystemConfigsInfoToServletContext();
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug("SystemConfigController-add:" + e.getMessage());
			vo = new SimpleReturnVo(FAIL, "异常");
		}
		return JsonUtils.toJson(vo);
	}

	@RequestMapping("/updateUI")
	@Permission(systemSn = "privilege", moduleSn = "config", value = PermissionConatant.U)
	public String updateUI(String sessionId, ModelMap model) {
		model.addAttribute("sessionId", sessionId);
		return "/system/system_config_update";
	}

	@ResponseBody
	@RequestMapping("/ajaxUpdate")
	@Permission(systemSn = "privilege", moduleSn = "config", value = PermissionConatant.U)
	public String ajaxUpdate(String id) {
		SystemConfig config = null;
		try {
			config = systemConfigService.getSystemConfigById(id);
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug("SystemConfigController-ajaxUpdate:" + e.getMessage());
		}
		return JsonUtils.toJson(config);
	}

	@ResponseBody
	@RequestMapping("/update")
	@Permission(systemSn = "privilege", moduleSn = "config", value = PermissionConatant.U)
	public String update(SystemConfig systemConfig) {
		SimpleReturnVo vo = null;
		try {
			systemConfigService.updateSystemConfig(systemConfig);
			vo = new SimpleReturnVo(SUCCESS, "成功");

			// 把配置信息放到servletContext中一份
			this.setSystemConfigsInfoToServletContext();
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug("SystemConfigController-update:" + e.getMessage());
			vo = new SimpleReturnVo(FAIL, "异常");
		}
		return JsonUtils.toJson(vo);
	}

	/**
	 * 修改个性化配置
	 * 
	 * @param systemConfig
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/updatePerson")
	public String updatePerson(SystemConfig systemConfig) {
		SimpleReturnVo vo = null;
		String perValue = systemConfig.getConfigValue();
		try {
			SystemConfig oldSystemConfig = systemConfigService.findSystemConfigByKey(systemConfig.getConfigKey());
			oldSystemConfig.setConfigValue(perValue);
			systemConfigService.updateSystemConfig(oldSystemConfig);
			vo = new SimpleReturnVo(SUCCESS, "成功");
			// 把配置信息放到servletContext中一份
			this.setSystemConfigsInfoToServletContext();
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug("SystemConfigController-update:" + e.getMessage());
			vo = new SimpleReturnVo(FAIL, "异常");
		}
		return JsonUtils.toJson(vo);
	}

	@ResponseBody
	@RequestMapping("/delete")
	@Permission(systemSn = "privilege", moduleSn = "config", value = PermissionConatant.D)
	public String delete(String idStrs) {
		SimpleReturnVo vo = null;
		if (idStrs != null && idStrs.length() > 0) {
			String[] ids = idStrs.split(",");
			try {
				systemConfigService.delSystemConfigs(ids);
				vo = new SimpleReturnVo(SUCCESS, "成功");

				// 把配置信息放到servletContext中一份
				this.setSystemConfigsInfoToServletContext();
			} catch (Exception e) {
				e.printStackTrace();
				logger.debug("SystemConfigController-delete:" + e.getMessage());
				vo = new SimpleReturnVo(FAIL, "异常");
			}
		}
		return JsonUtils.toJson(vo);
	}

	@ResponseBody
	@RequestMapping("/checkSnExsits")
	public String checkSnExsits(SystemConfig systemConfig) {
		try {
			List<SystemConfig> list = this.systemConfigService.getSystemConfigsBySn(systemConfig.getConfigSn());
			if (StringUtils.isNotBlank(systemConfig.getId())) {
				SystemConfig scf = this.systemConfigService.getSystemConfigById(systemConfig.getId());
				if (scf != null && scf.getConfigSn().equals(systemConfig.getConfigSn())) {
					return "0";
				} else {
					if (list != null && list.size() > 0) {
						return "1";
					}
				}
			} else {
				if (list != null && list.size() > 0) {
					return "1";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug("RoleController-checkSnExsits:" + e.getMessage());
		}
		return "0";
	}

	@ResponseBody
	@RequestMapping("/checkKeyExsits")
	public String checkKeyExsits(SystemConfig systemConfig) {
		try {
			List<SystemConfig> list = this.systemConfigService.getSystemConfigsBySn(systemConfig.getConfigKey());
			if (StringUtils.isNotBlank(systemConfig.getId())) {
				SystemConfig scf = this.systemConfigService.getSystemConfigById(systemConfig.getId());
				if (scf != null && scf.getConfigKey().equals(systemConfig.getConfigKey())) {
					return "0";
				} else {
					if (list != null && list.size() > 0) {
						return "1";
					}
				}
			} else {
				if (list != null && list.size() > 0) {
					return "1";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug("RoleController-checkKeyExsits:" + e.getMessage());
		}
		return "0";
	}

	/**
	 * 个性化上传图片
	 * 
	 * @return
	 */
	@RequestMapping("/uploadImageUI")
	public String uploadImageUI(String sessionId, ModelMap model,HttpServletRequest request) {
		model.addAttribute("sessionId", sessionId);
		// 得到系统配置信息
		super.getSystemConfigsInfoToModelMap(request,model);
		return "/system/common_conf";
	}

	/**
	 * 描述：文件上传
	 * 
	 * @param file
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/uploadImage")
	public String uploadImage(MultipartFile filedata, String filename) {
		String destFilePath = "";
		try {
			if (StringUtils.isNotBlank(filename)) {
				StringBuilder path = new StringBuilder();
				path.append(ServletContextUtil.getServletContext().getRealPath("/"));
				if ("logo".equals(filename)) {
					path.append("assets/images/ec-logo.png");
				} else if ("favicon".equals(filename)) {
					path.append("images/favicon.ico");
				}
				destFilePath = path.toString();
				// 拿到上传文件的输入流
				InputStream in = filedata.getInputStream();
				byte[] image = this.input2byte(in);
				SystemConfig config = new SystemConfig();
				config.setConfigName(filename);
				config.setImage(image);
				config.setConfigKey(filename);
				SystemConfig newConfig = systemConfigService.findSystemConfigByKey(filename);
				if(newConfig!=null) {
					config.setId(newConfig.getId());
					systemConfigService.updateSystemConfig(config);
				}else {
					systemConfigService.insertSystemConfig(config);
				}
				 FileOutputStream os = new FileOutputStream(destFilePath);
				 InputStream newIn = byte2Input(image);
				 // 以写字节的方式写文件
				 int b = 0;
				 while ((b = newIn.read()) != -1) {
					 os.write(b);
				 }
				 os.flush();
				 os.close();
				 in.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("SystemConfigController-uploadImage" + e);
			JsonUtils.toJson(new SimpleReturnVo(0, "上传失败"));
		}
		SimpleReturnVo returnVO = new SimpleReturnVo(this.SUCCESS, destFilePath);
		return JsonUtils.toJson(returnVO);
	}

	public String inputStream2String(InputStream is) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int i = -1;
		while ((i = is.read()) != -1) {
			baos.write(i);
		}
		return baos.toString();
	}

	public static final InputStream byte2Input(byte[] buf) {
		return new ByteArrayInputStream(buf);
	}

	public static final byte[] input2byte(InputStream inStream) throws IOException {
		ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
		byte[] buff = new byte[100];
		int rc = 0;
		while ((rc = inStream.read(buff, 0, 100)) > 0) {
			swapStream.write(buff, 0, rc);
		}
		byte[] in2b = swapStream.toByteArray();
		return in2b;
	}
}
