package com.ecnice.privilege.common;

import java.io.Serializable;

/**
	* 功能描述：查询参数封装
	*
	* @author 刘文军(bluesky.liu)
	*
	* <p>修改历史：(修改人，修改时间，修改原因/内容)</p>
 */
public class Query implements Serializable{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 817880730448759944L;
	
	/**
	 * 跳转
	 */
	private int pageIndex;
	/**
	 * 每页显示记录数
	 */
	private int pageSize = 15;

	//排序的属性名称
	private String sortField;
	//是desc 还是asc
	private String sortOrder;

	public Query() {}

	public Query(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageIndex() {
		if(this.pageIndex < 0) {
			this.pageIndex = 1;
		}else {
			this.pageIndex++;
		}
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	public String getSortField() {
		return sortField;
	}

	public void setSortField(String sortField) {
		this.sortField = sortField;
	}

	public String getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}
}
