package com.ecnice.privilege.component.httpclient.impl;

import java.net.URI;
import java.util.List;

import javax.annotation.Resource;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;

import com.ecnice.privilege.component.httpclient.IHttpclient;
import com.ecnice.privilege.model.system.SystemConfig;
import com.ecnice.privilege.service.system.ISystemConfigService;

@Component
public class HttpClientImpl implements IHttpclient {

	@Resource
	private ISystemConfigService systemConfigService;

	@Override
	public void execute(String url,String sessionId) throws Exception {
		List<SystemConfig> configs = systemConfigService
				.getSystemConfigsBySn(url);
		if (configs != null && configs.size() > 0) {
			DefaultHttpClient httpclient = new DefaultHttpClient();
			HttpGet httpGet = new HttpGet();
			for (SystemConfig config : configs) {
				httpGet.setURI(new URI(config.getConfigValue() + "?sessionId="
						+ sessionId));
				HttpResponse response = httpclient.execute(httpGet);
				String result = EntityUtils.toString(response.getEntity(),
						"utf-8");
				System.out.println("推送sessionId到" + config.getConfigName()
						+ "系统中成功！返回结果是:" + result);
			}
		}
	}

}
