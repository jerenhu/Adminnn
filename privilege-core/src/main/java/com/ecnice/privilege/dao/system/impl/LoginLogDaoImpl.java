package com.ecnice.privilege.dao.system.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ecnice.privilege.common.PagerModel;
import com.ecnice.privilege.common.Query;
import com.ecnice.privilege.dao.MybatisTemplate;
import com.ecnice.privilege.dao.system.ILoginLogDao;
import com.ecnice.privilege.model.system.LoginLog;
import com.ecnice.privilege.vo.privilege.LoginLogVo;
/**
 * 
 *@Title:
 *@Description:日志dao实现类
 *@Author:TaoXiang.Wen
 *@Since:2014年4月8日
 *@Version:1.1.0
浙江蘑菇加电子商务有限公司 2014 ~ 2015 版权所有
 */
@Repository
public class LoginLogDaoImpl extends MybatisTemplate implements ILoginLogDao {

	@Override
	public void insertLoginLog(LoginLog loginLog) throws Exception {
		this.insert("LoginLogXML.insertLoginLog", loginLog);
	}

	@Override
	public void updateLoginLog(LoginLog loginLog) throws Exception {
		this.update("LoginLogXML.updateLoginLog", loginLog);
	}

	@Override
	public void delLoginLog(int id) throws Exception {
		this.delete("LoginLogXML.delLoginLog", id);
	}

	@Override
	public LoginLog getLoginLogById(int id) throws Exception {
		return (LoginLog) this.selectOne("LoginLogXML.getLoginLogById", id);
	}

	@Override
	public PagerModel<LoginLog> getPagerModel(LoginLogVo loginLogVo, Query query) throws Exception {
		return this.getPagerModelByQuery(loginLogVo, query, "LoginLogXML.getPagerModel");
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LoginLog> getAll(LoginLogVo loginLogVo) throws Exception {
		return (List<LoginLog>) this.selectList("LoginLogXML.getAll", loginLogVo);
	}
}
