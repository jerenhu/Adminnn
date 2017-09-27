package com.ecnice.tools.common;

import java.util.Calendar;

/**
 * 文件路径类
 * @Title:
 * @Description:
 * @Author:sunxiao
 * @Since:2016年2月22日 上午9:32:26
 * @Version:1.1.0
 * @Copyright:Copyright (c) 浙江蘑菇加电子商务有限公司 2015 ~ 2016 版权所有
 */
public class ComFilePart {
	
	/**
	 * 取得年月日
	 * @param module
	 * @return module+年/月/日/
	 * @Description:
	 * @author wentaoxiang 2016年7月1日 下午3:43:48
	 */
	public static String getFilePath(String module){
		StringBuilder path = new StringBuilder(module);
		Calendar now = Calendar.getInstance();
		int year = now.get(Calendar.YEAR);
		int month = now.get(Calendar.MONTH) + 1;
		int date = now.get(Calendar.DATE);
		path.append(year);
		path.append("/");
		path.append(month);
		path.append("/");
		path.append(date);
		path.append("/");
		return path.toString();
	}
	
	public static void main(String[] args) {
		System.err.println(getFilePath("/prdct/imgs/"));
	}
}
