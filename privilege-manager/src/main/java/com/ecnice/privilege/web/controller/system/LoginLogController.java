package com.ecnice.privilege.web.controller.system;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ecnice.privilege.common.PagerModel;
import com.ecnice.privilege.common.Permission;
import com.ecnice.privilege.common.Query;
import com.ecnice.privilege.constant.PermissionConatant;
import com.ecnice.privilege.constant.PrivilegeConstant;
import com.ecnice.privilege.model.privilege.User;
import com.ecnice.privilege.model.system.LoginLog;
import com.ecnice.privilege.service.system.ILoginLogService;
import com.ecnice.privilege.utils.JsonUtils;
import com.ecnice.privilege.utils.WebUtils;
import com.ecnice.privilege.vo.SimpleReturnVo;
import com.ecnice.privilege.vo.privilege.LoginLogVo;
import com.ecnice.privilege.web.controller.BaseController;

/**
 * 
 * @Title:
 * @Description:日志控制器
 * @Author:TaoXiang.Wen
 * @Since:2014年4月8日
 * @Version:1.1.0 浙江蘑菇加电子商务有限公司 2014 ~ 2015 版权所有
 */
@Controller
@RequestMapping("/managment/system/loginLog")
public class LoginLogController extends BaseController{
	private static Logger logger=Logger.getLogger(LoginLogController.class);
	@Resource
	private ILoginLogService loginLogService;
	
	private Lock lock = new ReentrantLock(true);
	private Condition exportLoginCondition = lock.newCondition();

	private int exportLoginStatus = 0;
	
	@RequestMapping("/list")
	@Permission(systemSn="privilege",moduleSn="loginlog",value=PermissionConatant.R)
	public String list(String sessionId,ModelMap model){
		model.addAttribute("sessionId", sessionId);
		return "/system/login_log_page";
	}
	
	@ResponseBody
	@RequestMapping("/ajaxList")
	@Permission(systemSn="privilege",moduleSn="loginlog",value=PermissionConatant.R)
	public String ajaxList(LoginLogVo loginLogVo,Query query){
		PagerModel<LoginLog> pm=null;
		//可以在页面中指定开始时间和结束时间进行查询
		//loginLogVo.setOperationTimeStart("2014-04-09 18:03:48");
		//loginLogVo.setOperationTimeEnd("2014-04-09 19:03:48");
		try{
			pm=loginLogService.getPagerModel(loginLogVo, query);
		}catch(Exception e){
			e.printStackTrace();
			logger.debug("LoginLogController-ajaxList:",e);
		}
		return JsonUtils.getPmJson(pm);
	}
	
	@RequestMapping("/insertUI")
	@Permission(systemSn="privilege",moduleSn="loginlog",value=PermissionConatant.C)
	public String insertUI(String sessionId,ModelMap model){
		model.addAttribute("sessionId", sessionId);
		return "/system/login_log_insert";
	}
	
	@ResponseBody
	@RequestMapping("/insert")
	@Permission(systemSn="privilege",moduleSn="loginlog",value=PermissionConatant.C)
	public String insert(LoginLog loginLog){
		SimpleReturnVo vo=null;
		try {
			loginLogService.insertLoginLog(loginLog);
			vo=new SimpleReturnVo(SUCCESS,"成功");
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug("LoginLogController-insert:",e);
			vo=new SimpleReturnVo(FAIL, "异常");
		}
		return JsonUtils.toJson(vo);
	}
	
	@RequestMapping("/updateUI")
	@Permission(systemSn="privilege",moduleSn="loginlog",value=PermissionConatant.U)
	public String updateUI(String sessionId,ModelMap model){
		model.addAttribute("sessionId", sessionId);
		return "/system/login_log_update";
	}
	
