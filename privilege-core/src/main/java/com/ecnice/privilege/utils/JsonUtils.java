/**
 * JsonUtils.java V1.0 2013-5-14 上午10:18:44
 *
浙江蘑菇加电子商务有限公司版权所有，保留所有权利。
 *
 * Modification history(By Time Reason):
 *
 * Description:
 */

package com.ecnice.privilege.utils;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import com.ecnice.privilege.common.PagerModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * 转化成List的方法
 * getGson().fromJsonjson, new( TypeToken<List<clazz>>() {}.getType());
	* 功能描述：
	*
	* @author 刘文军(bruce.liu)
	*
	* <p>修改历史：(修改人，修改时间，修改原因/内容)</p>
 */
public class JsonUtils {
	
	public static Map<String, Object> getMap(String jsonString) {
		JSONObject jsonObject;
		try {
			jsonObject = new JSONObject();
			jsonObject = jsonObject.fromObject(jsonString);
			@SuppressWarnings("unchecked")
			Iterator<String> keyIter = jsonObject.keys();
			String key;
			Object value;
			Map<String, Object> valueMap = new HashMap<String, Object>();
			while (keyIter.hasNext()) {
				key = (String) keyIter.next();
				value = jsonObject.get(key);
				valueMap.put(key, value);
			}
			return valueMap;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
		* 功能描述：得到分页json
		*
		* @author 刘文军(bruce.liu)
		* <p>创建日期 ：2013-6-3 上午10:24:38</p>
		*
		* @param pm
		* @return
		*
		* <p>修改历史 ：(修改人，修改时间，修改原因/内容)</p>
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static String getPmJson(PagerModel<?> pm) {
		if (pm == null)
			return "";
		List<?> data = pm.getDatas();
		long total = pm.getTotal();
		HashMap result = new HashMap();
		result.put("data", data);
		result.put("total", total);
		result.put("rows", data);
		return JsonUtils.getGson().toJson(result);
	}
	
	/**
	* 功能描述：字符串转为对象
	*
	* @author wang.m
	* <p>创建日期 ：2013-5-14 上午12:38:13</p>
	*
	* @param json
	* @param format
	* @param obj
	* @return
	*
	* <p>修改历史 ：(修改人，修改时间，修改原因/内容)</p>
 */
	@SuppressWarnings("unchecked")
	public static Object jsonToObj(String json,@SuppressWarnings("rawtypes") Class clazz) {
		return JsonUtils.getGson().fromJson(json, clazz);
	}
	
	/**
		* 功能描述：得到GSON
		*
		* @author 刘文军(bruce.liu)
		* <p>创建日期 ：2013-5-22 下午2:27:29</p>
		*
		* @return
		*
		* <p>修改历史 ：(修改人，修改时间，修改原因/内容)</p>
	 */
	public static Gson getGson() {
		GsonBuilder gsonBuilder = new GsonBuilder();  
		gsonBuilder.setDateFormat("yyyy-MM-dd'T'HH:mm:ss");  
		gsonBuilder.registerTypeAdapter(Timestamp.class,new TimestampTypeAdapter());  
		Gson GSON = gsonBuilder.create();  
		return  GSON;
	}
	
	/**
		* 功能描述：对象转发成json
		*
		* @author 刘文军(bruce.liu)
		* <p>创建日期 ：2013-6-3 上午10:25:08</p>
		*
		* @param obj
		* @return
		*
		* <p>修改历史 ：(修改人，修改时间，修改原因/内容)</p>
	 */
	public static String toJson(Object obj) {
		return getGson().toJson(obj);
	}
	
	
}