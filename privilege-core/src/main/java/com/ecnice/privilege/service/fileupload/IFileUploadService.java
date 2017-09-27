package com.ecnice.privilege.service.fileupload;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;

/**
 * 文件上传
 * @author Martin.Wang
 *
 */
public interface IFileUploadService {
	
	/**
	 * 上传文件到指定的文件夹下
	 * @param model 模块文件夹名
	 * @param file 要上传的文件
	 * @param request 
	 * @return 
	 * @throws Exception 
	 */
	public String fileUpload(String model,MultipartFile file,DefaultMultipartHttpServletRequest request)throws Exception;
	
	/**
	 * 修改上传文件
	 * @param model 模块文件夹名
	 * @param file 要上传的文件
	 * @param request 
	 * @param oldImageUrl 原来的文件路径
	 * @return
	 * @throws Exception
	 */
	public String updateFileUpload(String model,MultipartFile file,DefaultMultipartHttpServletRequest request
			,String oldImageUrl)throws Exception;
	
	
}
