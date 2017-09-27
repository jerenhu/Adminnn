package com.ecnice.privilege.service.fileupload.impl;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;

import com.ecnice.privilege.service.fileupload.IFileUploadService;

@Service
public class FileUploadServiceImpl implements IFileUploadService{

	@Override
	public String fileUpload(String model, MultipartFile file,DefaultMultipartHttpServletRequest request) throws Exception {
		//生成文件的路径
		String now=new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		String oldname=file.getOriginalFilename();
		String fileName=Calendar.getInstance().getTimeInMillis()+oldname.substring(oldname.lastIndexOf("."), oldname.length());
		String basePath=request.getRealPath("/");
		String tim [] =now.split("-");
		String folder=tim[0]+"/"+tim[1]+"/"+tim[2];
		//上传文件
		File fileUpload=new File(basePath+"/attached/"+model+"/"+folder);
		if(!fileUpload.exists()){
			fileUpload.mkdirs();
		}
		basePath=fileUpload.getPath();
		fileUpload=new File(basePath+"/"+fileName);
		if(!fileUpload.exists()){
			fileUpload.createNewFile();
		}
		BufferedOutputStream out=new BufferedOutputStream(new FileOutputStream(fileUpload));
		out.write(file.getBytes());
		out.flush();
		out.close();
		request.getSession().setAttribute("percent", "0%");
		return "attached/"+model+"/"+folder+"/"+fileName;
	}
	
	@Override
	public String updateFileUpload(String model, MultipartFile file,DefaultMultipartHttpServletRequest request
			,String oldImageUrl) throws Exception {
		//生成文件的路径
		String now=new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		String oldname=file.getOriginalFilename();
		String fileName=Calendar.getInstance().getTimeInMillis()+oldname.substring(oldname.lastIndexOf("."), oldname.length());
		String basePath=request.getRealPath("/");
		String tim [] =now.split("-");
		String folder=tim[0]+"/"+tim[1]+"/"+tim[2];
		//删除原来上传的文件
		if(StringUtils.isNotBlank(oldImageUrl)){
			File oldFile=new File(basePath+oldImageUrl);
			if(oldFile.exists()){
				oldFile.delete();
			}
		}
		//上传新文件
		File uploadfile=new File(basePath+"/attached/"+model+"/"+folder);
		if(!uploadfile.exists()){
			uploadfile.mkdirs();
		}
		basePath=uploadfile.getPath();
		uploadfile=new File(basePath+"/"+fileName);
		if(!uploadfile.exists()){
			uploadfile.createNewFile();
		}
		BufferedOutputStream out=new BufferedOutputStream(new FileOutputStream(uploadfile));
		out.write(file.getBytes());
		out.flush();
		out.close();
		request.getSession().setAttribute("percent", "0%");
		return "attached/"+model+"/"+folder+"/"+fileName;
	}
	
}
