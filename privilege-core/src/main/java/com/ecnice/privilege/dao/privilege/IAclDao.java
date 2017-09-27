package com.ecnice.privilege.dao.privilege;

import java.util.List;

import com.ecnice.privilege.model.privilege.ACL;
import com.ecnice.privilege.vo.privilege.AclVo;

/**
 * @Title:
 * @Description:访问控制中心Dao接口
 * @Author:Bruce.Liu
 * @Since:2014年4月1日
 * @Version:1.1.0
浙江蘑菇加电子商务有限公司 2014 ~ 2015 版权所有
 */
public interface IAclDao {
	/**
	 * 通过id删除acl
	 * @param id
	 */
	public void delAclById(String id) ;
	/**
	 * 更新acl
	 * @param acl
	 */
	public void updateAcl(ACL acl) ;
	/**
	 * 根据条件得到所有的ACL列表
	 * @param acl
	 * @return
	 */
	public List<ACL> getAllACL(ACL acl) ;
	/**
	 * @param roleId
	 * @return
	 * @throws Exception
	 * @Description:通过角色id得到acl列表
	 */
	public List<ACL> getAclsByRoleId(String roleId) throws Exception;

	/**
	 * @param userId
	 * @return
	 * @throws Exception
	 * @Description:通过用户的id得到acl列表
	 */
	public List<ACL> getAclsByUserId(String userId) throws Exception;

	/**
	 * @param roleId
	 * @return
	 * @throws Exception
	 * @Description:通过角色ids得到acl列表
	 */
	public List<ACL> getAclsByRoleIds(String roleIds) throws Exception;

	/**
	 * @param userId
	 * @return
	 * @throws Exception
	 * @Description:通过用户的id得到模块id列表
	 */
	public List<AclVo> getModuleIdsByUserId(String userId) throws Exception;

	/**
	 * @param roleId
	 * @return
	 * @throws Exception
	 * @Description:通过角色id得到模块id列表
	 */
	public List<AclVo> getModuleIdsByRoleIds(String roleIds) throws Exception;

	/**
	 * @param acl
	 * @throws Exception
	 * @Description:添加访问控制中心
	 */
	public void insertAcl(ACL acl) throws Exception;
	
	public void insertAclBatch(List<ACL> acls) throws Exception;

	/**
	 * @param id
	 * @throws Exception
	 * @Description:删除访问控制中心
	 */
	public void delAcl(ACL acl) throws Exception;

	/**
	 * @param userId
	 * @param systemId
	 * @return
	 * @Description:通过用户和系统id得到这个用户的acl列表
	 */
	public List<ACL> getAclsByUserId(String userId, String systemSn);

	/**
	 * @param roleId
	 * @param systemId
	 * @return
	 * @Description:通过角色和系统id得到这个用户的acl列表
	 */
	public List<ACL> getAclsByRoleId(String roleId, String systemSn);
	
	/**
	 * 
	 * @param systemSn 新标识
	 * @param oldSystemSn 旧标识
	 * @Description:修改系统标识
	 */
	public void updateSystemSn(String systemSn,String oldSystemSn);
	
	/**
	 * 
	 * @param systemSn 新标识
	 * @param oldSystemSn 旧标识
	 * @Description:修改模块标识
	 */
	public void updateModuleSn(String moduleSn,String oldModuleSn);
	
	/**
	 * 通过模块id修改模块标识
	 * @param newModuleSn 新的模块标识
	 * @param moduleId 模块id
	 * @Description:
	 */
	public void updateModuleSnByModuleId(String newModuleSn,String moduleId);

	/**
	 * 获取ACL
	 * @param acl
	 * @return
	 */
	public ACL getAclBySystemSnAndModuleId(ACL acl);
}
