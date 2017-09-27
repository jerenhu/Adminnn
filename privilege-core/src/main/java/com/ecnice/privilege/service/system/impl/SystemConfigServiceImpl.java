package com.ecnice.privilege.service.system.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ecnice.privilege.common.PagerModel;
import com.ecnice.privilege.common.Query;
import com.ecnice.privilege.dao.system.ISystemConfigDao;
import com.ecnice.privilege.model.system.SystemConfig;
import com.ecnice.privilege.service.system.ISystemConfigService;
import com.ecnice.tools.common.ServletContextUtil;
import com.ecnice.tools.common.UUIDGenerator;

/**
 * @Title:
 * @Description:系统配置servie实现类
 * @Author:Bruce.Liu
 * @Since:2014年4月1日
 * @Version:1.1.0
浙江蘑菇加电子商务有限公司 2014 ~ 2015 版权所有
 */
@Service
public class SystemConfigServiceImpl implements ISystemConfigService {

	@Resource
	private ISystemConfigDao systemConfigDao;

	@Override
	public SystemConfig findSystemConfigByKey(String key) throws Exception {
		@SuppressWarnings("unchecked")
		List<SystemConfig> configs = (List<SystemConfig>) ServletContextUtil.getAppObject("systemConfigs");
		if(configs!=null && configs.size()>0) {
			for(SystemConfig config : configs) {
				if(config.getConfigKey().equals(key)) {
					return config;
				}
			}
		}
		return null;
	}

	@Override
	public void delSystemConfigs(String[] ids) throws Exception {
		for(String id : ids) {
			systemConfigDao.delSystemConfig(id);
		}
	}

	@Override
	public List<SystemConfig> getSystemConfigsBySn(String configSn)
			throws Exception {
		return systemConfigDao.getSystemConfigsBySn(configSn);
	}

	@Override
	public List<SystemConfig> getSystemConfigsByKey(String configKey) throws Exception {
		return systemConfigDao.getSystemConfigsByKey(configKey);
	}
	
	@Override
	public List<SystemConfig> getSystemConfigs() throws Exception {
		return systemConfigDao.getSystemConfigs();
	}

	@Override
	public void insertSystemConfig(SystemConfig config) throws Exception {
		config.setId(UUIDGenerator.generate());
		systemConfigDao.insertSystemConfig(config);
	}

	@Override
	public void updateSystemConfig(SystemConfig config) throws Exception {
		systemConfigDao.updateSystemConfig(config);
	}

	@Override
	public void delSystemConfig(String id) throws Exception {
		systemConfigDao.delSystemConfig(id);
	}

	@Override
	public SystemConfig getSystemConfigById(String id) throws Exception {
		return systemConfigDao.getSystemConfigById(id);
	}

	@Override
	public PagerModel<SystemConfig> getPagerModel(SystemConfig config,
			Query query) throws Exception {
		return systemConfigDao.getPagerModel(config, query);
	}

}
