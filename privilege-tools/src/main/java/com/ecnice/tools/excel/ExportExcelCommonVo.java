package com.ecnice.tools.excel;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 导出到Excel表的ExcelVo类
 * @Title:
 * @Description:
 * @Author:hujianhua
 * @Since:2016年5月20日 下午4:17:13
 * @Version:1.1.0
 * @Copyright:Copyright (c) 浙江蘑菇加电子商务有限公司 2015 ~ 2016 版权所有
 */
public class ExportExcelCommonVo<T> implements Serializable{
    
	private static final long serialVersionUID = 1L;
	
	/**
	 * 工作簿名称、数据的Map类
	 */
	private Map<String, List<T>> mapExportData;
	
	/**
	 * 文件名称
	 */
	private String excelFileName;
	
	/**
	 * 标题行的名称
	 */
	private List<String> cellTitles;
	
	/**
	 * 标题行的行号(第几行开始)
	 */
	private int titleRowIndex;
	
	/**
	 * 截止查询日期
	 */
	private Date endTime;

	public Map<String, List<T>> getMapExportData() {
		return mapExportData;
	}

	public void setMapExportData(Map<String, List<T>> mapExportData) {
		this.mapExportData = mapExportData;
	}

	public String getExcelFileName() {
		return excelFileName;
	}

	public void setExcelFileName(String excelFileName) {
		this.excelFileName = excelFileName;
	}

	public List<String> getCellTitles() {
		return cellTitles;
	}

	public void setCellTitles(List<String> cellTitles) {
		this.cellTitles = cellTitles;
	}

	public int getTitleRowIndex() {
		return titleRowIndex;
	}

	public void setTitleRowIndex(int titleRowIndex) {
		this.titleRowIndex = titleRowIndex;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	@Override
	public String toString() {
		return "ExportExcelCommonVo [mapExportData=" + mapExportData + ", excelFileName=" + excelFileName
				+ ", cellTitles=" + cellTitles + ", titleRowIndex=" + titleRowIndex + ", endTime=" + endTime + "]";
	}
	
}
