package com.ecnice.tools.push;

import java.util.List;

/**
 * 推送服务
 * 
 * @Title:
 * @Description:
 * @Author:wentaoxiang
 * @Since:2016年10月14日 上午11:47:24
 * @Version:1.1.0
 * @Copyright:Copyright (c) 浙江蘑菇加电子商务有限公司 2015 ~ 2016 版权所有
 */
public interface IPushService {

	/**
	 * 推送消息到所有设备
	 * @param pushMsg
	 * @param deviceIds
	 * @return
	 * @Description:
	 * @author wentaoxiang 2016年10月17日 上午9:05:21
	 */
	public IPushService pushAll(PushMsg pushMsg, String... deviceIds);
	/**
	 * 推送消息到所有设备
	 * @param pushMsg
	 * @param deviceIds
	 * @return
	 * @Description:
	 * @author wentaoxiang 2016年10月17日 上午9:05:24
	 */
	public IPushService pushAll(PushMsg pushMsg, List<String> deviceIds);

	
	/**
	 * 推送消息到IOS
	 * @param pushMsg
	 * @param deviceIds
	 * @Description:
	 * @author wentaoxiang 2016年10月14日 下午3:27:37
	 */
	public IPushService pushIOS(PushMsg pushMsg, String... deviceIds);
	/**
	 * 推送消息到IOS
	 * @param pushMsg
	 * @param deviceIds
	 * @Description:
	 * @author wentaoxiang 2016年10月14日 下午3:27:42
	 */
	public IPushService pushIOS(PushMsg pushMsg, List<String> deviceIds);

	
	/**
	 * 推送消息到Android
	 * @param pushMsg
	 * @param deviceIds
	 * @Description:
	 * @author wentaoxiang 2016年10月14日 下午3:27:25
	 */
	public IPushService pushAndroid(PushMsg pushMsg, String... deviceIds);
	/**
	 * 推送消息到Android
	 * @param pushMsg
	 * @param deviceIds
	 * @Description:
	 * @author wentaoxiang 2016年10月14日 下午3:27:29
	 */
	public IPushService pushAndroid(PushMsg pushMsg, List<String> deviceIds);

	
	/**
	 * 推送消息到winPhone
	 * @param pushMsg
	 * @param deviceIds
	 * @Description:
	 * @author wentaoxiang 2016年10月14日 下午3:27:17
	 */
	public IPushService pushWp(PushMsg pushMsg, String... deviceIds);
	/**
	 * 推送消息到winPhone
	 * @param pushMsg
	 * @param deviceIds
	 * @Description:
	 * @author wentaoxiang 2016年10月14日 下午3:27:00
	 */
	public IPushService pushWp(PushMsg pushMsg, List<String> deviceIds);

}
