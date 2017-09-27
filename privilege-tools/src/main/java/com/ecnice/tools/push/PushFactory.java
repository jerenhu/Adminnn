package com.ecnice.tools.push;

import java.util.concurrent.ConcurrentHashMap;

import com.ecnice.tools.common.JsonUtils;
import com.ecnice.tools.push.jpush.JPushService;

/**
 * 推送Factory
 * @Title:
 * @Description:
 * @Author:wentaoxiang
 * @Since:2016年10月14日 下午3:45:37
 * @Version:1.1.0
 * @Copyright:Copyright (c) 浙江蘑菇加电子商务有限公司 2015 ~ 2016 版权所有
 */
public class PushFactory {
	private static String b_masterSecret = "eed69d02e2ee7a90f1118521"; // B端测试
	private static String b_appKey = "f73597b358b57b177fc8f028"; // B端测试
	
	private static String c_masterSecret = "1816946c7dbea16b8152ea2b"; // C端测试
	private static String c_appKey = "4965cdc1f49067f1eff210ec"; // C端测试
	
	
	private static ConcurrentHashMap<String, IPushService> mapService = new ConcurrentHashMap<String, IPushService>();

	public static IPushService build(PushParam param) {
		String key = param.getPushTypeEnum().getCode().toString() + param.getPushAppEnum().getCode().toString();
		IPushService pushService = mapService.get(key);
		if (null != pushService) {
			return pushService;
		}

		switch (param.getPushTypeEnum()) {
		case JPUSH:
			switch (param.getPushAppEnum()) {
			case B:
				pushService = new JPushService(b_masterSecret,b_appKey);
				break;
			case C:
				pushService = new JPushService(c_masterSecret,c_appKey);
				break;
			default:
				break;
			}
			break;
		default:
			break;
		}
		mapService.putIfAbsent(key, pushService);
		return mapService.get(key);
	}

	public static void setB_masterSecret(String b_masterSecret) {
		PushFactory.b_masterSecret = b_masterSecret;
	}

	public static void setB_appKey(String b_appKey) {
		PushFactory.b_appKey = b_appKey;
	}
	
	public static void setC_masterSecret(String c_masterSecret) {
		PushFactory.c_masterSecret = c_masterSecret;
	}
	public static void setC_appKey(String c_appKey) {
		PushFactory.c_appKey = c_appKey;
	}


	public static void main(String[] args) throws InterruptedException {
		PushMsg pushMsg = new PushMsg();
		pushMsg.setAlert("I love you");
		pushMsg.setType(5);
		pushMsg.setAlert("【验收通知】工地华联钱塘公馆1幢的（业主：测试2，手机：15700064852）安装验收已发起，请尽快与业主约定验收时间。");
		pushMsg.setId("8a8a944f5605da1a0156073bd9d10091");
		PushFactory.build(new PushParam(PushTypeEnum.JPUSH, PushAppEnum.B))/*.pushAll(pushMsg,"100d8559094e46d4e6d")*/.pushAndroid(pushMsg, "100d8559094e4c1f15a").pushIOS(pushMsg, "161a3797c80ac0b7a65");
		PushFactory.build(new PushParam(PushTypeEnum.JPUSH, PushAppEnum.C))/*.pushAll(pushMsg,"100d8559094e46d4e6d")*/.pushAndroid(pushMsg, "160a3797c80ad3368ca").pushIOS(pushMsg, "161a3797c80ac0b7a65");
		PushFactory.build(new PushParam(PushTypeEnum.JPUSH, PushAppEnum.B))/*.pushAll(pushMsg,"100d8559094e46d4e6d")*/.pushAndroid(pushMsg, "100d8559094e4c1f15a").pushIOS(pushMsg, "141fe1da9ea51f1cefa");
		PushFactory.build(new PushParam(PushTypeEnum.JPUSH, PushAppEnum.C))/*.pushAll(pushMsg,"100d8559094e46d4e6d")*/.pushAndroid(pushMsg, "160a3797c80ad3368ca").pushIOS(pushMsg, "141fe1da9ea51f1cefa");
		System.out.println(JsonUtils.toJson(pushMsg));
	}
}
