package com.ecnice.privilege.dao.privilege.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ecnice.privilege.common.PagerModel;
import com.ecnice.privilege.common.Query;
import com.ecnice.privilege.dao.MybatisTemplate;
import com.ecnice.privilege.dao.privilege.IModuleDao;
import com.ecnice.privilege.model.privilege.Module;

@Repository
public class ModuleDaoImpl extends MybatisTemplate implements IModuleDao {
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Module> getModulesBySystemSn(String systemSn) throws Exception {
		return (List<Module>) selectList("ModuleXML.getModulesBySystemSn",
				systemSn);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Module> getModulesByIds(String moduleIds,String systemId) throws Exception {
		Map<String,String> params = new HashMap<String, String>();
		params.put("moduleIds", moduleIds);
		params.put("systemId", systemId);
		return (List<Module>) selectList("ModuleXML.getModulesByIds",
				params);
	}

	@Override
	public boolean checkChildren(String pid) {
		int count = (Integer) selectOne("ModuleXML.checkChildren", pid);
		return count > 0;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Module> getModulesBySystemId(String systemId) throws Exception {
		return (List<Module>) selectList("ModuleXML.getModulesBySystemId",
				systemId);
	}

	/**
	 * 删除模块
	 */
	@Override
	public void deleteModule(String id) throws Exception {
		this.delete("ModuleXML.deleteModule", id);
	}

	/**
	 * 获取所有模块列表
	 */
	@Override
	public List<Module> getAllModule(Module module) throws Exception {
		return (List<Module>) this.selectList("ModuleXML.getAllModule", module);
	}

	/**
	 * 根据id获取模块对象
	 */
	@Override
	public Module getModuleById(String id) throws Exception {
		return (Module) this.selectOne("ModuleXML.getModuleById", id);
	}

	/**
	 * 分页获取模块列表
	 */
	@Override
	public PagerModel<Module> getPagerModule(Module module, Query query)
			throws Exception {
		return this.getPagerModelByQuery(module, query,
				"ModuleXML.getPagerModule");
	}

	/**
	 * 添加模块
	 */
	@Override
	public void insertModule(Module module) throws Exception {
		Date currDate = new Date();
		module.setCreateTime(currDate);
		module.setUpdateTime(currDate);
		this.insert("ModuleXML.insertModule", module);
	}

	/**
	 * 更新模块
	 */
	@Override
	public void updateModule(Module module) throws Exception {
		this.update("ModuleXML.updateModule", module);
	}
}
