package com.ecnice.privilege.web.listener;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.ecnice.privilege.model.system.SystemConfig;
import com.ecnice.privilege.service.system.ISystemConfigService;
import com.ecnice.tools.common.ServletContextUtil;
import com.ecnice.tools.common.SpringContextHolder;

/**
 * @Title:
 * @Description:系统初始化监听器
 * @Author:Bruce.Liu
 * @Since:2014年4月18日
 * @Version:1.1.0 浙江蘑菇加电子商务有限公司 2014 ~ 2015 版权所有
 */
public class SystemInitListener implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		ServletContext servletContext = sce.getServletContext();
		ISystemConfigService systemConfigService = SpringContextHolder.getBean("systemConfigServiceImpl");
		try {
			List<SystemConfig> systemConfigs = systemConfigService.getSystemConfigs();
			servletContext.setAttribute("systemConfigs", systemConfigs); // 将系统配置信息放到servletContext中去
			
			// 生成logo和favicon
			StringBuilder path = new StringBuilder();
			path.append(ServletContextUtil.getServletContext().getRealPath("/"));
			String logoPath = path+"assets/images/ec-logo.png";
			this.createFile("logo", systemConfigService, logoPath);
			String faviconPath = path+"images/favicon.ico";
			this.createFile("favicon", systemConfigService, faviconPath);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void createFile(String key, ISystemConfigService systemConfigService, String destFilePath)
			throws Exception {
		SystemConfig config = systemConfigService.findSystemConfigByKey(key);
		if(config!=null) {
			FileOutputStream os = new FileOutputStream(destFilePath);
			ByteArrayInputStream in = new ByteArrayInputStream(config.getImage());
			// 以写字节的方式写文件
			int b = 0;
			while ((b = in.read()) != -1) {
				os.write(b);
			}
			os.flush();
			os.close();
			in.close();
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {

	}

}
