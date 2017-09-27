package com.ecnice.privilege.service.system.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Service;

import com.ecnice.privilege.common.PagerModel;
import com.ecnice.privilege.common.Query;
import com.ecnice.privilege.dao.system.ILoginLogDao;
import com.ecnice.privilege.model.system.LoginLog;
import com.ecnice.privilege.service.system.ILoginLogService;
import com.ecnice.privilege.vo.privilege.LoginLogVo;

/**
 * 
 * @Title:
 * @Description:日志service实现类
 * @Author:TaoXiang.Wen
 * @Since:2014年4月8日
 * @Version:1.1.0
浙江蘑菇加电子商务有限公司 2014 ~ 2015 版权所有
 */
@Service
public class LoginLogServiceImpl implements ILoginLogService {
	private static final Logger logger = Logger.getLogger(LoginLogServiceImpl.class);

	@Resource
	private ILoginLogDao loginLogDao;

	@Override
	public void insertLoginLog(LoginLog loginLog) throws Exception {
		this.loginLogDao.insertLoginLog(loginLog);
	}

	@Override
	public void updateLoginLog(LoginLog loginLog) throws Exception {
		this.loginLogDao.updateLoginLog(loginLog);
	}

	@Override
	public void delLoginLog(int id) throws Exception {
		this.loginLogDao.delLoginLog(id);
	}

	@Override
	public void delLoginLogs(int[] ids) throws Exception {
		for (int id : ids) {
			this.loginLogDao.delLoginLog(id);
		}
	}

	@Override
	public LoginLog getLoginLogById(int id) throws Exception {
		return this.loginLogDao.getLoginLogById(id);
	}

	@Override
	public PagerModel<LoginLog> getPagerModel(LoginLogVo loginLogVo, Query query) throws Exception {
		return this.loginLogDao.getPagerModel(loginLogVo, query);
	}

	@Override
	public List<LoginLog> getAll(LoginLogVo loginLogVo) throws Exception {
		return this.loginLogDao.getAll(loginLogVo);
	}
	
