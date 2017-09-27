package com.ecnice.privilege.utils;

import org.apache.commons.lang.StringUtils;

/***
 * url处理
 * @Author:TaoXiang.Wen
 * @Since:2015年12月30日
 * @Version:1.1.0浙江蘑菇加电子商务有限公司 2014 ~ 2015 版权所有
 */
public class UrlUtils {
	/**
	 * 去掉url最后的 '/' 例如 'http://127.0.0.1:8080/privilege-manager/' ---> 'http://127.0.0.1:8080/privilege-manager'
	 */
	public static String handelUrl(String url) {
		if (StringUtils.isNotBlank(url)) {
			url = url.trim();
			if (url.lastIndexOf("/") == url.length() - 1) {
				url = url.substring(0, url.length() - 1);
			}
		}
		return url;
	}

	/***
	 * 处理模块的url，最前面加上 '/' ,例如 'managment/privilege/icsystem/list.do' ---> '/managment/privilege/icsystem/list.do'
	 */
	public static String handelModelUrl(String url) {
		if (StringUtils.isNotBlank(url)) {
			url = url.trim();
			if (!url.substring(0, 1).equals("/")) {
				url = "/" + url;
			}
		}
		return url;
	}
}
