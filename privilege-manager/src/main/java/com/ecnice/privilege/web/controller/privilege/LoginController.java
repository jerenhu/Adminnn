package com.ecnice.privilege.web.controller.privilege;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ecnice.privilege.constant.PrivilegeConstant;
import com.ecnice.privilege.model.privilege.ACL;
import com.ecnice.privilege.model.privilege.User;
import com.ecnice.privilege.model.system.LoginLog;
import com.ecnice.privilege.service.privilege.IAclService;
import com.ecnice.privilege.service.privilege.IUserService;
import com.ecnice.privilege.service.privilege.InitSystem;
import com.ecnice.privilege.service.system.ILoginLogService;
import com.ecnice.privilege.utils.CommUtils;
import com.ecnice.privilege.utils.CookiesUtil;
import com.ecnice.privilege.utils.DateUtil;
import com.ecnice.privilege.utils.VerifyCodeUtil;
import com.ecnice.privilege.utils.WebUtils;
import com.ecnice.privilege.web.controller.BaseController;
import com.ecnice.tools.common.Base64Utils;

/**
 * @Comment:用户登录
 * @author: bruce.liu@ceacsz.com.cn
 * @version: 2014年3月27日 下午5:59:30
 */
@Controller
@RequestMapping("/")
public class LoginController extends BaseController {

	private static final String COOKIE_USER_PWD = "mhome_user_pwd";
	private static Logger logger = Logger.getLogger(LoginController.class);

	@Resource
	private IUserService userService;
	@Resource
	private InitSystem initSystem;
	@Resource
	private IAclService aclService;
	@Resource
	private ILoginLogService loginLogService;
	
	// 初始化
	@RequestMapping("/init")
	public String init(HttpServletRequest request, ModelMap model) {
		try{
			//通过userId = 1查询数据库是否有记录
			User user=this.userService.getUserById("1");
			if(user==null){
				initSystem.init();
			}
		}catch(Exception e){
			e.printStackTrace();
			model.addAttribute("message", "初始化失败!");
			return "/frame/login";
		}
		return "/frame/login";
	}

	// 退出
	@RequestMapping("/logout")
	public String logout(HttpServletRequest request, ModelMap model) {
		WebUtils.getSession(request).removeAttribute(
				PrivilegeConstant.LOGIN_USER);
		//this.setCookieValue(request, model);
		model.put("copy", PrivilegeConstant.COMPANY_COPY);
		//得到系统配置信息
		super.getSystemConfigsInfoToModelMap(request,model);
		
		//isFromIMD
		/*HttpSession session  = request.getSession();
		Object isFromIMDObj = session.getAttribute("isFromIMD");
		if(null!=isFromIMDObj && isFromIMDObj instanceof String){
			String isFromIMD = (String)isFromIMDObj;
			session.removeAttribute("isFromIMD");
			return "redirect:"+PrivilegeConstant.LOGOUTURL;
		}else{
			return "/frame/login";
		}*/
		return "/frame/login";
	}

	/**
	 * 登录输入界面
	 * 
	 * @return
	 **/
	@RequestMapping("/loginUI")
	public String loginUI(HttpServletRequest request, ModelMap model,String message) {
		this.setCookieValue(request, model);
		if(StringUtils.isNotBlank(message)) {
			model.addAttribute("message", Base64Utils.decodeStr(message));
		}
		//model.addAttribute("copy", PrivilegeConstant.COMPANY_COPY);
		request.setAttribute("copy", PrivilegeConstant.COMPANY_COPY);
		//得到系统配置信息
		super.getSystemConfigsInfoToModelMap(request,model);
		
//		HttpSession session =  request.getSession();//
//		//String  isFromIMD = (String)session.getAttribute("isFromIMD");
//		Assertion assertion = (Assertion) session.getAttribute(AbstractCasFilter.CONST_CAS_ASSERTION);
//		if(null!=assertion){
//			return "redirect:"+PrivilegeConstant.IDM_PORT_URL;
//		}
		return "/frame/login";
	}

	

