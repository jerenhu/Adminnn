package com.ecnice.privilege.common;

import java.io.Serializable;
import java.util.List;


public class PagerModel<T> implements Serializable{
	private static final long serialVersionUID = 1L;
	
	public static final int PAGER_SIZE = 20;
	private long total;
	private List<T> datas;
	private List<T> rows; //easyui使用

	public PagerModel(){}
	
	public PagerModel(long total, List<T> datas) {
		this.total = total;
		this.datas = datas;
	}

	public List<T> getDatas() {
		return datas;
	}

	public void setDatas(List<T> datas) {
		this.datas = datas;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public List<T> getRows() {
		return rows;
	}

	public void setRows(List<T> rows) {
		this.rows = rows;
	}

}
