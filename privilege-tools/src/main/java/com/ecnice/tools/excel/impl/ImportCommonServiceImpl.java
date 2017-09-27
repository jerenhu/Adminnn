package com.ecnice.tools.excel.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ecnice.tools.excel.IImportCommonService;
import com.ecnice.tools.excel.ImportExcelCallBack;
import com.ecnice.tools.excel.ImportParameter;

@Service
public class ImportCommonServiceImpl implements IImportCommonService{
	
	@Override
	public <T>void importExcel(MultipartFile file,ImportParameter<T> param,ImportExcelCallBack callback,Object ...customParams) throws Exception {
		if(file == null){
			throw new Exception("file参数不能为空");
		}
		if(param == null){
			throw new Exception("param参数不能为空");
		}
		if(param.getObj() == null){
			throw new Exception("param.getObj()为空");
		}
		InputStream inputStream = file.getInputStream();
		// 1、得到所有excel的数据
		List<T> excelInfos = this.readReport(inputStream,param,callback,customParams);
		param.setList(excelInfos);
		if(callback != null){
			callback.successCallBack(param,customParams);
		}
	}
	private <T>List<T> readReport(InputStream inp,ImportParameter<T> param,ImportExcelCallBack callback,Object ...customParams) throws Exception {
		List<T> listTs = new ArrayList<T>();  
        try {  
            String cellStr = null;  
            Workbook wb = WorkbookFactory.create(inp);  
            Sheet sheet = wb.getSheetAt(0);// 取得第一个sheets 
            if(sheet.getLastRowNum() < 1){
            	throw new Exception("该excel文件没有可以导入的数据");
            }
            ImportParameter<T> tempParam = new ImportParameter<T>();
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {//从第二行开始读取数据
            	T addT = (T) param.getObj().getClass().newInstance();
            	Row row = sheet.getRow(i);// 获取行(row)对象  
            	if (row == null) {
                    continue;
            	}
                for (int j = 0; j < row.getLastCellNum(); j++) {
					try {
						Cell cell = row.getCell(j); // 获得单元格(cell)对象
						if (cell != null) {//防止Excel中某些栏位为空直接报空指针异常
							cellStr = ConvertCellStr(cell, cellStr);// 转换接收的单元格
						} else {
							cellStr = "";
						}
						//去空格
						if (StringUtils.isNotBlank(cellStr)) {
							cellStr = cellStr.trim();
						}
						tempParam.setRow(i);
						tempParam.setCol(j);
						tempParam.setCellStr(cellStr);
						tempParam.setObj(addT);
						this.addT(tempParam,callback,customParams);//将单元格的数据添加至一个对象
						cellStr = null;
					} catch (Exception e) {
						throw new Exception("导入报表时第" + (i + 1) + "行第" + (j + 1) + "列报错"+e.getMessage());
					}
                }
                listTs.add(tempParam.getObj());
            }
        }
        finally {
            if (inp != null) {
                try {
                    inp.close();  
                } catch (IOException e) {
                    e.printStackTrace();  
                }
            }
        }
        return listTs;
	}
	/**
	 * 
	 * @param cell
	 * @param cellStr
	 * @return
	 * @Description:读取excel转换类型
	 * @author v-wuqiang 2016 上午9:26:27
	 */
    private String ConvertCellStr(Cell cell, String cellStr){
        switch (cell.getCellType()){
	        case Cell.CELL_TYPE_STRING:
	            cellStr = cell.getStringCellValue().toString();// 读取String   
	            break;
	        case Cell.CELL_TYPE_BOOLEAN:  
	            cellStr = String.valueOf(cell.getBooleanCellValue());//得到Boolean对象的方法
	            break;
	        case Cell.CELL_TYPE_NUMERIC:  
	            if (DateUtil.isCellDateFormatted(cell)) {// 先看是否是日期格式 
	                cellStr = cell.getDateCellValue().toString();//读取日期格式
	            } else {
	                cellStr = String.valueOf(cell.getNumericCellValue());//读取数字
	            }
	            break;
	        case Cell.CELL_TYPE_FORMULA:
	            cellStr = cell.getCellFormula().toString();//读取公式 
	            break;
        }
        return cellStr; 
    }
    /**
     * 
     * @param j
     * @param cellStr
     * @param addProductInfo
     * @throws Exception
     * @Description:对各种数据进行验证
     * @author v-wuqiang 2016 上午9:26:43
     */
    private <T>void addT(ImportParameter<T> param,ImportExcelCallBack callback,Object ...customParams) throws Exception{
    	if(callback != null){
    		callback.validCallback(param,customParams);
    	}
	}
}
