package com.ecnice.privilege.vo.privilege;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

/**
 * 树形结构的组织机构数据
 * @Title:
 * @Description:
 * @Author:xietongjian
 * @Since:2017年3月16日 下午4:09:29
 * @Version:1.1.0
 * @Copyright:Copyright (c) 浙江蘑菇加电子商务有限公司 2015 ~ 2016 版权所有  
 */
public class OrgTreeApiVo implements Serializable{

	/**
	 *
	 */
	private static final long serialVersionUID = -3347841349381434117L;

	/**
	 * 节点ID
	 */
	private String id;
	/**
	 * 节点父级ID
	 */
	private String pId;
	/**
	 * 节点名称
	 */
	private String text;
	
	/**
	 * 编码
	 */
	private String code;
	
	/**
	 * 展开状态
	 */
	private String open;
	/**
	 * 类型 1:公司；2:部门
	 */
	private String type;  
	/**
	 * 公司ID
	 */
	private String companyId;
	/**
	 * 子节点
	 */
	private String[] children;
	/**
	 * 子节点数据
	 */
	private List<OrgTreeApiVo> orgTreeApiVos;
	/**
	 * 节点状态，'open' 或 'closed'。
	 */
	private String state; 
	/**
	 * 如果 公司下 第一个部门  和公司 同名  则合并   1合并 空代表没合并
	 */
	private String isMerge;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getpId() {
		return pId;
	}
	public void setpId(String pId) {
		this.pId = pId;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public String[] getChildren() {
		return children;
	}
	public void setChildren(String[] children) {
		this.children = children;
	}
	public List<OrgTreeApiVo> getOrgTreeApiVos() {
		return orgTreeApiVos;
	}
	public void setOrgTreeApiVos(List<OrgTreeApiVo> orgTreeApiVos) {
		this.orgTreeApiVos = orgTreeApiVos;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getIsMerge() {
		return isMerge;
	}
	public void setIsMerge(String isMerge) {
		this.isMerge = isMerge;
	}
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	@Override
	public String toString() {
		return "OrgTreeApiVo [id=" + id + ", pId=" + pId + ", text=" + text + ", type=" + type + ", companyId="
				+ companyId + ", children=" + Arrays.toString(children) + ", orgTreeApiVos=" + orgTreeApiVos
				+ ", state=" + state + ", isMerge=" + isMerge + "]";
	} 
	
}
