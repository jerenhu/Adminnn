package com.ecnice.privilege.constant;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;


/**
 * @Comment:系统中的常量
 * @author bruce.liu
 * @Create Date 2014年3月27日
 */
public class PrivilegeConstant {
	
	private static Properties props = new Properties();
	static {
		try {
			InputStream pis = PrivilegeConstant.class.getClassLoader().getResourceAsStream("config/application.properties");
			props.load(pis);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static final int success = 1;
	public static final int error = 0;
	
	public static final String TREE_PARENT_ROOT = "ROOT";
	public static final String SYSTEM_SN="privilege";
	//是否是生成环境
	public static final String IS_PROD = "is_prod";
	//公司名称
	public static final String COMPANY_NAME = "company_name";
	//平台名称
	public static final String PLAIN_NAME = "plain_name";
	//平台Logo
	public static final String PLAIN_LOGO = "plain_logo";
	//平台icon
	public static final String PLAN_ICON = "plan_icon";
	/** 普通分割符 */
	public static final String SEPARATOR = ",";
	/** 特殊分割符 */
	public static final String SPECIAL_SEPARATOR = ";,;";
	/**
	 * session在缓存中的存活时间
	 */
	public static final int SESSION_OUT_TIME = 28800;
	//登录用户的session中的key值
	public static final String LOGIN_USER = "login_user";
	//登录用户的acl列表
	public static final String LOGIN_USER_ACLS = "login_user_acls";
	//验证码
	public static final String VERIFY_CODE = "vc";
	
	//密码加密前缀
	public static final String USER_PASSWORD_FRONT = "yasha";
	//密码加密前缀
	public static final String DEFAULT_USER_PASSWORD = "888888";
	//sessionid的名称
	public static final String SESSION_ID = "sessionId";
	
	//资源标示用户
	public static final String RESOURCE_SN_USER = "user";
	//资源标示角色
	public static final String RESOURCE_SN_ROLE = "role";
	
	//数据标示
	public static final String DATA_PRIVILEGE_SN = "data_sn";
	
	//©2015-2016
	public static final String COMPANY_COPY="2015-"+new SimpleDateFormat("yyyy").format(new Date());
	
	// 成功
	public static final String SUCCESS_CODE = "1";
	// 失败
	public static final String ERROR_CODE = "0";
	// 异常
	public static final String EXCEPTION_CODE = "2";
	
	/** 删除状态=0 */
	public static final int HAS_DELETE_FLAG = 0;

	/** 未删除状态=1 */
	public static final int NO_DELETE_FLAG = 1;

	// 成功
	public static final int SUCCESS = 1;
	// 失败
	public static final int ERROR = 0;
	// 是
	public static final int YES = 1;
	// 否
	public static final int NO = 0;
	
	/** 密码有效期（月） */
	public static final int PWD_ENABLE_MONTH = 3;
	/** 密码过期提醒天数 */
	public static final int PWD_WARN_DAY = 5;
	
	//-------角色s-------
	/** 运营商申请记录：责任人对应的角色 */
	public static final String INVESTMENT_SUPPORT = "InvestmentSupport";
	/** 大区经理 */
	public static final String AREAJL = "areajl";
	/** 采购员 */
	public static final String BUYER = "buyer";
	//-------角色e-------
	//E-HR的APPKEY
	public static final String EHR_APPKEY = "214892c7-04f2-4ffc-8c01-18baa9e4b336";
	
	public static final String LOGOUTURL = props.getProperty("idm_logout_pre")+"?"+ props.getProperty("idm_logout_next");
	
	public static final String IDM_LOGIN_URL = props.getProperty("idm_login_url");
	public static final String IDM_PORT_URL =  props.getProperty("idm_port_url"); //"http://10.10.12.235:8080/idm/login.do";

	/**
	 * 创建用户时，默认失效月份
	 */
	public static final Integer DEFAULT_FAIL_MONTH = 0;
	/**
	 * 组织架构树形结构根节点名称
	 */
	public static final String ORG_TREE_ROOT_TEXT = "org.tree.root.text";
	
	/**
	 * 角色等级标识
	 */
	public static final String ROLE_LEVELS_SN = "role.level.sn";
}
