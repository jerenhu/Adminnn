package com.ecnice.tools.excel.impl;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.springframework.stereotype.Service;

import com.ecnice.tools.excel.ExportExcelCommonVo;
import com.ecnice.tools.excel.IExportExcelCallBackService;
import com.ecnice.tools.excel.IExportExcelService;

/**
 * 公共方法类
 * @Title:
 * @Description:
 * @Author:wangzhaoliao
 * @Since:2016年3月2日 下午3:7:22
 * @Version:1.1.0
 * @Copyright:Copyright (c) 浙江蘑菇加电子商务有限公司 2015 ~ 2016 版权所有
 */
@Service
public class ExportExcelServiceImpl implements IExportExcelService {
	private static final Logger logger = Logger.getLogger(ExportExcelServiceImpl.class);
	
	@Override
	public <T>void exportExcel(IExportExcelCallBackService exportExcelCallBackService, ExportExcelCommonVo exportExcelCommonVo, HttpServletResponse response, T paramOnSuccess, Object ...objectT) throws Exception {
		if(null==exportExcelCommonVo){
			throw new Exception("exportExcelCommonVo不能为空");
		}
		if(StringUtils.isEmpty(exportExcelCommonVo.getExcelFileName())){
			throw new Exception("excelFileName不能为空");
		}
		if(MapUtils.isEmpty(exportExcelCommonVo.getMapExportData())){
			throw new Exception("mapExportData不能为空");
		}
		if(CollectionUtils.isEmpty(exportExcelCommonVo.getCellTitles())){
			throw new Exception("cellTitles不能为空");
		}
		if(null==response){
			throw new Exception("response不能为空");
		}
		
		HSSFWorkbook wb = new HSSFWorkbook();
		
		int sheetIndex = 0;	//工作簿序号
		for (Map.Entry<String, List<T>> entry : ((Map<String, List<T>>)exportExcelCommonVo.getMapExportData()).entrySet()) {
			HSSFSheet sheet = wb.createSheet(entry.getKey());
			
			// 生成标题行
			if(CollectionUtils.isNotEmpty(exportExcelCommonVo.getCellTitles())){
				Row row = sheet.createRow(exportExcelCommonVo.getTitleRowIndex());
				for(int i = 0; i < exportExcelCommonVo.getCellTitles().size(); i++){
					row.createCell(i).setCellValue((String)exportExcelCommonVo.getCellTitles().get(i));
				}
			}
			// 从标题行的下一行开始生成数据
			for (int i = 0; i < entry.getValue().size(); i++) {
				T cache = entry.getValue().get(i);
				Row row = sheet.createRow(i + 1 + exportExcelCommonVo.getTitleRowIndex());
				for(int j = 0; j < exportExcelCommonVo.getCellTitles().size(); j++){
					row.createCell(j).setCellValue(exportExcelCallBackService.getExcelCellDataCallBack(j, cache));;
				}
			}
			//生成自定义的单元格
			exportExcelCallBackService.genSpacialCellCallBack(wb, sheetIndex, sheet, exportExcelCommonVo.getEndTime());
			sheetIndex++;
		}
		
		//输出到页面
		response.setHeader("Content-disposition", "attachment; filename=" + exportExcelCommonVo.getExcelFileName());
		ServletOutputStream outputStream = null;
		try {
			outputStream = response.getOutputStream();
			wb.write(outputStream);
		} catch (IOException e) {
			logger.warn("导出模板出错" + e);
			throw e;
		} finally {
			try {
				outputStream.flush();
				exportExcelCallBackService.successCallBack(paramOnSuccess);
			} catch (IOException e) {
				logger.warn("flush stream fails" + e);
				throw e;
			}
		}
	}
	
}

	