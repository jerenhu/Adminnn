package com.ecnice.privilege.service.system.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ecnice.privilege.common.PagerModel;
import com.ecnice.privilege.common.Query;
import com.ecnice.privilege.dao.system.IDictionaryDao;
import com.ecnice.privilege.model.system.Dictionary;
import com.ecnice.privilege.service.system.IDictionaryService;

/**
 * 
 * @Description:数据字典service
 * @Author:Martin.Wang
 * @Since:2014-4-8
 * @Version:1.1.0
浙江蘑菇加电子商务有限公司 2014 ~ 2015 版权所有
 */
@Service
public class DictionaryServiceImpl implements IDictionaryService {
	@Resource
	private IDictionaryDao idcDictionaryDao;

	@Override
	public void insertDictionary(Dictionary dictionary) throws Exception {
		this.idcDictionaryDao.insertDictionary(dictionary);
	}

	@Override
	public void updateDictionary(Dictionary dictionary) throws Exception {
		this.idcDictionaryDao.updateDictionary(dictionary);
	}

	@Override
	public void delDictionary(String id) throws Exception {
		this.idcDictionaryDao.delDictionary(id);
	}
	
	@Override
	public void delDictionarys(String [] ids) throws Exception {
		for(String id :ids){
			this.delDictionary(id);
		}
	}

	@Override
	public Dictionary getDictionaryById(String id) throws Exception {
		return this.idcDictionaryDao.getDictionaryById(id);

	}

	@Override
	public PagerModel<Dictionary> getPagerModel(Dictionary dictionary,
			Query query) throws Exception {
		return this.idcDictionaryDao.getPagerModel(dictionary, query);

	}

	@Override
	public List<Dictionary> getAll(Dictionary dictionary) throws Exception {
		return this.idcDictionaryDao.getAll(dictionary);

	}
}
