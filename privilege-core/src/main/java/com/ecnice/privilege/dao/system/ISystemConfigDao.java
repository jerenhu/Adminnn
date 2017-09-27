package com.ecnice.privilege.dao.system;

import java.util.List;

import com.ecnice.privilege.common.PagerModel;
import com.ecnice.privilege.common.Query;
import com.ecnice.privilege.model.system.SystemConfig;

/**
 * @Title:
 * @Description:系统配置dao接口
 * @Author:Bruce.Liu
 * @Since:2014年4月1日
 * @Version:1.1.0
浙江蘑菇加电子商务有限公司 2014 ~ 2015 版权所有
 */
public interface ISystemConfigDao {

	/**
	 * @param configSn
	 * @return
	 * @throws Exception
	 * @Description:通过标示得到系统配置列表
	 */
	public List<SystemConfig> getSystemConfigsBySn(String configSn)
			throws Exception;
	
	/**
	 * @param configKey
	 * @return
	 * @throws Exception
	 * @Description:通过配置key得到系统配置列表
	 */
	public List<SystemConfig> getSystemConfigsByKey(String configKey)
			throws Exception;

	/**
	 * @param config
	 * @throws Exception
	 * @Description:添加系统配置
	 */
	public void insertSystemConfig(SystemConfig config) throws Exception;

	/**
	 * @param config
	 * @throws Exception
	 * @Description:更新系统配置
	 */
	public void updateSystemConfig(SystemConfig config) throws Exception;

	/**
	 * @param id
	 * @throws Exception
	 * @Description:删除系统配置
	 */
	public void delSystemConfig(String id) throws Exception;

	/**
	 * @param id
	 * @return
	 * @throws Exception
	 * @Description:通过id得到系统配置
	 */
	public SystemConfig getSystemConfigById(String id) throws Exception;

	/**
	 * @param config
	 * @param query
	 * @return
	 * @throws Exception
	 * @Description:分页查询系统配置
	 */
	public PagerModel<SystemConfig> getPagerModel(SystemConfig config,
			Query query) throws Exception;

	/**
	 * @return
	 * @throws Exception
	 * @Description:得到所有的系统配置
	 */
	public List<SystemConfig> getSystemConfigs() throws Exception;
}
