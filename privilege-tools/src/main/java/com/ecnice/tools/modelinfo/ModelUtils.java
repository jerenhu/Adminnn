package com.ecnice.tools.modelinfo;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.Duration;

import com.ecnice.tools.common.JsonUtils;
import com.ecnice.tools.vo.ComTree;
import com.ecnice.tools.vo.DictionaryVo;
import com.ecnice.tools.vo.WrapperTree;

/**
 * model相关操作
 * @Title:
 * @Description:
 * @Author:wentaoxiang
 * @Since:2016年5月8日 下午7:49:38
 * @Version:1.1.0
 * @Copyright:Copyright (c) 浙江蘑菇加电子商务有限公司 2015 ~ 2016 版权所有
 */
public class ModelUtils {
	private static final Logger logger = Logger.getLogger(ModelUtils.class);
	
	/**
	 * 通过枚举得到数据字典vo
	 * 
	 * @param clas 枚举class
	 * @param methods 枚举方法数组【默认为 getCode,getName,getRemark,getSn】
	 * @return
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @Description:
	 * @author wentaoxiang 2016年5月8日 下午7:12:24
	 */
	public static List<DictionaryVo> getDictsByEnum(Class<?> clas, String... methods)
			throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Method mt = clas.getMethod("values");
		Object[] objs = (Object[]) mt.invoke(clas);