	@Override
	public String export(LoginLogVo loginLogVo,String realPath) {
		
		/** Excel 07-2003一个工作表最多可有65536行*/
		int maxRow=10000; //每个sheet的大小
		int pageNumber=0; //第几页
		PagerModel<LoginLog> pm=null;
		try {
			//1、分页查询第一页的数据，返回总记录数目
			Query query = new Query();
			query.setPageIndex(pageNumber);
			query.setPageSize(maxRow);
			pm = this.getPagerModel(loginLogVo, query);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		String zipFileName="MOS_Login_"+new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())+".zip"; //压缩包的名称
		
		if (null != pm && CollectionUtils.isNotEmpty(pm.getDatas())) {
			List<LoginLog> loginList = pm.getDatas();
			int total = (int) pm.getTotal(); // 总记录数
			int totalPage = (total - 1 + maxRow) / maxRow; // 总共多少个excel文件
			
			List<File> excelFiles= new ArrayList<File>();
			//2、分页查询数据
			for (int sheetNum = 0; sheetNum < totalPage; sheetNum++) {
				if (0 != sheetNum) {
					try {
						Query query = new Query();
						query.setPageIndex(sheetNum);
						query.setPageSize(maxRow);
						pm=this.getPagerModel(loginLogVo, query);
						if (null != pm && CollectionUtils.isNotEmpty(pm.getRows())) {
							loginList = pm.getRows();
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				
				//3、将每一页的数据生成一个对应的Excel文件
				excelFiles.add(this.setLoginLogs(loginList, sheetNum, realPath, "login"+(sheetNum+1)+".xls"));
				loginList=null;
				pm=null;
			}
			
			//4、将所有生成的excel文件打包成zip，最后删除所有生成的excel文件
			if(CollectionUtils.isNotEmpty(excelFiles)){
				this.fileToZip(realPath, zipFileName, excelFiles);
			}
		}
		return zipFileName;
	}
	
	
	private File setLoginLogs(List<LoginLog> loginList, int sheetNum, String realPath, String fileName) {
		Workbook wb = new HSSFWorkbook();
		Sheet sheet = wb.createSheet("MOS登录日志" + (sheetNum + 1));
		
		// 生成标题
		Row row = sheet.createRow(0);
		Cell username = row.createCell(0);
		Cell realName = row.createCell(1);
		Cell loginTime = row.createCell(2);
		Cell loginIP = row.createCell(3);
		Cell opt = row.createCell(4);
		
		/*CellStyle cellStyle = wb.createCellStyle(); 
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 指定单元格居中对齐 
		cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 指定单元格垂直居中对齐 
		cellStyle.setWrapText(true);// 指定单元格自动换行 
		// 设置单元格字体 
		Font font = wb.createFont(); 
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); 
		font.setFontName("宋体"); 
		font.setFontHeight((short) 300); 
		cellStyle.setFont(font); 
		username.setCellStyle(cellStyle); */
		
		CellStyle style = wb.createCellStyle();
		style = wb.createCellStyle();
		style.setFillForegroundColor(IndexedColors.GOLD.getIndex()); //设置单元格背景颜色
		style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		
		
		username.setCellValue("用户名");
		username.setCellStyle(style);
		realName.setCellValue("真实姓名");
		realName.setCellStyle(style);
		loginTime.setCellValue("登录时间");
		loginTime.setCellStyle(style);
		loginIP.setCellValue("登录IP");
		loginIP.setCellStyle(style);
		opt.setCellValue("操作行为");
		opt.setCellStyle(style);

		if (CollectionUtils.isNotEmpty(loginList)) {
			for (int i = 0; i < loginList.size(); ++i) {
				LoginLog loginLog = loginList.get(i);
				Row row2 = sheet.createRow(i + 1);
				Cell cell0 = row2.createCell(0);
				Cell cell1 = row2.createCell(1);
				Cell cell2 = row2.createCell(2);
				Cell cell3 = row2.createCell(3);
				Cell cell4 = row2.createCell(4);

				cell0.setCellValue(loginLog.getOperationUsername());
				cell1.setCellValue(loginLog.getOperationPerson());
				cell2.setCellValue(loginLog.getOperationTimeStr());
				cell3.setCellValue(loginLog.getIp());
				cell4.setCellValue(loginLog.getOperationContent());
			}
		}
		
		/**设置列宽:在数据设置完后再设置*/
		sheet.autoSizeColumn((short)0); 
		sheet.autoSizeColumn((short)1); 
		sheet.autoSizeColumn((short)2); 
		sheet.autoSizeColumn((short)3); 
		sheet.autoSizeColumn((short)4); 
		
		String fileNamePath = realPath + fileName;
		FileOutputStream out = null;
		File file = null;
		try {
			file = new File(fileNamePath);
			file.createNewFile();
			out = new FileOutputStream(file);
			wb.write(out);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (null != out) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			wb = null;
			out = null;
		}
		return file;
	}
	
	/**
	 * 压缩文件成zip包
	 * @param realPath 项目真实路径
	 * @param zipName zip包的名称
	 * @param files 需要打包到zip中的Files
	 */
	public void fileToZip(String realPath,String zipName,List<File> files ) {    
        byte[] buffer = new byte[1024];    
        String strZipPath = realPath  + zipName;    
		try {
			ZipOutputStream out = new ZipOutputStream(new FileOutputStream(strZipPath));

			if (CollectionUtils.isNotEmpty(files)) {
				// 1、读取临时的excel文件
				for (File file : files) {
					if (null != file && file.exists()) {
						FileInputStream fis = new FileInputStream(file);
						out.putNextEntry(new ZipEntry(file.getName()));
						int len;
						// 2、 读入需要下载的文件的内容，打包到zip文件
						while ((len = fis.read(buffer)) > 0) {
							out.write(buffer, 0, len);
						}
						out.closeEntry();
						fis.close();

						// 3、删除临时的excel文件
						file.delete();
						System.err.println(file.exists());
					}
				}
				out.close();
			}
		} catch (Exception e) {   
			logger.error("打包失败：LoginLogServiceImpl-fileToZip：",e);
        	e.printStackTrace();
        }    
    } 
}
