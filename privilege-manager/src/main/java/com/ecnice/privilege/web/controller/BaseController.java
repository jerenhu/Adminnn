package com.ecnice.privilege.web.controller;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.ui.ModelMap;
import org.springframework.web.multipart.MultipartFile;

import com.ecnice.privilege.constant.PrivilegeConstant;
import com.ecnice.privilege.model.privilege.User;
import com.ecnice.privilege.model.system.SystemConfig;
import com.ecnice.privilege.utils.WebUtils;
import com.ecnice.tools.common.ServletContextUtil;

public class BaseController {
	// 成功
	public final int SUCCESS = 100;
	// 失败
	public final int FAIL = 101;
	
	public int success = 1;
	public int error = 0;
	
//	/** 成功 */
//	public static final int SUCCESS = 1;
//
//	/** 失败 */
//	public static final int ERROR = 0;


	public User getLoginUser(HttpServletRequest request){
		return (User) WebUtils.getSession(request).getAttribute(PrivilegeConstant.LOGIN_USER);
	}
	
	/**test123
	 * @Comment: 获取文件的扩展名
	 * @author liu.wj
	 * @Create Date 2013-5-29
	 * @param imgFile
	 * @return
	 */
	public static String getExt(MultipartFile imgFile) {
		return imgFile.getOriginalFilename()
				.substring(imgFile.getOriginalFilename().lastIndexOf('.') + 1)
				.toLowerCase();
	}

	/**
	 * @Comment: 获取文件的扩展名
	 * @author liu.wj
	 * @Create Date 2013-5-29
	 * @param imgFile
	 * @return
	 */
	public static String getExt(String fileName) {
		return fileName.substring(fileName.lastIndexOf('.') + 1).toLowerCase();
	}

	/**
	 * 验证上传文件类型是否属于图片格式
	 * 
	 * @return
	 */
	public static boolean validateImageFileType(MultipartFile imgFile) {
		if (imgFile != null && imgFile.getSize() > 0) {
			List<String> arrowType = Arrays.asList("image/bmp", "image/png",
					"image/gif", "image/jpg", "image/jpeg", "image/pjpeg");
			List<String> arrowExtension = Arrays.asList("gif", "jpg", "bmp",
					"png", "xls");
			String ext = getExt(imgFile);
			return arrowType.contains(imgFile.getContentType().toLowerCase())
					&& arrowExtension.contains(ext);
		}
		return true;
	}
	
	/**
	 * 得到系统的配置信息放到ModelMap中
	 * @param model
	 * @Description:
	 */
	@SuppressWarnings("unchecked")
	public ModelMap getSystemConfigsInfoToModelMap(HttpServletRequest request,ModelMap model) {
		List<SystemConfig> systemConfigs = (List<SystemConfig>) ServletContextUtil.getServletContext().getAttribute("systemConfigs");
		if (CollectionUtils.isNotEmpty(systemConfigs)) {
			for (SystemConfig systemConfig : systemConfigs) {
				if (null != systemConfig) {
					String key = systemConfig.getConfigKey();
					if (StringUtils.isNotBlank(key)) {
						key = key.trim();
						String value = systemConfig.getConfigValue();
						if (PrivilegeConstant.COMPANY_NAME.equals(key)) {
							//model.put("companyName", value);
							request.setAttribute("companyName", value);
						} else if (PrivilegeConstant.PLAIN_NAME.equals(key)) {
							//model.put("plainName", value);
							request.setAttribute("plainName", value);
						} else if (PrivilegeConstant.PLAIN_LOGO.equals(key)) {
							//model.put("plainLogo", value);
							request.setAttribute("plainLogo", value);
						} else if (PrivilegeConstant.PLAN_ICON.equals(key)) {
							//model.put("plainIcon", value);
							request.setAttribute("plainIcon", value);
						}
					}
				}
			}
		}
		
		return model;
	}
}