		List<DictionaryVo> dicts = setDictionaryVoInfo(objs, methods);
		return CollectionUtils.isNotEmpty(dicts) ? dicts : null;
	}

	/**
	 * 通过枚举得到Map
	 * @param clas 枚举class
	 * @param methods 枚举方法数组 【默认为 getSn,getCode】
	 * @return key:sn(标识);value:code
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @Description:
	 * @author wentaoxiang 2016年5月8日 下午7:12:24
	 */
	public static Map<String, Object> getMapByEnum(Class<?> clas, String... methods)
			throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		boolean methodsFlag = false;
		if (ArrayUtils.isNotEmpty(methods)) {
			methodsFlag = true;
		}

		Method mt = clas.getMethod("values");
		Object[] objs = (Object[]) mt.invoke(clas);
		Map<String, Object> map = getMapInfo(methodsFlag, Arrays.asList(objs), methods);
		return MapUtils.isNotEmpty(map) ? map : null;
	}

	/**
	 * 通过List得到Map
	 * @param lists List集合
	 * @param methodNames 【methods.length==1:key:默认为 getSn,value:Object;methods.length!=1:key:默认为 getSn,value:默认为 getCode】
	 * @return key:默认为 getSn,value:Object
	 * @throws NoSuchMethodException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @Description:
	 * @author wentaoxiang 2016年7月11日 下午4:00:28
	 */
	public static Map<String, Object> getMapByList(List<?> lists, String... methodNames) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException{
		boolean methodsFlag = false;
		if (ArrayUtils.isNotEmpty(methodNames)) {
			methodsFlag = true;
		}
		Map<String, Object> map = getMapInfo(methodsFlag, lists, methodNames);
		return MapUtils.isNotEmpty(map) ? map : null;
	}
	/**
	 * 转成map
	 * @param methodsFlag
	 * @param objs
	 * @param methodNames 【methods.length==1:key:默认为 getSn,value:Object;methods.length!=1:key:默认为 getSn,value:默认为 getCode】
	 * @return key:默认为 getSn,value:Object
	 * @throws NoSuchMethodException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @Description:
	 * @author wentaoxiang 2016年7月11日 下午4:00:02
	 */
	private static Map<String, Object> getMapInfo(boolean methodsFlag,List<?> objs, String... methodNames)
			throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		if (CollectionUtils.isEmpty(objs)) {
			return null;
		}
		Map<String, Object> map = new TreeMap<String, Object>();
		for (Object obj : objs) {
			Class<? extends Object> cls = obj.getClass();
			if (null != methodNames && methodNames.length == 1) {
				Method getSnMtd = cls.getMethod(methodsFlag && StringUtils.isNotBlank(methodNames[0]) ? methodNames[0].trim() : "getSn");
				String sn = getSnMtd.invoke(obj).toString();
				if (StringUtils.isNotBlank(sn)) {
					map.put(sn.trim(), obj);
				}
			} else {
				Method getSnMtd = cls.getMethod(methodsFlag && StringUtils.isNotBlank(methodNames[0]) ? methodNames[0].trim() : "getSn");
				Method getCodeMtd = cls.getMethod(methodsFlag && StringUtils.isNotBlank(methodNames[1]) ? methodNames[1].trim() : "getCode");
				String sn = getSnMtd.invoke(obj).toString();
				Object code = getCodeMtd.invoke(obj);

				if (StringUtils.isNotBlank(sn) && null != code) {
					map.put(sn.trim(), code);
				}
			}
		}
		return map;
	}
	
	
	/**
	 * 通过List得到Map
	 * @param lists List集合
	 * @param methodName 默认为 getSn
	 * @return key:默认为 getSn,value:T
	 * @throws NoSuchMethodException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @Description:
	 * @author wentaoxiang 2016年7月11日 下午4:00:28
	 */
	public static <T> Map<String, T> getMapByListOneMtd(List<T> lists, String methodName) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException{
		boolean methodsFlag = false;
		if (StringUtils.isNotBlank(methodName)) {
			methodsFlag = true;
		}
		Map<String, T> map = getMapInfoOneMtd(methodsFlag, lists, methodName);
		return MapUtils.isNotEmpty(map) ? map : null;
	}
	/**
	 * 转成map
	 * @param methodsFlag
	 * @param objs
	 * @param 默认为 getSn
	 * @return  key:默认为 getSn,value:T
	 * @throws NoSuchMethodException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @Description:
	 * @author wentaoxiang 2016年7月11日 下午4:00:02
	 */
	private static <T> Map<String, T> getMapInfoOneMtd(boolean methodsFlag, List<T> objs, String methodName)
			throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		if (CollectionUtils.isEmpty(objs)) {
			return null;
		}
		Map<String, T> map = new TreeMap<String, T>();
		for (T obj : objs) {
			Class<?> cls = obj.getClass();
			Method getSnMtd = cls.getMethod(methodsFlag ? methodName.trim() : "getSn");
			String sn = getSnMtd.invoke(obj).toString();
			if (StringUtils.isNotBlank(sn)) {
				map.put(sn.trim(), obj);
			}
		}
		return map;
	}
	
	/**
	 * 将list转成数据字典vo
	 * @param list
	 * @param methods 枚举方法数组【默认为 getCode,getName,getRemark】
	 * @return
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @Description:
	 * @author wentaoxiang 2016年5月8日 下午7:12:24
	 */
	public static List<DictionaryVo> convertDictionaryVo(List<?> dicts,String... methods) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException{
		List<DictionaryVo> dictVos = null;
		if (CollectionUtils.isNotEmpty(dicts)) {
			dictVos = setDictionaryVoInfo(dicts.toArray(new Object[dicts.size()]), methods);
		}
		return CollectionUtils.isNotEmpty(dictVos) ? dictVos : null;
	}
	
	/**
	 * 设置数据字典vo
	 * @param objs
	 * @param methods 方法名称数组
	 * @return
	 * @throws NoSuchMethodException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @Description:
	 * @author wentaoxiang 2016年5月8日 下午9:24:53
	 */
	private static List<DictionaryVo> setDictionaryVoInfo(Object[] objs, String... methods)
			throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		boolean methodsFlag = false;
		if (ArrayUtils.isNotEmpty(methods)) {
			methodsFlag = true;
		}
		
		List<DictionaryVo> dicts = new ArrayList<DictionaryVo>();
		for (Object obj : objs) {
			Class<? extends Object> cls = obj.getClass();
			Method getCodeMtd = cls.getMethod(methodsFlag && StringUtils.isNotBlank(methods[0]) ? methods[0].trim() : "getCode");
			Method getNameMtd = cls.getMethod(methodsFlag && StringUtils.isNotBlank(methods[1]) ? methods[1].trim() : "getName");
			
			Method getRemarkMtd = null;
			try {
				getRemarkMtd = cls.getMethod(methodsFlag && StringUtils.isNotBlank(methods[2]) ? methods[2].trim() : "getRemark");
			} catch (Exception e) {
			}
			
			Method getSnMtd = null;
			try {
				getSnMtd = cls.getMethod(methodsFlag && StringUtils.isNotBlank(methods[3]) ? methods[3].trim() : "getSn");
			} catch (Exception e) {
			}
			
			String code = getCodeMtd.invoke(obj).toString();
			String name = getNameMtd.invoke(obj).toString();
			String remark = null != getRemarkMtd ? getRemarkMtd.invoke(obj).toString() : null;
			String sn = null != getSnMtd ? getSnMtd.invoke(obj).toString() : null;

			if (StringUtils.isNotBlank(code) && StringUtils.isNotBlank(name)) {
				DictionaryVo dict = new DictionaryVo();
				dict.setCode(code.trim());
				dict.setName(name.trim());
				dict.setRemark(StringUtils.isNotBlank(remark) ? remark.trim() : null);
				dict.setSn(StringUtils.isNotBlank(sn) ? sn.trim() : null);
				dicts.add(dict);
			}
		}
		
		return dicts;
	}
	
	/**
	 * 通过枚举的类名取得枚举的值，例如  ModelUtils.getEnumByClassName("com.ys.order.enms.CstatusEnum")---> [{"sn":"re_handing","status":200,"name":"意向处理中"},{"sn":"re_handing","status":200,"name":"意向处理中"},{"sn":"re_handing","status":200,"name":"意向处理中"},{"sn":"re_close","status":201,"name":"意向关闭"},{"sn":"re_close","status":201,"name":"意向关闭"},{"sn":"re_close","status":201,"name":"意向关闭"},{"sn":"order_handing","status":202,"name":"订单处理中"},{"sn":"order_handing","status":202,"name":"订单处理中"},{"sn":"order_handing","status":202,"name":"订单处理中"},{"sn":"order_close","status":203,"name":"订单关闭"},{"sn":"order_close","status":203,"name":"订单关闭"},{"sn":"order_close","status":203,"name":"订单关闭"},{"sn":"contr_sign","status":204,"name":"合同签订"},{"sn":"contr_sign","status":204,"name":"合同签订"},{"sn":"contr_sign","status":204,"name":"合同签订"},{"sn":"contr_cutout","status":205,"name":"合同终止"},{"sn":"contr_cutout","status":205,"name":"合同终止"},{"sn":"contr_cutout","status":205,"name":"合同终止"},{"sn":"pro_wstart","status":206,"name":"项目待开工"},{"sn":"pro_wstart","status":206,"name":"项目待开工"},{"sn":"pro_wstart","status":206,"name":"项目待开工"},{"sn":"pro_ing","status":207,"name":"项目施工中"},{"sn":"pro_ing","status":207,"name":"项目施工中"},{"sn":"pro_ing","status":207,"name":"项目施工中"},{"sn":"pro_suspend","status":208,"name":"项目暂停"},{"sn":"pro_suspend","status":208,"name":"项目暂停"},{"sn":"pro_suspend","status":208,"name":"项目暂停"},{"sn":"pro_cutout","status":209,"name":"项目终止"},{"sn":"pro_cutout","status":209,"name":"项目终止"},{"sn":"pro_cutout","status":209,"name":"项目终止"},{"sn":"pro_finish","status":210,"name":"项目竣工"},{"sn":"pro_finish","status":210,"name":"项目竣工"},{"sn":"pro_finish","status":210,"name":"项目竣工"}]
	 * @param className
	 * @return
	 * @throws ClassNotFoundException
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @Description:
	 * @author wentaoxiang 2016年9月10日 下午5:23:56
	 */
	public static List<Map<String, Object>> getEnumByClassName(String className)
			throws ClassNotFoundException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Class<?> cls = Class.forName(className);
		Method valuesMTd = cls.getMethod("values");
		Object[] objs = (Object[]) valuesMTd.invoke(cls);
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		for (Object obj : objs) {
			Map<String, Object> map = new HashMap<String, Object>();
			for (Method me : cls.getDeclaredMethods()) {
				String mtdname = me.getName();
				if (mtdname.startsWith("get")) {
					try {
						Method mtd = obj.getClass().getMethod(mtdname);
						Object oj = mtd.invoke(obj);
						map.put(StringUtils.uncapitalize(mtdname.substring(3)), oj);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			list.add(map);
		}
		return list;
	}
	
	/**
	 * 将list封装树形结构
	 * @param list
	 * @param param
	 * @return
	 * @throws NoSuchFieldException
	 * @throws SecurityException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @Description:
	 * @author wentaoxiang 2016年12月31日 下午9:48:00
	 */
	public static <T> List<T> wrapperTree(List<T> list, WrapperTree param) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		//long t1 = System.currentTimeMillis();
		DateTime begin = null;
		if (logger.isDebugEnabled()) {
			begin = new DateTime();
		}

		if (CollectionUtils.isEmpty(list)) {
			return null;
		}
		String key = param.getKey();
		String parentKey = param.getParentKey();
		String childrenKey = param.getChildrenKey();
		if (StringUtils.isBlank(key) || StringUtils.isBlank(parentKey) || StringUtils.isBlank(childrenKey)) {
			return null;
		}

		Class<?> cls = list.get(0).getClass();
		Field fdKey = cls.getDeclaredField(key);
		fdKey.setAccessible(true);

		Field fdParentKey = cls.getDeclaredField(parentKey);
		fdParentKey.setAccessible(true);

		Field fdChildrenKey = cls.getDeclaredField(childrenKey);
		fdChildrenKey.setAccessible(true);

		Map<String, T> map = new HashMap<String, T>();
		for (int i = 0; i < list.size(); i++) {
			T obj = list.get(i);
			map.put((String) fdKey.get(obj), obj);
		}

		List<T> listTemp = new ArrayList<T>();
		for (int i = 0; i < list.size(); i++) {
			T obj = list.get(i);
			String parentTemp = (String) fdParentKey.get(obj);
			if (StringUtils.isNotBlank(parentTemp)) {
				T parentObj = map.get(parentTemp);
				@SuppressWarnings("unchecked")
				List<T> children = (List<T>) fdChildrenKey.get(parentObj);
				if (CollectionUtils.isEmpty(children))
					children = new ArrayList<T>();
				children.add(obj);
				
				fdChildrenKey.set(parentObj, children);
			} else {
				listTemp.add(obj);
			}
		}
		map = null;
		/*long t2 = System.currentTimeMillis();
		logger.info("ModelUtils-wrapperTree:耗时：" + (t2 - t1) + "ms");*/
		
		if (logger.isDebugEnabled()) {
			DateTime end = new DateTime();
			Duration drt = new Duration(begin, end);
			logger.debug("ModelUtils-wrapperTree:耗时d：" + drt.getMillis() + "ms");
		}
		return listTemp;
	}
	
	public static void main(String[] args) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		//list封装成tree---start
		List<ComTree> list = new ArrayList<ComTree>();
		
		ComTree root=new ComTree();
		root.setId("0");
		root.setName("root");
		list.add(root);
		
		ComTree root_1=new ComTree();
		root_1.setId("1");
		root_1.setName("root-1");
		root_1.setPid("0");
		list.add(root_1);
		
		ComTree root_2=new ComTree();
		root_2.setId("2");
		root_2.setName("root-2");
		root_2.setPid("0");
		list.add(root_2);
		
		ComTree root_1_1=new ComTree();
		root_1_1.setId("11");
		root_1_1.setName("root-1-1");
		root_1_1.setPid("1");
		list.add(root_1_1);
		
		ComTree root_1_2=new ComTree();
		root_1_2.setId("12");
		root_1_2.setName("root-1-2");
		root_1_2.setPid("1");
		list.add(root_1_2);
		
		ComTree root_1_3=new ComTree();
		root_1_3.setId("13");
		root_1_3.setName("root-1-3");
		root_1_3.setPid("1");
		list.add(root_1_3);
		
		ComTree root_2_1=new ComTree();
		root_2_1.setId("21");
		root_2_1.setName("root-2-1");
		root_2_1.setPid("2");
		list.add(root_2_1);
		
		WrapperTree param = new WrapperTree();
		param.setKey("id");
		param.setParentKey("pid");
		param.setChildrenKey("children");
		
		System.out.println("封装树前："+JsonUtils.toJson(list));
		System.out.println("封装树后："+JsonUtils.toJson(ModelUtils.wrapperTree(list, param)));
		//list封装成tree---end
	}
	
	
}
