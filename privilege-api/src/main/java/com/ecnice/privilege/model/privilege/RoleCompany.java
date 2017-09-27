package com.ecnice.privilege.model.privilege;

import java.io.Serializable;

import com.ecnice.privilege.model.BaseModel;

/**
 * @Title:角色分配的公司
 * @Description:
 * @Author:XTJ
 * @Since:2017-05-09 21:25:08
 * @Version:1.1.0
 * @Copyright:Copyright (c) 浙江蘑菇加电子商务有限公司 2015 ~ 2016 版权所有  
 */
public class RoleCompany extends BaseModel implements Serializable{
    /**
	 *
	 */
	private static final long serialVersionUID = 3339294414947655952L;

	/**
     * ID
     */
    private String id;
    
    /**
     * 角色id
     */
    private String roleId;
    
    /**
     * 公司id
     */
    private String companyId;
    
    
    
    
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    public String getRoleId() {
        return roleId;
    }
    
    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }
    public String getCompanyId() {
        return companyId;
    }
    
    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }
}