	@ResponseBody
	@RequestMapping("/ajaxUpdate")
	@Permission(systemSn="privilege",moduleSn="loginlog",value=PermissionConatant.U)
	public String ajaxUpdate(String id){
		LoginLog loginLog=null;
		try {
			if(id.matches("\\d+")){
				loginLog=loginLogService.getLoginLogById(Integer.parseInt(id.trim()));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return JsonUtils.toJson(loginLog);
	}
	
	@ResponseBody
	@RequestMapping("/update")
	@Permission(systemSn="privilege",moduleSn="loginlog",value=PermissionConatant.U)
	public String update(LoginLog loginLog){
		SimpleReturnVo vo=null;
		try {
			loginLogService.updateLoginLog(loginLog);
			vo=new SimpleReturnVo(SUCCESS, "成功");
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug("LoginLogController-update:" ,e);
			vo=new SimpleReturnVo(FAIL,"异常");
		}
		return JsonUtils.toJson(vo);
	}
	
	@ResponseBody
	@RequestMapping("/delete")
	@Permission(systemSn="privilege",moduleSn="loginlog",value=PermissionConatant.D)
	public String delete(String idStrs){
		SimpleReturnVo vo=null;
		if (idStrs != null && idStrs.length() > 0) {
			String[] idStr = idStrs.split(",");
			int[] ids=new int[idStr.length];
			try {
				for (int i = 0; i < idStr.length; i++) {
					if(idStr[i].matches("\\d+")){
						ids[i]=Integer.parseInt(idStr[i].trim());
					}
				}
				loginLogService.delLoginLogs(ids);
				vo=new SimpleReturnVo(SUCCESS,"成功");
			} catch (Exception e) {
				e.printStackTrace();
				logger.debug("LoginLogController-delete:" ,e);
				vo=new SimpleReturnVo(FAIL,"异常");
			}
		}
		return JsonUtils.toJson(vo);
	}
	
	
	//导出日志
	@RequestMapping("/export")
	public void export(LoginLogVo loginLogVo,String sessionId, HttpServletResponse response,HttpServletRequest request) throws IOException {
		User loginUser = WebUtils.getLoginUser(request);
		String userName = loginUser.getUsername();
		String realName = loginUser.getRealName();
		String log="【username:"+userName+",realName:"+realName+"】";
		logger.info("正在下载登录日志..."+log);
		lock.lock();
		File file=null;
		try {
			if (StringUtils.isNotBlank(loginLogVo.getOperationUsername())) {
				loginLogVo.setOperationUsername(loginLogVo.getOperationUsername().trim());
			} else {
				loginLogVo.setOperationUsername(null);
			}

			if (StringUtils.isNotBlank(loginLogVo.getOperationPerson())) {
				loginLogVo.setOperationPerson(loginLogVo.getOperationPerson().trim());
			} else {
				loginLogVo.setOperationPerson(null);
			}
			if (StringUtils.isNotBlank(loginLogVo.getOperationTimeStart())) {
				loginLogVo.setOperationTimeStart(loginLogVo.getOperationTimeStart().trim());
			} else {
				loginLogVo.setOperationTimeStart(null);
			}

			if (StringUtils.isNotBlank(loginLogVo.getOperationTimeEnd())) {
				loginLogVo.setOperationTimeEnd(loginLogVo.getOperationTimeEnd().trim());
			} else {
				loginLogVo.setOperationTimeEnd(null);
			}
			
			String realPath = this.getClass().getClassLoader().getResource("/").getPath();
			//1、得到压缩包的名称
			String zipFileName = loginLogService.export(loginLogVo,realPath);
			String path = realPath + zipFileName;
            file = new File(path);    
            //2、下载压缩包
			if (file.exists()) { 
                InputStream ins = new FileInputStream(path);    
                BufferedInputStream bins = new BufferedInputStream(ins);// 放到缓冲流里面    
                OutputStream outs = response.getOutputStream();// 获取文件输出IO流    
                BufferedOutputStream bouts = new BufferedOutputStream(outs);    
                response.setContentType("application/x-download");// 设置response内容的类型    
                response.setHeader(    
                        "Content-disposition",    
                        "attachment;filename="    
                                + URLEncoder.encode(zipFileName, "UTF-8"));// 设置头部信息    
                int bytesRead = 0;    
                byte[] buffer = new byte[8192];    
                // 开始向网络传输文件流    
                while ((bytesRead = bins.read(buffer, 0, 8192)) != -1) {    
                    bouts.write(buffer, 0, bytesRead);    
                }    
                bouts.flush();// 这里一定要调用flush()方法    
                ins.close();    
                bins.close();    
                outs.close();    
                bouts.close();    
                this.exportLoginStatus = 1;
            }else{
            	this.exportLoginStatus = 2;
            	logger.warn("File not find");
            }
		} catch (Exception e) {
			this.exportLoginStatus = 2;
			logger.warn("flush stream fails, loginLogVo:" + loginLogVo);
			logger.error("LoginLogController-export:",e);
			e.printStackTrace();
		} finally {
			if(null!=file && file.exists()){
				//3、删除zip包
				file.delete();
				System.err.println("zipFile:"+file.exists());
			}
			//4、发送通知-告诉exportLoginStatus方法可以继续执行了
			exportLoginCondition.signal();
			lock.unlock();
		}
		logger.info("下载登录完成!"+log);
	}
	
	//用于返回什么时候下载完数据
	@ResponseBody
	@RequestMapping("/exportStatus")
	public String exportStatus(String sessionId) {
		lock.lock();
		SimpleReturnVo returnVo = new SimpleReturnVo(PrivilegeConstant.ERROR, "下载失败");
		try {
			while (this.exportLoginStatus == 0) {
				exportLoginCondition.await();
			}
			if (this.exportLoginStatus == 1) {
				returnVo = new SimpleReturnVo(PrivilegeConstant.SUCCESS, "下载成功");
			}
		} catch (Exception e) {
			logger.error("LoginLogController-exportStatus:",e);
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
		return JsonUtils.toJson(returnVo);
	}
}
