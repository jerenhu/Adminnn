package com.ecnice.tools.utils;
import java.util.Date;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.ecnice.tools.pager.PagerModel;
import com.ecnice.tools.vo.DbSourceInfo;

/**
 * json工具
 * 
 * @Title:
 * @Description:
 * @Author:wentaoxiang
 * @Since:2016年9月8日 下午6:57:30
 * @Version:1.1.0
 * @Copyright:Copyright (c) 浙江蘑菇加电子商务有限公司 2015 ~ 2016 版权所有
 */
public class FastJsonUtils {
	
	/**
	 * 对象转成json
	 * 
	 * @param t
	 * @return
	 * @Description:
	 * @author wentaoxiang 2016年9月8日 下午6:57:43
	 */
	public static <T> String objectToJson(T t) {
		return JSON.toJSONString(t, SerializerFeature.DisableCircularReferenceDetect);
		/***
    	 *  Fastjson的SerializerFeature序列化属性:
    	 *  QuoteFieldNames———-输出key时是否使用双引号,默认为true 
		 *	WriteMapNullValue——–是否输出值为null的字段,默认为false 
		 *	WriteNullNumberAsZero—-数值字段如果为null,输出为0,而非null 
		 *	WriteNullListAsEmpty—–List字段如果为null,输出为[],而非null 
		 *	WriteNullStringAsEmpty—字符类型字段如果为null,输出为”“,而非null 
		 *	WriteNullBooleanAsFalse–Boolean字段如果为null,输出为false,而非null
    	 */
        //return JSON.toJSONString(t,SerializerFeature.DisableCircularReferenceDetect,SerializerFeature.WriteMapNullValue,SerializerFeature.WriteNullStringAsEmpty);
    }
	
	public static <T> String objectToJson(T t,SerializerFeature... features) {
		return JSON.toJSONString(t, features);
		/***
    	 *  Fastjson的SerializerFeature序列化属性:
    	 *  QuoteFieldNames———-输出key时是否使用双引号,默认为true 
		 *	WriteMapNullValue——–是否输出值为null的字段,默认为false 
		 *	WriteNullNumberAsZero—-数值字段如果为null,输出为0,而非null 
		 *	WriteNullListAsEmpty—–List字段如果为null,输出为[],而非null 
		 *	WriteNullStringAsEmpty—字符类型字段如果为null,输出为”“,而非null 
		 *	WriteNullBooleanAsFalse–Boolean字段如果为null,输出为false,而非null
    	 */
        //return JSON.toJSONString(t,SerializerFeature.DisableCircularReferenceDetect,SerializerFeature.WriteMapNullValue,SerializerFeature.WriteNullStringAsEmpty);
    }

	/**
	 * json转成对象
	 * 
	 * @param json
	 * @param t
	 * @return
	 * @Description:
	 * @author wentaoxiang 2016年9月8日 下午6:58:00
	 */
	public static <T> T jsonToObject(String json, Class<T> t) {
		return JSON.parseObject(json, t);
	}
	
	
	public static void main(String[] args) {
		DbSourceInfo db = new DbSourceInfo();
		db.setCreateTime(new Date());
		db.setDelFlag(null);
		System.err.println(JSON.toJSONString(db, SerializerFeature.DisableCircularReferenceDetect));//输出{"createTime":"2016-09-23 16:12:43","delFlag":1}
		
		System.err.println(JSON.toJSONString(db, SerializerFeature.DisableCircularReferenceDetect,SerializerFeature.WriteMapNullValue,SerializerFeature.WriteNullStringAsEmpty));//输出{"createTime":"2016-09-23 16:12:43","creator":null,"dbName":null,"delFlag":1,"driverClass":null,"id":null,"ip":null,"isCommon":null,"password":null,"port":null,"updateTime":null,"updator":null,"userId":null,"userName":null}
		PagerModel<DbSourceInfo> pm = new PagerModel<DbSourceInfo>();
		System.err.println(JSON.toJSONString(pm, SerializerFeature.DisableCircularReferenceDetect));
		System.err.println(JSON.toJSONString(pm, SerializerFeature.DisableCircularReferenceDetect,SerializerFeature.WriteMapNullValue,SerializerFeature.WriteNullStringAsEmpty));
	}
}
