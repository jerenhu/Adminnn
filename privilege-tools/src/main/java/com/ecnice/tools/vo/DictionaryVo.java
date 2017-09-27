package com.ecnice.tools.vo;

import java.io.Serializable;

/**
 * 数据字典vo
 * @Title:
 * @Description:
 * @Author:wentaoxiang
 * @Since:2016年5月8日 下午6:58:52
 * @Version:1.1.0
 * @Copyright:Copyright (c) 浙江蘑菇加电子商务有限公司 2015 ~ 2016 版权所有
 */
public class DictionaryVo implements Serializable{
    
    /**
	 *
	 */
	private static final long serialVersionUID = -5530995491150505068L;

    /**
     * 名称
     */
    private String name;
    
    /**
     * 编码
     */
    private String code;
    
    /**
     * 标识
     */
    private String sn;
    
    /**
     * 备注
     */
    private String remark;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	@Override
	public String toString() {
		return "DictionaryVo [name=" + name + ", code=" + code + ", sn=" + sn + ", remark=" + remark + "]";
	}
        
}