	// 恢复cookie值
	private void setCookieValue(HttpServletRequest request, ModelMap model) {
		String base64userpwd = WebUtils.findCookieValue(request,
				COOKIE_USER_PWD);
		if (StringUtils.isNotBlank(base64userpwd)) {
			String userpwd = Base64Utils.decodeStr(base64userpwd);
			String[] userpwds = userpwd.split("~");
			model.addAttribute("username", userpwds[0]);
			model.addAttribute("password", userpwds[1]);
			model.addAttribute("selectFlag", userpwds[2]);
		}
	}

	/**
	 * 
	 * @param request
	 * @param username
	 * @param password
	 * @return
	 * @Description: 用户登录
	 */
	@ResponseBody
	@RequestMapping("/login")
	public String login(HttpServletRequest request,
			HttpServletResponse response, String username, String password,String company,
			Integer selectFlag, String verifyCode) {
		User user = null;
		String sessionId = "0";
		try {
			String cvcode = CookiesUtil.get(request, PrivilegeConstant.VERIFY_CODE);
			if(StringUtils.isNotBlank(verifyCode) && StringUtils.isNotBlank(cvcode)){
				verifyCode = verifyCode.toLowerCase();
				cvcode = cvcode.toLowerCase();
			}
			if(StringUtils.isNotBlank(verifyCode) && StringUtils.isNotBlank(cvcode) && cvcode.equals(verifyCode)) {
				// 1:查询到用户对象
				user = userService.login(username, password);
				
				//1.1验证是否过期 
				if (null != user && null != user.getFailureTime()) {
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					int day = DateUtil.diffDate(sdf.parse(sdf.format(user.getFailureTime())), sdf.parse(sdf.format(new Date())));
					if (day <= 0) {
						sessionId = "ft"; // 过期
						return sessionId;
					}
				}else if(null != user && null == user.getFailureTime()){
					sessionId = "sft"; // 未设置过期时间
					return sessionId;
				}
				
				// 如果选择了就放到cookie里面 这个必须放在登录成功之后
				if (user!=null && selectFlag != null && selectFlag.intValue() == 1) {
					String namePwd = username + "~" + password + "~" + selectFlag;
					String base64Str = Base64Utils.encodeStr(namePwd);
					WebUtils.addCookie(request, response, COOKIE_USER_PWD, base64Str, 2592000);
				} else {
					WebUtils.failureCookie(request, response, COOKIE_USER_PWD);
				}
				if (user != null) {
					// 2:用户信息放到session里面一份
					WebUtils.getSession(request).setAttribute(
							PrivilegeConstant.LOGIN_USER, user);
					// 3：把用户拥有的所有权限ACL列表查询出来放到session中
					Set<ACL> acls = (Set<ACL>) aclService.getAclsByUserId(user
							.getId());
					WebUtils.getSession(request).setAttribute(
							PrivilegeConstant.LOGIN_USER_ACLS, acls);
					sessionId = WebUtils.getSession(request).getId();
					//插入登录日志
					String ip = CommUtils.getRealRemoteIP(request);//request.getRemoteAddr();
					LoginLog loginLog = new LoginLog(ip, user.getId(), user.getUsername(), user.getRealName(), "登录");
					loginLogService.insertLoginLog(loginLog);
				}
			}else {
				sessionId = "vc";
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
		response.setHeader("sessionId", sessionId);
		return sessionId;
	}
	
	@RequestMapping("/index")
	public String index(HttpServletRequest request,ModelMap model) {
		//1、得到系统配置信息
		model.put("copy", PrivilegeConstant.COMPANY_COPY);
		super.getSystemConfigsInfoToModelMap(request,model);
		return "/index";
	}
	
	/**
	 * 产生随机检验码
	 * @param username 登录用户名
	 * @param password 登录密码
	 * @param verifyCode 检验码
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/verifyCode")
	public ResponseEntity<byte[]> verifyCode(HttpServletRequest request,HttpServletResponse response) {
		byte[] bytes = null;
		HttpHeaders headers = new HttpHeaders();
		try {
		    headers.setContentType(MediaType.IMAGE_PNG);
		    headers.add("pragma", "no-cache");
		    headers.add("cache-control", "no-cache");
		    headers.add("expires", "0");
		    bytes = VerifyCodeUtil.getVerifyCode(110, 40,request,response);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("产生检验码出错!",e);
		}
		return new ResponseEntity<byte[]>(bytes, headers, HttpStatus.CREATED);
	}
}
