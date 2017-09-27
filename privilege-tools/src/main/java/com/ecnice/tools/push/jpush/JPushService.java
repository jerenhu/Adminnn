package com.ecnice.tools.push.jpush;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.log4j.Logger;

import com.ecnice.tools.common.JsonUtils;
import com.ecnice.tools.push.IPushService;
import com.ecnice.tools.push.PushMsg;

import cn.jpush.api.JPushClient;
import cn.jpush.api.common.resp.APIConnectionException;
import cn.jpush.api.common.resp.APIRequestException;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;
import cn.jpush.api.push.model.notification.WinphoneNotification;

public class JPushService implements IPushService {
	private static final Logger log = Logger.getLogger(TestJPushUtils.class);
	private JPushClient jpushClient = null;

	public JPushService(String masterSecret,String appKey) {
		jpushClient = new JPushClient(masterSecret, appKey);
	}

	@SuppressWarnings("static-access")
	@Override
	public IPushService pushAll(PushMsg pushMsg, String... deviceIds) {
		try {
			PushResult result = null;
			if (ArrayUtils.isEmpty(deviceIds)) {
				result = jpushClient.sendPush(PushPayload.alertAll(pushMsg.getAlert()).messageAll(JsonUtils.toJson(pushMsg)));
			} else {
				result = jpushClient.sendPush(
						PushPayload.newBuilder().setPlatform(Platform.all()).setAudience(Audience.registrationId(deviceIds)).setNotification(Notification.alert(pushMsg.getAlert()))
								.setMessage(Message.newBuilder().setMsgContent(JsonUtils.toJson(pushMsg)).build()).build());
			}
			log.debug("Got result - " + result);
		} catch (APIConnectionException e) {
			// Connection error, should retry later
			log.error("Connection error, should retry later", e);
		} catch (APIRequestException e) {
			// Should review the error, and fix the request
			log.error("Should review the error, and fix the request", e);
			log.error("HTTP Status: " + e.getStatus());
			log.error("Error Code: " + e.getErrorCode());
			log.error("Error Message: " + e.getErrorMessage());
		}
		return this;
	}

	@SuppressWarnings("static-access")
	@Override
	public IPushService pushAll(PushMsg pushMsg, List<String> deviceIds) {
		try {
			PushResult result = null;
			if (CollectionUtils.isEmpty(deviceIds)) {
				result = jpushClient.sendPush(PushPayload.alertAll(pushMsg.getAlert()).messageAll(JsonUtils.toJson(pushMsg)));
			} else {
				result = jpushClient.sendPush(
						PushPayload.newBuilder().setPlatform(Platform.all()).setAudience(Audience.registrationId(deviceIds)).setNotification(Notification.alert(pushMsg.getAlert()))
								.setMessage(Message.newBuilder().setMsgContent(JsonUtils.toJson(pushMsg)).build()).build());
			}
			log.debug("Got result - " + result);
		} catch (APIConnectionException e) {
			// Connection error, should retry later
			log.error("Connection error, should retry later", e);
		} catch (APIRequestException e) {
			// Should review the error, and fix the request
			log.error("Should review the error, and fix the request", e);
			log.error("HTTP Status: " + e.getStatus());
			log.error("Error Code: " + e.getErrorCode());
			log.error("Error Message: " + e.getErrorMessage());
		}
		return this;
	}

	@Override
	public IPushService pushIOS(PushMsg pushMsg, String... deviceIds) {
		try {
			PushResult result = jpushClient
					.sendPush(
							PushPayload.newBuilder().setPlatform(Platform.ios()).setAudience(Audience.registrationId(deviceIds))
									.setNotification(Notification.newBuilder()
											.addPlatformNotification(
													IosNotification.newBuilder().setAlert(pushMsg.getAlert()).addExtra("pushMsgJson", JsonUtils.toJson(pushMsg)).build())
											.build())
									.setMessage(Message.newBuilder().setMsgContent(JsonUtils.toJson(pushMsg)).build()).build());
			log.debug("Got result - " + result);
		} catch (APIConnectionException e) {
			// Connection error, should retry later
			log.error("Connection error, should retry later", e);
		} catch (APIRequestException e) {
			// Should review the error, and fix the request
			log.error("Should review the error, and fix the request", e);
			log.error("HTTP Status: " + e.getStatus());
			log.error("Error Code: " + e.getErrorCode());
			log.error("Error Message: " + e.getErrorMessage());
		}
		return this;
	}

	@Override
	public IPushService pushIOS(PushMsg pushMsg, List<String> deviceIds) {
		try {
			PushResult result = jpushClient
					.sendPush(
							PushPayload.newBuilder().setPlatform(Platform.ios()).setAudience(Audience.registrationId(deviceIds))
									.setNotification(Notification.newBuilder()
											.addPlatformNotification(
													IosNotification.newBuilder().setAlert(pushMsg.getAlert()).addExtra("pushMsgJson", JsonUtils.toJson(pushMsg)).build())
											.build())
									.setMessage(Message.newBuilder().setMsgContent(JsonUtils.toJson(pushMsg)).build()).build());
			log.debug("Got result - " + result);
		} catch (APIConnectionException e) {
			// Connection error, should retry later
			log.error("Connection error, should retry later", e);
		} catch (APIRequestException e) {
			// Should review the error, and fix the request
			log.error("Should review the error, and fix the request", e);
			log.error("HTTP Status: " + e.getStatus());
			log.error("Error Code: " + e.getErrorCode());
			log.error("Error Message: " + e.getErrorMessage());
		}
		return this;
	}

