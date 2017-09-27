package com.ecnice.privilege.model.privilege;

import java.io.Serializable;
import java.util.List;

import com.ecnice.privilege.model.BaseModel;

/**
 * 公司类
 * @author wentaoxiang
 * @date 2016-11-13 15:16:07
 */
public class Company extends BaseModel implements Serializable{
    
    
	/**
	 *
	 */
		
	private static final long serialVersionUID = 1L;

	/**
     * 
     */
    private String id;
    
    /**
     * 上级公司id
     */
    private String pid;
    
    /**
     * 公司中文名称
     */
    private String cname;
    
    /**
     * 公司英文名称
     */
    private String ename;
    
    /**
     * 公司code
     */
    private String code;
    
    /**
     * 描述
     */
    private String descr;
    
    /**
     * 状态 1启用 0禁用
     */
    private Integer status;
    
    private String userName;//登入名
    private List<String> roleSnList;//角色标识
    
	 /* 
    private String startTime; 
    private String endTime;
    
    public String getStartTime() {
    	return startTime;
    }
    
    public void setStartTime(String startTime) {
    	this.startTime = startTime;
    }
    
    public String getEndTime() {
    	return endTime;
    }
    
    public void setEndTime(String endTime) {
    	this.endTime = endTime;
    }*/
	
    public String getId()
    {
        return id;
    }
        
    public void setId(String id)
    {
        this.id = id;
    }
        
    public String getPid()
    {
        return pid;
    }
        
    public void setPid(String pid)
    {	
        this.pid = pid;
    }
        
    public String getCname()
    {
        return cname;
    }
        
    public void setCname(String cname)
    {
        this.cname = cname;
    }
        
    public String getEname()
    {
        return ename;
    }
        
    public void setEname(String ename)
    {
        this.ename = ename;
    }
        
    public String getCode()
    {
        return code;
    }
        
    public void setCode(String code)
    {
        this.code = code;
    }
        
    public String getDescr()
    {
        return descr;
    }
        
    public void setDescr(String descr)
    {
        this.descr = descr;
    }
        
    public Integer getStatus()
    {
        return status;
    }
        
    public void setStatus(Integer status)
    {
        this.status = status;
    }

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public List<String> getRoleSnList() {
		return roleSnList;
	}

	public void setRoleSnList(List<String> roleSnList) {
		this.roleSnList = roleSnList;
	}

}
