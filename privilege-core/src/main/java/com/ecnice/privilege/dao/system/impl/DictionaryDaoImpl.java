package com.ecnice.privilege.dao.system.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ecnice.privilege.common.PagerModel;
import com.ecnice.privilege.common.Query;
import com.ecnice.privilege.dao.MybatisTemplate;
import com.ecnice.privilege.dao.system.IDictionaryDao;
import com.ecnice.privilege.model.system.Dictionary;

/**
 * 
 * @Description:数据字典dao
 * @Author:Martin.Wang
 * @Since:2014-4-8
 * @Version:1.1.0
浙江蘑菇加电子商务有限公司 2014 ~ 2015 版权所有
 */
@Repository
public class DictionaryDaoImpl extends MybatisTemplate implements
		IDictionaryDao {

	@Override
	public void insertDictionary(Dictionary dictionary) throws Exception {
		this.insert("DictionaryXML.insertDictionary", dictionary);
	}

	@Override
	public void updateDictionary(Dictionary dictionary) throws Exception {
		this.update("DictionaryXML.updateDictionary", dictionary);
	}

	@Override
	public void delDictionary(String id) throws Exception {
		this.delete("DictionaryXML.delDictionary", id);
	}

	@Override
	public Dictionary getDictionaryById(String id) throws Exception {
		return (Dictionary) this.selectOne("DictionaryXML.getDictionaryById",
				id);
	}

	@Override
	public PagerModel<Dictionary> getPagerModel(Dictionary dictionary,
			Query query) throws Exception {
		return this.getPagerModelByQuery(dictionary, query,
				"DictionaryXML.PagerModel");
	}

	@Override
	public List<Dictionary> getAll(Dictionary dictionary) throws Exception {
		return (List<Dictionary>) this.selectList("DictionaryXML.getAll",
				dictionary);
	}
}
