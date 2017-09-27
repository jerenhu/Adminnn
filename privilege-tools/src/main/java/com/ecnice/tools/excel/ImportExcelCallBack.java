package com.ecnice.tools.excel;

public interface ImportExcelCallBack {
	/**
	 * 
	 * @param param 
	 * 		参数：row 代表第几行，从1开始
	 * 		参数：col 代表第几列，从0开始
	 * 		参数：cellStr代表单元格的内容
	 * 		参数：obj是用来接收该单元格内容的对象
	 * 		参数：list是读取excel内容的存储list
	 * 
	 * @param customParams 怎样传入，怎样传出，程序不做处理
	 * @throws Exception
	 * @Description:
	 * @author v-wuqiang 2016 下午3:56:36
	 */
	public <T>void validCallback(ImportParameter<T> param,Object ...customParams) throws Exception;
	/**
	 * 
	  * @param param 
	 * 		参数：row 代表第几行，从1开始
	 * 		参数：col 代表第几列，从0开始
	 * 		参数：cellStr代表单元格的内容
	 * 		参数：obj是用来接收该单元格内容的对象
	 * 		参数：list是读取excel内容的存储list
	 * 
	 * @param customParams 怎样传入，怎样传出，程序不做处理
	 * @throws Exception
	 * @Description:
	 * @author v-wuqiang 2016 下午3:56:45
	 */
	public <T>void successCallBack(ImportParameter<T> param,Object ...customParams) throws Exception;
}