	@Override
	public IPushService pushAndroid(PushMsg pushMsg, String... deviceIds) {
		try {
			Map<String,String> map = new HashMap<String, String>();
			map.put("jsonpush", JsonUtils.toJson(pushMsg));
			PushResult result = jpushClient.sendPush(PushPayload.newBuilder().setPlatform(Platform.android()).setAudience(Audience.registrationId(deviceIds))
					.setNotification(Notification.android(pushMsg.getAlert(), pushMsg.getTitle(), map))
					.build());
			
			/*PushResult result = jpushClient.sendPush(PushPayload.newBuilder().setPlatform(Platform.android()).setAudience(Audience.registrationId(deviceIds))
			.setNotification(Notification.android(pushMsg.getAlert(), pushMsg.getTitle(), null))
			.setMessage(Message.newBuilder().setMsgContent(JsonUtils.toJson(pushMsg)).addExtra("jsonpush", JsonUtils.toJson(pushMsg)).build()).build());*/
			log.debug("Got result - " + result);
		} catch (APIConnectionException e) {
			// Connection error, should retry later
			log.error("Connection error, should retry later", e);
		} catch (APIRequestException e) {
			// Should review the error, and fix the request
			log.error("Should review the error, and fix the request", e);
			log.error("HTTP Status: " + e.getStatus());
			log.error("Error Code: " + e.getErrorCode());
			log.error("Error Message: " + e.getErrorMessage());
		}
		return this;
	}

	@Override
	public IPushService pushAndroid(PushMsg pushMsg, List<String> deviceIds) {
		try {
			Map<String,String> map = new HashMap<String, String>();
			map.put("jsonpush", JsonUtils.toJson(pushMsg));
			PushResult result = jpushClient.sendPush(PushPayload.newBuilder().setPlatform(Platform.android()).setAudience(Audience.registrationId(deviceIds))
					.setNotification(Notification.android(pushMsg.getAlert(), pushMsg.getTitle(), map))
					.build());
			
			/*PushResult result = jpushClient.sendPush(PushPayload.newBuilder().setPlatform(Platform.android()).setAudience(Audience.registrationId(deviceIds))
					.setNotification(Notification.android(pushMsg.getAlert(), pushMsg.getTitle(), null))
					.setMessage(Message.newBuilder().setMsgContent(JsonUtils.toJson(pushMsg)).addExtra("jsonpush", JsonUtils.toJson(pushMsg)).build()).build());*/
			log.debug("Got result - " + result);
		} catch (APIConnectionException e) {
			// Connection error, should retry later
			log.error("Connection error, should retry later", e);
		} catch (APIRequestException e) {
			// Should review the error, and fix the request
			log.error("Should review the error, and fix the request", e);
			log.error("HTTP Status: " + e.getStatus());
			log.error("Error Code: " + e.getErrorCode());
			log.error("Error Message: " + e.getErrorMessage());
		}
		return this;
	}

	@Override
	public IPushService pushWp(PushMsg pushMsg, String... deviceIds) {
		try {
			PushResult result = jpushClient.sendPush(PushPayload.newBuilder().setPlatform(Platform.winphone()).setAudience(Audience.registrationId(deviceIds))
					.setNotification(Notification.newBuilder().addPlatformNotification(WinphoneNotification.newBuilder().setAlert(pushMsg.getAlert()).build()).build())
					.setMessage(Message.newBuilder().setMsgContent(JsonUtils.toJson(pushMsg)).build()).build());
			log.debug("Got result - " + result);
		} catch (APIConnectionException e) {
			// Connection error, should retry later
			log.error("Connection error, should retry later", e);
		} catch (APIRequestException e) {
			// Should review the error, and fix the request
			log.error("Should review the error, and fix the request", e);
			log.error("HTTP Status: " + e.getStatus());
			log.error("Error Code: " + e.getErrorCode());
			log.error("Error Message: " + e.getErrorMessage());
		}
		return this;
	}

	@Override
	public IPushService pushWp(PushMsg pushMsg, List<String> deviceIds) {
		try {
			PushResult result = jpushClient.sendPush(PushPayload.newBuilder().setPlatform(Platform.winphone()).setAudience(Audience.registrationId(deviceIds))
					.setNotification(Notification.newBuilder().addPlatformNotification(WinphoneNotification.newBuilder().setAlert(pushMsg.getAlert()).build()).build())
					.setMessage(Message.newBuilder().setMsgContent(JsonUtils.toJson(pushMsg)).build()).build());
			log.debug("Got result - " + result);
		} catch (APIConnectionException e) {
			// Connection error, should retry later
			log.error("Connection error, should retry later", e);
		} catch (APIRequestException e) {
			// Should review the error, and fix the request
			log.error("Should review the error, and fix the request", e);
			log.error("HTTP Status: " + e.getStatus());
			log.error("Error Code: " + e.getErrorCode());
			log.error("Error Message: " + e.getErrorMessage());
		}
		return this;
	}
	
}
