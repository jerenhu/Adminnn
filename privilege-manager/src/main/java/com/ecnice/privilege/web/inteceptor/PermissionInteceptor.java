package com.ecnice.privilege.web.inteceptor;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.ecnice.privilege.cache.CacheEntity;
import com.ecnice.privilege.cache.CacheListHandler;
import com.ecnice.privilege.common.Permission;
import com.ecnice.privilege.common.SessionMap;
import com.ecnice.privilege.constant.PrivilegeConstant;
import com.ecnice.privilege.service.privilege.IAclService;
import com.ecnice.privilege.utils.WebUtils;
import com.ecnice.tools.common.Base64Utils;

/**
 * 权限拦截器
 * 
 * @Comment:
 * @author liu.wj
 * @Create Date 2013-2-15
 * 
 */
public class PermissionInteceptor extends HandlerInterceptorAdapter {

	private Logger logger = Logger.getLogger(PermissionInteceptor.class);

	private IAclService aclService;
	private String[] EXCLUDE_URLS = {
			"/common/", //公共插件-人员选择器
		};
	/**
	 * 在业务处理器处理请求之前被调用 如果返回false 从当前的拦截器往回执行所有拦截器的afterCompletion(),再退出拦截器链
	 * 
	 * 如果返回true 执行下一个拦截器,直到所有的拦截器都执行完毕 再执行被拦截的Controller 然后进入拦截器链,
	 * 从最后一个拦截器往回执行所有的postHandle() 接着再从最后一个拦截器往回执行所有的afterCompletion()
	 */
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		String sessionId = request.getParameter(PrivilegeConstant.SESSION_ID);
		String reqURL = request.getRequestURL().toString();

		//对特殊的一些url不做拦截处理
		if(null!=EXCLUDE_URLS && EXCLUDE_URLS.length>0) {
			boolean exclude = false;
			for(int i=0;i<EXCLUDE_URLS.length;i++) {
				if(reqURL.contains(EXCLUDE_URLS[i])) {
					exclude = true;
					break;
				}
			}
			if(exclude) return true;
		}
		
//		logger.info(request.getHeader("sessionId"));
//		logger.info(request.getRequestURI());
//		response.setHeader("sessionId", sessionId);
		
		Object obj = WebUtils.getSession(request).getAttribute(
				PrivilegeConstant.LOGIN_USER);
		if(obj == null) {
			String path = request.getContextPath();
			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ path + "/";
			response.sendRedirect(basePath + "loginUI.do");
			return false;
		}
		if (StringUtils.isBlank(sessionId)) {
			String path = request.getContextPath();
			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ path + "/";
			String message = Base64Utils.encodeStr("非法操作！");
			response.sendRedirect(basePath + "loginUI.do?message="+message);
			return false;
		}
		return true;
	}

	/**
	 * 在业务处理器处理请求执行完成后,生成视图之前执行的动作
	 */
	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		HandlerMethod handlerMethod = (HandlerMethod) handler;
		// 得到反射方法
		Method method = handlerMethod.getMethod();
		boolean flag = false;
		String sessionId = request.getParameter(PrivilegeConstant.SESSION_ID);
		try {
			flag = this.checkPermission(method, sessionId);
		} catch (Exception e) {
			logger.warn("session过期");
			e.printStackTrace();
		}
		if (!flag) {
			String path = request.getContextPath();
			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ path + "/";
			response.sendRedirect(basePath + "exception.jsp");
		}
	}

	//判断权限
	public boolean checkPermission(Method method, String sessionId) {
		boolean flag = true;
		// 1:查询session中的user是否存在
		CacheEntity ce = CacheListHandler.getCache(sessionId);
		if (ce != null) {
			SessionMap sessionMap = (SessionMap) ce.getCacheContext();
			String userJson = (String) sessionMap
					.get(PrivilegeConstant.LOGIN_USER);
			// 2：如果不存在user从缓存中取出user通过sessionId
			if (StringUtils.isBlank(userJson)) {
				return false;
			}
		}
		if (method != null) {
			Permission permission = method.getAnnotation(Permission.class);
			// 3.判断该方法上面是否有注解，如果没有默认就是可执行方法，如果有就判断该方法是不是有权限执行
			if (permission != null) {
				// 判断该用户有没有这个权限
				flag = aclService.hasPermission(sessionId,
						permission.systemSn(), permission.moduleSn(),
						permission.value());
			}
		}
		return flag;
	}

	public void setAclService(IAclService aclService) {
		this.aclService = aclService;
	}
}
