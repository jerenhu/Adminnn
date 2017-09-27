package com.ecnice.tools.excel;

import java.util.Date;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * 
 * @Title:
 * @Description:
 * @Author:hujianhua
 * @Since:2016年10月11日 下午12:02:23
 * @Version:1.1.0
 * @Copyright:Copyright (c) 浙江蘑菇加电子商务有限公司 2015 ~ 2016 版权所有
 */
public interface IExportExcelCallBackService {
	
	/**
	 * 生成特殊的单元格（合并）
	 * @param cellIndex
	 * @param param
	 * @param customParams
	 * @throws Exception
	 * @Description:
	 */
	public <T> void genSpacialCellCallBack(HSSFWorkbook workbook, int sheetIndex, HSSFSheet sheet, Date endTime) throws Exception;
	
	/**
	 * 获取需要装载的Excel数据
	 * @param cellIndex
	 * @param param
	 * @param customParams
	 * @throws Exception
	 * @Description:
	 */
	public <T>String getExcelCellDataCallBack(int cellIndex, T param, Object ...objectOnSuccess) throws Exception;
	
	/**
	 * 导出成功的回调方法
	 * @param param
	 * @param customParams
	 * @return
	 * @throws Exception
	 * @Description:
	 */
	public <T> void successCallBack(T paramOnSuccess, Object ...customParamsOnSuccess) throws Exception;
	
}

	