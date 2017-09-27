package com.ecnice.tools.excel;

import org.springframework.web.multipart.MultipartFile;

public interface IImportCommonService {
	/**
	 * 
	 * @param file 导入的excel文件
	 * @param param 参数里面的obj属性必填，用来映射excel文件内容的实体类
	 * @param callback 回调接口
	 * @param customParams 用户自定义对象，用于程序扩展，怎样传入，回调的时候怎样传回，不做处理
	 * @throws Exception
	 * @Description:
	 * @author v-wuqiang 2016 下午3:52:34
	 */
	public <T>void importExcel(MultipartFile file, ImportParameter<T> param, ImportExcelCallBack callback,Object ...customParams) throws Exception;
}
