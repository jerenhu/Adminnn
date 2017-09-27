package com.ecnice.tools.push.jpush;

import org.apache.log4j.Logger;

import cn.jpush.api.JPushClient;
import cn.jpush.api.common.resp.APIConnectionException;
import cn.jpush.api.common.resp.APIRequestException;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.audience.AudienceTarget;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;

/**
 * 极光推送Test
 * 
 * @Title:
 * @Description:
 * @Author:wentaoxiang
 * @Since:2016年10月13日 下午5:39:59
 * @Version:1.1.0
 * @Copyright:Copyright (c) 浙江蘑菇加电子商务有限公司 2015 ~ 2016 版权所有
 */
public class TestJPushUtils {
	private static final Logger LOG = Logger.getLogger(TestJPushUtils.class);
	
	private static JPushClient jpushClient = null;
//	private static final String masterSecret = "52c627c8f3b2f61391a334d2"; //正式
//	private static final String appKey = "8764b0b0f426548a1a799fe3";  //正式
	private static final String masterSecret = "7c5f5f661d3f5b896222428e"; //测试
	private static final String appKey = "5a63230ce75f88468eac0f66"; //测试
	private static final String ALERT = "【蘑菇加】尊敬的**，您在**小区*幢*单元*室的工地蘑菇大叔张三已到位，手机号13788888888。感谢您对蘑菇加的信赖与支持，整体家装，就选蘑菇加。下载蘑菇加APP，乐享轻松装修，请点击**************。";
	private static final String MSG_CONTENT = "";
	private static final String TITLE = "";
	
	static {
		jpushClient = new JPushClient(masterSecret, appKey);
	}
	
	public static void pushMsg() {
		jpushClient = new JPushClient(masterSecret, appKey);

		// For push, all you need do is to build PushPayload object.
		PushPayload payload = buildPushObject_all_all_alert();

		try {
			PushResult result = jpushClient.sendPush(payload);
			LOG.info("Got result - " + result);

		} catch (APIConnectionException e) {
			// Connection error, should retry later
			LOG.error("Connection error, should retry later", e);

		} catch (APIRequestException e) {
			// Should review the error, and fix the request
			LOG.error("Should review the error, and fix the request", e);
			LOG.info("HTTP Status: " + e.getStatus());
			LOG.info("Error Code: " + e.getErrorCode());
			LOG.info("Error Message: " + e.getErrorMessage());
		}
	}

	// 快捷地构建推送对象：所有平台，所有设备，内容为 ALERT 的通知。
	public static PushPayload buildPushObject_all_all_alert() {
		return PushPayload.alertAll(ALERT);
	}

	// 构建推送对象：所有平台，推送目标是别名为 "alias1"，通知内容为 ALERT。
	public static PushPayload buildPushObject_all_alias_alert() {
		return PushPayload.newBuilder().setPlatform(Platform.all()).setAudience(Audience.alias("alias1")).setNotification(Notification.alert(ALERT)).build();
	}

	// 构建推送对象：平台是 Android，目标是 tag 为 "tag1" 的设备，内容是 Android 通知 ALERT，并且标题为 TITLE。
	public static PushPayload buildPushObject_android_tag_alertWithTitle() {
		return PushPayload.newBuilder().setPlatform(Platform.android()).setAudience(Audience.tag("tag1")).setNotification(Notification.android(ALERT, TITLE, null)).build();
	}

	// 构建推送对象：平台是 iOS，推送目标是 "tag1", "tag_all" 的交集，推送内容同时包括通知与消息 - 通知信息是
	// ALERT，角标数字为 5，通知声音为 "happy"，并且附加字段 from = "JPush"；消息内容是 MSG_CONTENT。通知是
	// APNs 推送通道的，消息是 JPush 应用内消息通道的。APNs 的推送环境是“生产”（如果不显式设置的话，Library 会默认指定为开发）
	public static PushPayload buildPushObject_ios_tagAnd_alertWithExtrasAndMessage() {
		return PushPayload.newBuilder().setPlatform(Platform.ios()).setAudience(Audience.tag_and("tag1", "tag_all"))
				.setNotification(Notification.newBuilder()
						.addPlatformNotification(IosNotification.newBuilder().setAlert(ALERT).setBadge(5).setSound("happy.caf").addExtra("from", "JPush").build()).build())
				.setMessage(Message.content(MSG_CONTENT)).setOptions(Options.newBuilder().setApnsProduction(true).build()).build();
	}

	// 构建推送对象：平台是 Andorid 与 iOS，推送目标是 （"tag1" 与 "tag2" 的并集）且（"alias1" 与 "alias2"
	// 的并集），推送内容是 - 内容为 MSG_CONTENT 的消息，并且附加字段 from = JPush。
	public static PushPayload buildPushObject_ios_audienceMore_messageWithExtras() {
		return PushPayload.newBuilder().setPlatform(Platform.android_ios())
				.setAudience(Audience.newBuilder().addAudienceTarget(AudienceTarget.tag("tag1", "tag2")).addAudienceTarget(AudienceTarget.alias("alias1", "alias2")).build())
				.setMessage(Message.newBuilder().setMsgContent(MSG_CONTENT).addExtra("from", "JPush").build()).build();
	}
	
	public static void main(String[] args) throws APIConnectionException, APIRequestException {
		pushMsg();//广播
		//单个设备推送
		PushResult result = jpushClient.sendPush(PushPayload.newBuilder().setPlatform(Platform.android())
				.setAudience(Audience.registrationId("100d8559094e4c1f15a")).setNotification(Notification.android("I love you", "蘑菇加", null)).build());
		System.err.println(result);
	}

}
