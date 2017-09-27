package com.ecnice.tools.excel;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * @param <E>
 * @Title:
 * @Description:excel导入参数实体类
 * @Author:v-wuqiang
 * @Since:2016年10月11日 上午11:48:05
 * @Version:1.1.0
 * @Copyright:Copyright (c) 浙江蘑菇加电子商务有限公司 2015 ~ 2016 版权所有
 */
public class ImportParameter<T> implements Serializable{
	/**
	 *
	 */
	private static final long serialVersionUID = 6973437058478229831L;
	/**
	 * 行数，第1行开始
	 */
	private Integer row;
	/**
	 * 列数，第0列开始
	 */
	private Integer col;
	/**
	 * 单元格内容
	 */
	private String cellStr;
	/**
	 * 接收excel一行数据的对象
	 */
	private T obj;
	/**
	 * 返回的excel数据
	 */
	private List<T> list;
	
	public Integer getRow() {
		return row;
	}
	public void setRow(Integer row) {
		this.row = row;
	}
	public Integer getCol() {
		return col;
	}
	public void setCol(Integer col) {
		this.col = col;
	}
	public String getCellStr() {
		return cellStr;
	}
	public void setCellStr(String cellStr) {
		this.cellStr = cellStr;
	}
	public T getObj() {
		return obj;
	}
	public void setObj(T obj) {
		this.obj = obj;
	}
	public List<T> getList() {
		return list;
	}
	public void setList(List<T> list) {
		this.list = list;
	}
	
}
