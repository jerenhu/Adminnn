package com.ecnice.privilege.service.privilege;

import java.util.List;

import com.ecnice.privilege.common.PagerModel;
import com.ecnice.privilege.common.Query;
import com.ecnice.privilege.model.privilege.Company;




/**
 * 公司Service接口
 * @author wentaoxiang
 * @date 2016-11-13 15:16:07
 */
public interface ICompanyService {

	/**
	 * 通过id得到公司Company
	 * @param id
	 * @return 
	 * @throws Exception
	 * @Description:
	 */
	public Company getCompanyById(String id) throws Exception;

	/**
	 * 通过ids批量得到公司Company
	 * @param ids 如："'1','2','3','4'..."
	 * @return 
	 * @throws Exception
	 * @Description:
	 */
	public List<Company> getCompanyByIds(String ids) throws Exception;
	
	/**
	 * 通过ids批量得到公司Company
	 * @param ids 
	 * @return 
	 * @throws Exception
	 * @Description:
	 */
	public List<Company> getCompanyByIdsList(List<String> ids) throws Exception;
	
	/**
	 * 通过codes批量得到公司Company
	 * @param ids 
	 * @return 
	 * @throws Exception
	 * @Description:
	 */
	public List<Company> getCompanyByCodesList(List<String> codes) throws Exception;

	/**
	 * 得到所有公司Company
	 * @param company
	 * @return 
	 * @throws Exception
	 * @Description:
	 */
	public List<Company> getAll(Company company) throws Exception;
	
	/**
	 * 更加人员和角色标识得到所有公司Company
	 * @param company
	 * @return 
	 * @throws Exception
	 * @Description:
	 */
	public List<Company> getCompanyByUserNameAndRoleSn(Company company) throws Exception;

	/**
	 * 分页查询公司Company
	 * @param company
	 * @param query
	 * @return
	 * @throws Exception
	 * @Description:
	 */
	public PagerModel<Company> getPagerModelByQuery(Company company, Query query) throws Exception;

	/**
	 * 查询记录数
	 * @param company
	 * @return
	 * @throws Exception
	 * @Description:
	 */
	public int getByPageCount(Company company)throws Exception ;
	
	/**
	 * 添加公司Company
	 * @param company
	 * @throws Exception
	 * @Description:
	 */
	public void insertCompany(Company company) throws Exception;
	
	/**
	 * 批量添加公司Company
	 * @param companys
	 * @throws Exception
	 * @Description:
	 */
	public void insertCompanyBatch(List<Company> companys) throws Exception;

	/**
	 * 通过id删除公司Company
	 * @param id
	 * @throws Exception
	 * @Description:
	 */
	public void delCompanyById(String id) throws Exception;

	/**
	 * 通过id批量删除公司Company
	 * @param ids 如："'1','2','3','4'..."
	 * @throws Exception
	 * @Description:
	 */
	public void delCompanyByIds(String ids) throws Exception;
	
	/**
	 * 通过id批量删除公司Company
	 * @param ids 
	 * @throws Exception
	 * @Description:
	 */
	public void delCompanyByIdsList(List<String> ids) throws Exception;

	/**
	 * 通过id修改公司Company
	 * @param company
	 * @throws Exception
	 * @Description:
	 */
	public int updateCompany(Company company) throws Exception;
	
	/**
	 * 通过ids批量修改公司Company
	 * @param ids 如："'1','2','3','4'..."
	 * @param company
	 * @throws Exception
	 * @Description:
	 */
	public int updateCompanyByIds(String ids,Company company) throws Exception;
	
	/**
	 * 通过ids批量修改公司Company
	 * @param ids 
	 * @param company
	 * @throws Exception
	 * @Description:
	 */
	public int updateCompanyByIdsList(List<String> ids,Company company) throws Exception;
	
	/**
	 * 覆盖更新：新的插入，老的更新
	 * @param companys
	 * @throws Exception
	 * @Description:
	 * @author xietongjian 2017 下午2:22:34
	 */
	public void insertCompanyReplaceBatch(List<Company> companys) throws Exception;
	
	/**
	 * 通过id批量修改公司Company
	 * @param companys
	 * @throws Exception
	 * @Description:
	 */
	public int updateCompanyList(List<Company> companys) throws Exception;
	
	//------------api------------
}
