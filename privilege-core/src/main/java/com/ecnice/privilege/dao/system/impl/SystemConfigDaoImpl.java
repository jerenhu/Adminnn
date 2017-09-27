package com.ecnice.privilege.dao.system.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ecnice.privilege.common.PagerModel;
import com.ecnice.privilege.common.Query;
import com.ecnice.privilege.dao.MybatisTemplate;
import com.ecnice.privilege.dao.system.ISystemConfigDao;
import com.ecnice.privilege.model.system.SystemConfig;

/**
 * @Title:
 * @Description:dao实现类
 * @Author:Bruce.Liu
 * @Since:2014年4月1日
 * @Version:1.1.0
浙江蘑菇加电子商务有限公司 2014 ~ 2015 版权所有
 */
@Repository
public class SystemConfigDaoImpl extends MybatisTemplate implements
		ISystemConfigDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<SystemConfig> getSystemConfigs() throws Exception {
		return (List<SystemConfig>) this
				.selectList("SystemConfigXML.getSystemConfigs");
	}

	@Override
	public void insertSystemConfig(SystemConfig config) throws Exception {
		this.insert("SystemConfigXML.insertSystemConfig", config);
	}

	@Override
	public void updateSystemConfig(SystemConfig config) throws Exception {
		this.update("SystemConfigXML.updateSystemConfig", config);
	}

	@Override
	public void delSystemConfig(String id) throws Exception {
		this.delete("SystemConfigXML.delSystemConfig", id);
	}

	@Override
	public SystemConfig getSystemConfigById(String id) throws Exception {
		return (SystemConfig) this.selectOne(
				"SystemConfigXML.getSystemConfigById", id);
	}

	@Override
	public PagerModel<SystemConfig> getPagerModel(SystemConfig config,
			Query query) throws Exception {
		return this.getPagerModelByQuery(config, query,
				"SystemConfigXML.getPagerModel");
	}

	@Override
	public List<SystemConfig> getSystemConfigsBySn(String configSn)
			throws Exception {
		return (List<SystemConfig>) this
				.selectList("SystemConfigXML.getSystemConfigsBySn",configSn);

	}

	@Override
	public List<SystemConfig> getSystemConfigsByKey(String configKey) throws Exception {
		return (List<SystemConfig>)this.selectList("SystemConfigXML.getSystemConfigsByKey",configKey);
			
	}
}
