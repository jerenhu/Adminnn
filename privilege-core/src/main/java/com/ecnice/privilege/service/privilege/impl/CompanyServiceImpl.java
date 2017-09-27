package com.ecnice.privilege.service.privilege.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.ecnice.privilege.common.PagerModel;
import com.ecnice.privilege.common.Query;
import com.ecnice.privilege.dao.privilege.ICompanyDao;
import com.ecnice.privilege.model.privilege.Company;
import com.ecnice.privilege.service.privilege.ICompanyService;
import com.ecnice.privilege.utils.StringTools;




/**
 * 公司Service实现
 * @author wentaoxiang
 * @date 2016-11-13 15:16:07
 */
@Service
public class CompanyServiceImpl implements ICompanyService {
	private static Logger logger = Logger.getLogger(CompanyServiceImpl.class);

	@Resource
	private ICompanyDao companyDao;

	@Override
	public Company getCompanyById(String id) throws Exception {
		return StringUtils.isNotBlank(id) ? this.companyDao.getCompanyById(id.trim()) : null;
	}

	@Override
	public List<Company> getCompanyByIds(String ids) throws Exception {
		ids = StringTools.converString(ids);
		return StringUtils.isNotBlank(ids) ? this.companyDao.getCompanyByIds(ids) : null;
	}
	
	@Override
	public List<Company> getCompanyByIdsList(List<String> ids) throws Exception {
		return CollectionUtils.isNotEmpty(ids) ? this.companyDao.getCompanyByIdsList(ids) : null;
	}
	
	@Override
	public List<Company> getCompanyByCodesList(List<String> codes) throws Exception {
		return CollectionUtils.isNotEmpty(codes) ? this.companyDao.getCompanyByCodesList(codes) : null;
	}

	@Override
	public List<Company> getAll(Company company) throws Exception {
		return null != company ? this.companyDao.getAll(company) : null;
	}
	
	@Override
	public List<Company> getCompanyByUserNameAndRoleSn(Company company) throws Exception{
		return null != company ? this.companyDao.getCompanyByUserNameAndRoleSn(company) : null;
	}

	@Override
	public PagerModel<Company> getPagerModelByQuery(Company company, Query query)
			throws Exception {
		return (null != company && null != query) ? this.companyDao.getPagerModelByQuery(company, query) : null;
	}
	
	@Override
	public int getByPageCount(Company company)throws Exception {
		return (null != company) ? this.companyDao.getByPageCount(company) : 0;
	}

	@Override
	public void insertCompany(Company company) throws Exception {
		this.companyDao.insertCompany(company);
	}
	
	@Override
	public void insertCompanyBatch(List<Company> companys) throws Exception {
		this.companyDao.insertCompanyBatch(companys);
	}
	
	@Override
	public void delCompanyById(String id) throws Exception {
		if (StringUtils.isNotBlank(id)) {
			this.companyDao.delCompanyById(id.trim());
		}
	}
	
	@Override
	public void delCompanyByIds(String ids) throws Exception {
		ids = StringTools.converString(ids);
		if(StringUtils.isNotBlank(ids)){
			this.companyDao.delCompanyByIds(ids);
		}
	}
	
	@Override
	public void delCompanyByIdsList(List<String> ids) throws Exception {
		if(CollectionUtils.isNotEmpty(ids)){
			this.companyDao.delCompanyByIdsList(ids);
		}
	}

	@Override
	public int updateCompany(Company company) throws Exception {
		return this.companyDao.updateCompany(company);
	}
	
	@Override
	public int updateCompanyByIds(String ids,Company company) throws Exception {
		return this.companyDao.updateCompanyByIds(ids, company);
	}
	
	@Override
	public int updateCompanyByIdsList(List<String> ids,Company company) throws Exception {
		return this.companyDao.updateCompanyByIdsList(ids, company);
	}
	
	@Override
	public int updateCompanyList(List<Company> companys) throws Exception {
		return this.companyDao.updateCompanyList(companys);
	}
	
	@Override
	public void insertCompanyReplaceBatch(List<Company> companys) throws Exception {
		this.companyDao.insertCompanyReplaceBatch(companys);
	}
	
	//------------api------------
	
}

