package com.ecnice.privilege.dao.privilege.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Repository;

import com.ecnice.privilege.common.PagerModel;
import com.ecnice.privilege.common.Query;
import com.ecnice.privilege.dao.MybatisTemplate;
import com.ecnice.privilege.dao.privilege.IICSystemDao;
import com.ecnice.privilege.model.privilege.ICSystem;

@Repository
public class ICSystemDaoImpl extends MybatisTemplate implements IICSystemDao {
	@Override
	public List<ICSystem> getICSystemsBySns(Set<String> systemSns) throws Exception {
		List<String> params = new ArrayList<String>();
		params.addAll(systemSns);
		return (List<ICSystem>) this.selectList("IcSystemXML.getICSystemsBySns",params);
	}

	/**
	 * 删除系统
	 */
	@Override
	public void deleteIcSystem(String id) throws Exception {
		this.delete("IcSystemXML.deleteIcSystem", id);
	}

	/**
	 * 获取所有系统列表
	 */
	@Override
	public List<ICSystem> getAllIcSystem(ICSystem icSystem) throws Exception {
		return (List<ICSystem>) this.selectList("IcSystemXML.getAllIcSystem",
				icSystem);
	}

	/**
	 * 根据id获取系统对象
	 */
	@Override
	public ICSystem getICSystemById(String id) throws Exception {
		return (ICSystem) this.selectOne("IcSystemXML.getICSystemById", id);
	}

	/**
	 * 分页获取系统列表
	 */
	@Override
	public PagerModel<ICSystem> getPagerIcSystem(ICSystem icSystem, Query query)
			throws Exception {
		return this.getPagerModelByQuery(icSystem, query,
				"IcSystemXML.getPagerIcSystem");
	}

	/**
	 * 添加系统
	 */
	@Override
	public void insertIcSystem(ICSystem icSystem) throws Exception {
		icSystem.setCreateTime(new Date());
		icSystem.setUpdateTime(new Date());
		this.insert("IcSystemXML.insertIcSystem", icSystem);
	}

	/**
	 * 更新系统
	 */
	@Override
	public void updateIcSystem(ICSystem icSystem) throws Exception {
		this.update("IcSystemXML.updateIcSystem", icSystem);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ICSystem> getICSystemsByUserId(String userId) {
		return (List<ICSystem>) this.selectList("IcSystemXML.getICSystemsByUserId", userId);
	}

	@Override
	public ICSystem getICSystemBySn(String sn) {
		return (ICSystem) this.selectOne("IcSystemXML.getICSystemBySn", sn);
	}
}
