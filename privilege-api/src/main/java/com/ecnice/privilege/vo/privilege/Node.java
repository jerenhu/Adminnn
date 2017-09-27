/**
	* Node.java V1.0 2013-5-10 下午4:37:55
	*
浙江蘑菇加电子商务有限公司版权所有，保留所有权利。
	*
	* Modification history(By Time Reason):
	*
	* Description:
	*/

package com.ecnice.privilege.vo.privilege;

import java.io.Serializable;

public class Node implements Serializable{
	private String id;
	private String text;
	private String pid;
	private Boolean isLeaf;
	private String url;
	private Boolean expanded;
	
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public Boolean getIsLeaf() {
		return isLeaf;
	}
	public void setIsLeaf(Boolean isLeaf) {
		this.isLeaf = isLeaf;
	}
	public Boolean getExpanded() {
		return expanded;
	}
	public void setExpanded(Boolean expanded) {
		this.expanded = expanded;
	}
	
}
