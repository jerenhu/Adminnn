package com.ecnice.privilege.service.system;

import java.util.List;

import com.ecnice.privilege.common.PagerModel;
import com.ecnice.privilege.common.Query;
import com.ecnice.privilege.model.system.Dictionary;

public interface IDictionaryService {
	/**
	 * @param Dictionary
	 * @throws Exception
	 * @Description:添加数据字典
	 */
	public void insertDictionary(Dictionary dictionary) throws Exception;

	/**
	 * @param Dictionary
	 * @throws Exception
	 * @Description:更新数据字典
	 */
	public void updateDictionary(Dictionary dictionary) throws Exception;

	/**
	 * @param id
	 * @throws Exception
	 * @Description:删除数据字典
	 */
	public void delDictionary(String id) throws Exception;

	/**
	 * 
	 * @param ids
	 * @throws Exception
	 * @Description:批量删除数据字典数据
	 */
	public void delDictionarys(String [] ids) throws Exception; 
	/**
	 * @param id
	 * @return
	 * @throws Exception
	 * @Description:根据id查询数据字典对象
	 */
	public Dictionary getDictionaryById(String id) throws Exception;

	/**
	 * @param Dictionary
	 * @param query
	 * @return
	 * @throws Exception
	 * @Description: 分页查询数据字典列表
	 */
	public PagerModel<Dictionary> getPagerModel(Dictionary dictionary,
			Query query) throws Exception;

	/**
	 * 
	 * @param Dictionary
	 * @return
	 * @throws Exception
	 * @Description:获取所有数据字典列表
	 */
	public List<Dictionary> getAll(Dictionary dictionary) throws Exception;
}
