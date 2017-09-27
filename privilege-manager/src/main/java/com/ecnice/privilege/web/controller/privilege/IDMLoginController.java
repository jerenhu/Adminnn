package com.ecnice.privilege.web.controller.privilege;

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
import com.ecnice.privilege.model.privilege.User;
import com.ecnice.privilege.service.privilege.IAclService;
import com.ecnice.privilege.service.privilege.IICSystemService;
import com.ecnice.privilege.service.privilege.IUserService;
import com.ecnice.privilege.service.privilege.InitSystem;
import com.ecnice.privilege.service.system.ILoginLogService;
import com.ecnice.privilege.utils.VerifyCodeUtil;
import com.ecnice.privilege.utils.WebUtils;
import com.ecnice.privilege.web.controller.BaseController;
import com.ecnice.tools.common.Base64Utils;
import com.ecnice.tools.common.ReadProperty;

/**
 * 单点登录控制器
 * @Title:
 * @Description:
 * @Author:wangzequan
 * @Since:2016年12月1日 下午2:37:59
 * @Version:1.1.0
 * @Copyright:Copyright (c) 浙江蘑菇加电子商务有限公司 2015 ~ 2016 版权所有
 */
@Controller
@RequestMapping("/idm")
public class IDMLoginController extends BaseController {

	private static final String COOKIE_USER_PWD = "mhome_user_pwd";
	private static Logger logger = Logger.getLogger(IDMLoginController.class);

