package com.ecnice.privilege.vo.privilege;

import java.io.Serializable;

/**
 * 判断时候有权限的vo
 * 
 * @Title:
 * @Description:
 * @Author:wentaoxiang
 * @Since:2016年1月14日 下午5:20:07
 * @Version:1.1.0
 * @Copyright:Copyright (c) 浙江蘑菇加电子商务有限公司 2015 ~ 2016 版权所有
 */
public class HasPermissionVo implements Serializable{
	
	/**
	 *
	 */
		
	private static final long serialVersionUID = 1L;
	/**
	 * url
	 */
	private String url;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public String toString() {
		return "HasPermissionVo [url=" + url + "]";
	}

}
