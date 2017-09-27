package com.ecnice.privilege.component.httpclient;

/**
 * @Title:
 * @Description:httpclient接口
 * @Author:Bruce.Liu
 * @Since:2014年4月4日
 * @Version:1.1.0
 */
public interface IHttpclient {

	/**
	 * @param sessionId
	 * @Description:系统的url列表 执行给系统添加一个静态的常量 即使系统缓存
	 */
	public void execute(String url,String sessionId)throws Exception;
}