	@Resource
	private IUserService userService;
	@Resource
	private InitSystem initSystem;
	@Resource
	private IAclService aclService;
	@Resource
	private ILoginLogService loginLogService;
	@Resource
	private IICSystemService iIcSystemService;
	@Resource
	private ReadProperty readProperty;
	
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
		this.setCookieValue(request, model);
		model.put("copy", PrivilegeConstant.COMPANY_COPY);
		//得到系统配置信息
		super.getSystemConfigsInfoToModelMap(request,model);
		return "/frame/login";
	}

	/**
	 * 登录输入界面
	 * 
	 * @return
	 */
	@RequestMapping("/loginUI")
	public String loginUI(HttpServletRequest request, ModelMap model,String message) {
		this.setCookieValue(request, model);
		if(StringUtils.isNotBlank(message)) {
			model.addAttribute("message", Base64Utils.decodeStr(message));
		}
		model.put("copy", PrivilegeConstant.COMPANY_COPY);
		//得到系统配置信息
		super.getSystemConfigsInfoToModelMap(request,model);
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
	 * @return
	 * @Description: 用户登录
	 */
	@RequestMapping("/login")
	public String login(ModelMap model,HttpServletRequest request) {
		User user = null;
		String sessionId = "0";
		try {/*
			HttpSession session = request.getSession();
			final Assertion assertion = (Assertion) session.getAttribute(AbstractCasFilter.CONST_CAS_ASSERTION);
			if(assertion == null){
				//没有登录成功 oa登录页面
				logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>> assertion have value : "+assertion);
				return "redirect:"+PrivilegeConstant.IDM_LOGIN_URL;
			}else{
				session.setAttribute("isFromIMD", "1");
				//登录成功，获取用户名和返回属性
				AttributePrincipal principal = assertion.getPrincipal();
				//工号test2/123-->test2
				String uid = principal.getName();
				logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>> get session uid   : "+uid);
				//uid = "admin";
				//其他属性（可配置返回）
				Map<String, Object> attributes = principal.getAttributes();
				Object mobile = attributes.get("mobile");//手机号
				Object cn = attributes.get("cn");//姓名
				Object mail = attributes.get("mail");//邮件地址
				
				//系统逻辑
				
				User quser = new User();
				quser.setUsername(uid);
				List<User> users= userService.getAll(quser);
				if(null!=users && users.size()>0){
					// 1:查询到用户对象
					logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>> get self user info  : "+users.get(0).getUsername());
					user = users.get(0);
				}
				if (user != null) {
					// 2:用户信息放到session里面一份
					WebUtils.getSession(request).setAttribute(PrivilegeConstant.LOGIN_USER, user);
					// 3：把用户拥有的所有权限ACL列表查询出来放到session中
					Set<ACL> acls = (Set<ACL>) aclService.getAclsByUserId(user.getId());
					WebUtils.getSession(request).setAttribute(PrivilegeConstant.LOGIN_USER_ACLS, acls);
					sessionId = WebUtils.getSession(request).getId();
					request.setAttribute("sessionId", sessionId);
					
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					int day = DateUtil.diffDate(sdf.parse(sdf.format(user.getFailureTime())), sdf.parse(sdf.format(new Date())));
					if (day > 0 && day <= 7) {
						model.put("warnDay", day); 
					} else if (day <= 0) { // 过期了-直接登录
						return "/frame/login";
					}
					
					boolean pwdFlag = true;
					//2、验证密码有效性
					//0.1 初始化密码未修改
					if (user.getPwdInit().intValue() != PrivilegeConstant.SUCCESS) {// 初始化密码未修改
						pwdFlag = false;
						model.put("pwdMsg", "初始化密码未修改，请重置密码。");
					}
					if(pwdFlag){
						//0.2 密码过期
						int dayPwd = DateUtil.diffDate(sdf.parse(sdf.format(user.getPwdFtime())), sdf.parse(sdf.format(new Date())));
						if (dayPwd <= 0) {// 密码过期
							pwdFlag = false;
							model.put("pwdMsg", "密码已过期，请重置密码！");
						} else if (dayPwd <= PrivilegeConstant.PWD_WARN_DAY) {
							model.put("pwdWarnDay", dayPwd);//过期天数
						}
					}
					if (!pwdFlag) {
						//tokenAuthtication.getResetTokenResult(strTokenID, Appkey);
						return "/frame/index";
					}

					List<ICSystem> systems = null;
					if (user.getUsername().equals(readProperty.getValue("system.admin"))) {
						systems = iIcSystemService.getAllIcSystem(null);
						for (ICSystem icSystem : systems) {
							icSystem.setUrl("http://10.20.12.205:8080");
						}
					} else {
						//systems = iIcSystemService.getICSystemsByUserId(user.getId());
						systems = iIcSystemService.getICSystemsByAcls(aclService.getSessionAcls(sessionId));
						// 给登录用户的设置都放里面去
						ICSystem icSystem = iIcSystemService.getICSystemBySn("setup");
						if (icSystem != null) {
							systems.add(icSystem);
						}
					}
					if (systems != null && systems.size() > 0) {
						ICSystem system = systems.get(0);
						model.addAttribute("systemName", system.getName());
					}
						
					model.addAttribute("systems", systems);
					
					//插入登录日志
					String ip = CommUtils.getRealRemoteIP(request);//request.getRemoteAddr();
					LoginLog loginLog = new LoginLog(ip, user.getId(), user.getUsername(), user.getRealName(), "登录");
					loginLogService.insertLoginLog(loginLog);
					
					//1、得到系统配置信息
					//model.put("copy", PrivilegeConstant.COMPANY_COPY);
					//model.addAttribute("copy", PrivilegeConstant.COMPANY_COPY);
					request.setAttribute("copy", PrivilegeConstant.COMPANY_COPY);
					super.getSystemConfigsInfoToModelMap(request,model);
				}else{
					logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>> user info   is null : ");
					return "/frame/nopower";
				}
				
			}
		*/} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
			return "/frame/nopower";
		}finally {
		}
		return "/frame/index";
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
		    bytes = VerifyCodeUtil.getVerifyCode(80, 40,request,response);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("产生检验码出错!",e);
		}
		return new ResponseEntity<byte[]>(bytes, headers, HttpStatus.CREATED);
	}
}
