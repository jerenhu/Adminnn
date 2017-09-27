package com.ecnice.privilege.model.privilege;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;

import com.ecnice.privilege.model.BaseModel;
import org.apache.commons.lang.StringUtils;

/**
 * @Comment:角色权限
 * @author bruce.liu
 * @Create Date 2014年3月11日
 */
@SuppressWarnings("serial")
public class ACL extends BaseModel implements Serializable {

	// 修改代码并提交到github测试...

	public static final String USER = "user";// 用户
	public static final String ROLE = "role";// 角色

	/**
	 * 授权允许
	 */
	public static final int ACL_YES = 1;

	/**
	 * 授权不允许
	 */
	public static final int ACL_NO = 0;

	/**
	 * 授权不确定
	 */
	public static final int ACL_NEUTRAL = -1;
	// id 32
	private String id;
	// 分类id 比方说用户id或者角色id32
	private String releaseId;
	// 分类标示 如果是角色的话这个就是 role 如果是用户的话就是user
	private String releaseSn = "role";
	// 系统标示
	private String systemSn;
	// 模块id
	private String moduleId;
	// 模块标示
	private String moduleSn;
	/**
	 * acl状态 5
	 */
	private BigInteger aclState;
	// 临时变量
	private List<SystemPrivilegeValue> values;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSystemSn() {
		return systemSn;
	}

	public void setSystemSn(String systemSn) {
		this.systemSn = systemSn;
	}

	public String getModuleSn() {
		return moduleSn;
	}

	public void setModuleSn(String moduleSn) {
		this.moduleSn = moduleSn;
	}

	public String getReleaseSn() {
		return releaseSn;
	}

	public void setReleaseSn(String releaseSn) {
		this.releaseSn = releaseSn;
	}

	public String getReleaseId() {
		return releaseId;
	}

	public void setReleaseId(String releaseId) {
		this.releaseId = releaseId;
	}

	public String getModuleId() {
		return moduleId;
	}

	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}

	/**
	 * 设置权限
	 * 
	 * @param permission
	 * @param yes
	 */
	public void setPermission(int permission, boolean yes) {
		if (aclState == null){
			aclState = new BigInteger("0");
		}
		BigInteger temp = new BigInteger("1");
		temp = temp.shiftLeft(permission);
		if (yes) {
			aclState = aclState.or(temp);
		} else {
			aclState = aclState.xor(temp);
		}
	}

	/**
	 * 得到权限
	 * 
	 * @param permission
	 * @return
	 */
	public int getPermission(int permission) {
		BigInteger temp = new BigInteger("1");
		temp = temp.shiftLeft(permission);
		temp = temp.and(aclState);
		if (StringUtils.isNotBlank(temp.toString()) && temp.toString() != "0") {
			return ACL_YES;
		}
		return ACL_NO;
	}

	public BigInteger getAclState() {
		return aclState;
	}

	public void setAclState(BigInteger aclState) {
		this.aclState = aclState;
	}

	public List<SystemPrivilegeValue> getValues() {
		return values;
	}

	public void setValues(List<SystemPrivilegeValue> values) {
		this.values = values;
	}

	@Override
	public String toString() {
		return "ACL{" + "id='" + id + '\'' + ", releaseId='" + releaseId + '\'' + ", releaseSn='" + releaseSn + '\''
				+ ", systemSn='" + systemSn + '\'' + ", moduleId='" + moduleId + '\'' + ", moduleSn='" + moduleSn + '\''
				+ ", aclState=" + aclState + ", values=" + values + '}';
	}
}
