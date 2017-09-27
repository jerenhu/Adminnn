package com.ecnice.privilege.model.privilege;

import java.io.Serializable;

import com.ecnice.privilege.model.BaseModel;
import com.ecnice.privilege.utils.UrlUtils;

/**
 * @Comment:系统信息
 * @author bruce.liu
 * @Create Date 2014年3月11日
 */
@SuppressWarnings("serial")
public class ICSystem extends BaseModel implements Serializable {
	//id 32
	private String id;
	//名称 20
	private String name;
	//系统标示
	private String sn;
	//系统url前缀 30
	private String url;
	//系统的图标 40
	private String image;
	//系统备注 80
	private String note;
	//排序号
	private Integer orderNo;
	//easyUI树结构文本
	private String text;
	//easyUIcheckbox选中标识
	private boolean checked=false;
	
	public boolean isChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
		this.text = name;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		//去掉最后的 ‘/’
		this.url = UrlUtils.handelUrl(url);;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getSn() {
		return sn;
	}
	public void setSn(String sn) {
		this.sn = sn;
	}
	public Integer getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}
	
}
