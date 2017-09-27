package com.ecnice.tools.template;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;

import com.github.miemiedev.mybatis.paginator.domain.Order;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.ecnice.tools.pager.ORDERBY;
import com.ecnice.tools.pager.PagerModel;
import com.ecnice.tools.pager.Query;

/**
 * mybatis模板抽象类
 * @Title:
 * @Description:
 * @Author:wentaoxiang
 * @Since:2016年1月12日 下午2:22:28
 * @Version:1.1.0
 * @Copyright:Copyright (c) 浙江蘑菇加电子商务有限公司 2015 ~ 2016 版权所有
 */
public abstract class MybatisTemplate {

	@Resource(name="sqlSessionTemplate")
	private SqlSessionTemplate sqlSessionTemplate;

	public <T> T selectOne(String statement) {
		return this.sqlSessionTemplate.selectOne(statement);
	}

	public <T> T selectOne(String statement, Object parameter) {
		return this.sqlSessionTemplate.selectOne(statement, parameter);
	}

	public <K, V> Map<K, V> selectMap(String statement, String mapKey) {
		return this.sqlSessionTemplate.selectMap(statement, mapKey);
	}

	public <K, V> Map<K, V> selectMap(String statement, Object parameter, String mapKey) {
		return this.sqlSessionTemplate.selectMap(statement, parameter, mapKey);
	}

	public <K, V> Map<K, V> selectMap(String statement, Object parameter, String mapKey, RowBounds rowBounds) {
		return this.sqlSessionTemplate.selectMap(statement, parameter, mapKey, rowBounds);
	}

	public <E> List<E> selectList(String statement) {
		return this.sqlSessionTemplate.selectList(statement);
	}

	public <E> List<E> selectList(String statement, Object parameter) {
		return this.sqlSessionTemplate.selectList(statement, parameter);
	}

	public <E> List<E> selectList(String statement, Object parameter, RowBounds rowBounds) {
		return this.sqlSessionTemplate.selectList(statement, parameter, rowBounds);
	}

	public void select(String statement, ResultHandler handler) {
		this.sqlSessionTemplate.select(statement, handler);
	}

	public void select(String statement, Object parameter, ResultHandler handler) {
		this.sqlSessionTemplate.select(statement, parameter, handler);
	}

	public void select(String statement, Object parameter, RowBounds rowBounds, ResultHandler handler) {
		this.sqlSessionTemplate.select(statement, parameter, rowBounds, handler);
	}

	public int insert(String statement) {
		return this.sqlSessionTemplate.insert(statement);
	}

	public int insert(String statement, Object parameter) {
		return this.sqlSessionTemplate.insert(statement, parameter);
	}

	public int update(String statement) {
		return this.sqlSessionTemplate.update(statement);
	}

	public int update(String statement, Object parameter) {
		return this.sqlSessionTemplate.update(statement, parameter);
	}

	public int delete(String statement) {
		return this.sqlSessionTemplate.delete(statement);
	}

	public int delete(String statement, Object parameter) {
		return this.sqlSessionTemplate.delete(statement, parameter);
	}
	
	/**
	 * @author bruce.liu
	 * @Description:排序查询list
	 * @param params 参数
	 * @param statement sql
	 * @param orderBy 排序的map key是列名，value是desc或者asc
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected <E> List<E> selectList(String statement, Object params, Map<String,ORDERBY> orderBy) {
		PageBounds pageBounds = new PageBounds(PageBounds.NO_PAGE,PageBounds.NO_ROW_LIMIT);
		//不让其查询总记录数
		pageBounds.setContainsTotalCount(false);
		//添加排序
		setOrderInfo(orderBy, pageBounds);
		return (List<E>) sqlSessionTemplate.selectList(statement, params, pageBounds);
	}
	
	/**
	 * 分页结果集
	 * @author bruce.liu
	 * @param statement
	 * @param params
	 * @param query
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected <E> List<E> getPagerModelListByQuery(String statement, Object params, Query query) {
		PageBounds pageBounds = new PageBounds(query.getPageNumber(), query.getPageSize());
		//不让其查询总记录数
		pageBounds.setContainsTotalCount(false);
		//添加排序
		setOrderInfo(query.getSqlOrderBy(), pageBounds);
		return (List<E>) sqlSessionTemplate.selectList(statement, params, pageBounds);
	}

	/**
	 * @param params 参数
	 * @param query 查询分页参数
	 * @param countSql 总记录数sql
	 * @param dataSql 数据集sql
	 * @return
	 * @Description:分页查询方法
	 */
	@SuppressWarnings("unchecked")
	protected <T> PagerModel<T> getPagerModelByQuery(Object params, Query query, String dataSql) {
		PageBounds pageBounds = new PageBounds(query.getPageNumber(), query.getPageSize());
		//添加排序
		this.setOrderInfo(query.getSqlOrderBy(),pageBounds);
		List<T> datas = (List<T>) sqlSessionTemplate.selectList(dataSql, params, pageBounds);
		PageList<T> pageList = (PageList<T>) datas;
		// 获得结果集条总数
		int count = pageList.getPaginator().getTotalCount();
		return new PagerModel<T>(count, datas);
	}

	
	// 设置排序信息
	private void setOrderInfo(Map<String,ORDERBY> orderBy,PageBounds pageBounds) {
		List<Order> orders = null;
		if(orderBy!=null && orderBy.size()>0) {
			orders = new ArrayList<Order>();
			for(Entry<String, ORDERBY> order: orderBy.entrySet()) {
				orders.add(Order.create(order.getKey(),order.getValue().toString()));
			}
			if(CollectionUtils.isNotEmpty(orders)){
				pageBounds.setOrders(orders);
			}
		}
	}

}
