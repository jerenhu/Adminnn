package com.ecnice.privilege.web.controller.fileupload;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 获取上传进度百分比
 *
 */
@Controller
@RequestMapping("/managment/process")
public class FileUploadProcessController {
	@ResponseBody
	@RequestMapping("/getPercent")
	public String getPercent(HttpServletRequest request){
		return request.getSession().getAttribute("percent")!=null?request.getSession().getAttribute("percent").toString():null;
	}
}
