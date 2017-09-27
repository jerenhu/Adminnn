package com.ecnice.tools.excel;

import javax.servlet.http.HttpServletResponse;

/**
 * 
 * @Title:
 * @Description:
 * @Author:hujianhua
 * @Since:2016年10月11日 下午12:02:23
 * @Version:1.1.0
 * @Copyright:Copyright (c) 浙江蘑菇加电子商务有限公司 2015 ~ 2016 版权所有
 */
public interface IExportExcelService {
	
	/**
	 * 导出Excel
	 * @param exportTitles
	 * @param exportData
	 * @param object
	 * @throws Exception
	 * @Description:
	 */
	public <T>void exportExcel(IExportExcelCallBackService exportExcelCallBackService, ExportExcelCommonVo exportExcelCommonVo, HttpServletResponse response, T paramOnSuccess, Object ...object) throws Exception;
	
}

	